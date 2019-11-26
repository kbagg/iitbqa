#from django.shortcuts import render
#from django.http import HttpResponse
#from django.core import serializers
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework.generics import ListAPIView
# Create your views here.

from api.models import Topic, User, Question, Answer, Vote, Notification, department_choices, degree_choices, year_choices 
from api.serializers import TopicSerializer, UserSerializer, QuestionSerializer, AnswerSerializer, VoteSerializer, NotificationSerializer

## @package views
#Actual operations on the db are performed in this package

def IdFromLdap(ldap):
    """!@brief Function for getting user id given ldapid
    @details this is useful because the frontend will store the ldapid for the current user instead of the table id used in the db
    @param ldap ldapid of the user whose id needs to be returned
    @return the id for the user with the input ldap
    """
    user = User.objects.filter(ldapid=ldap).values('id')
    return user[0]['id']

def QuestionResponse(userid, questionid):
    """@brief Function for getting the particular question for a logged in user
    @details It takes a question id and a user id as input and outputs the question along with a list of all the answers for this question and all the votes which are given for some answer of this question
    @param userid the user id in db
    @param questionid the question id in db
    @return the question along with a list of answers for the question and a list of votes by the user on answer for this question
    """
    
    question = Question.objects.filter(id=questionid)
    qdata = QuestionSerializer(question, many=True)
    
    answers = Answer.objects.filter(question=questionid)
    answer_list = answers.values_list('id', flat=True).order_by('id')
    adata = AnswerSerializer(answers, many=True)
    
    uservotes = Vote.objects.filter(user=userid) 
    ans_list = uservotes.values_list('answer', flat=True).order_by('answer')
    alist = [ans for ans in answer_list if ans in ans_list]
    votes = uservotes.filter(answer__in=alist) 
    vdata = VoteSerializer(votes, many=True)
    
    return Response({
        "Question" : qdata.data,
        "Answers" : adata.data,
        "Votes" : vdata.data,
    })  

def Feed(userid):
    """!@brief Function that returns the feed output for the user with id userid
    @details the function will search for questions with tagged topics in the list of subscribed_topics for the user as well as the questions that he asked himself and the questions will be displayed in sorted order based on their timestamps
    @param the userid of the logged in user
    @return a list of questions which need to be displayed to the user on his feed
    """
    subtopics = User.objects.filter(id=userid).values_list('subscribed_topics', flat=True)
    q1 = Question.objects.filter(user=userid)
    q2 = Question.objects.filter(topics__in=subtopics).distinct()
    q3 = q1.union(q2)
    qdata = QuestionSerializer(q3, many=True)
    return Response(qdata.data)

@api_view(['POST'])
def AddTopic(request):
    serializer = TopicSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data, status=status.HTTP_201_CREATED)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET'])
def GeneralData(request):
    """!@brief Function which returns general data useful during the signup process while filling the form
    @details the function returns the list of all topics in the db currently along with a list of department choices, degree choices and year choices while filling up the signup form
    @return a dictionary of department choices with key being the value to be added while the value being the description for the key, similarly for degree as well as year
    """
    depchoices = {}
    for x in department_choices:
        depchoices[x[0]] = x[1]
    degchoices = {}
    for x in degree_choices:
        degchoices[x[0]] = x[1]
    topics = Topic.objects.all()
    tserializer = TopicSerializer(topics, many=True)
    return Response({
        "department_choices" : depchoices,
        "degree_choices" : degchoices,
        "topics" : tserializer.data
    })

@api_view(['GET'])
def TopicList(request):
    topics = Topic.objects.all()
    serializer = TopicSerializer(topics, many=True)
    return Response(serializer.data)

@api_view(['GET'])
def GetQuestion(request, ldap, questionid):
    """!@brief Function that returns the particular question (with question id given) for a user with ldap id
    @details this function is required because the frontend sends the ldap of the user instead of id
    @return returns the particular question object
    """
    userid = IdFromLdap(ldap)
    return QuestionResponse(userid, questionid)

@api_view(['GET'])
def QuestionList(request, ldap):
    """!@brief Function that returns the list of questions for a given ldap
    @details this is needed because the frontend sends the ldap of the user
    @return the list of questions
    """
    userid = IdFromLdap(ldap)
    return Feed(userid)

@api_view(['POST'])
def PostAnswer(request):
    """!@brief Function for posting an answer which is part of the request body
    @details this function extracts the answer details from the request body, then creates a new entry in the answer table with the corresponding details. It also creates a notification for a user whose question has been provided an answer and increase the field numAnswers for that question by 1
    @post New entries have been created in the answer table as well as the notification table along with an update in th question table to accomodate for the new changes
    @return returns the updated question object
    """
    serializer = AnswerSerializer(data=request.data)
    if serializer.is_valid():
        ans = serializer.validated_data
        sender = ans['user']
        question = ans['question']
        receiver = question.user
        answer = Answer(user=sender, username=sender.name, userdepartment=sender.department, userbio=sender.bio, userdegree=sender.degree, userspecialization=sender.specialization, content=ans['content'], question=question)
        answer.save()
        notification = Notification(receiver=receiver, sender=sender, sendername=sender.name, question=question, code=3)
        notification.save()
        #print(question.numAnswers)
        question.numAnswers = question.numAnswers + 1
        question.save()
        return QuestionResponse(sender.id, question.id)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET'])
def GetNotifications(request, ldap):
    """!@brief Function for getting notifications for a user
    @details searches the notification table for all entries with receiver being the same as the given user
    @return returns a list of notifications for the given user
    """
    receiver = User.objects.get(ldapid=ldap)
    notifs = Notification.objects.filter(receiver=receiver.id)
    notifserializer = NotificationSerializer(notifs, many=True)
    return Response(notifserializer.data)

@api_view(['POST'])
def PostQuestion(request):
    """!@brief Function which is called when a user adds a question
    @details the function takes a question object as part of the request body, extracts the object and then creates a new entry in the question table. Since the topics is a many-to-many field, the topics need to be added one by one
    @return returns the feed output for the given user
    """
    serializer = QuestionSerializer(data=request.data)
    if serializer.is_valid():
        ques = serializer.validated_data
        user = ques['user']
        question = Question(user=user, username=user.name, userdepartment=user.department, userbio=user.bio, userdegree=user.degree, userspecialization=user.specialization, question=ques['question'], description=ques['description'])
        question.save()
        for topic in ques['topics']:
            question.topics.add(topic)
        return Feed(user.id)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def AddVote(request):
    """!@brief Function to add a vote to some answer
    @details takes a vote object as part of the request body, extracts the vote object and creates a new entry in the vote table. Also, updates the total votes for the answer and the total votes for the user who gave the answer. Also creates a new notification for the user who gave the answer.
    @post New entries have been created in the vote and the notification table. Also updated the answer and user tables.
    @return returns the modified question object
    """
    serializer = VoteSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        vote = serializer.validated_data
        answer = vote['answer']
        receiver = answer.user
        sender = vote['user']
        question = answer.question
        u_d = vote['upvote_downvote']
        if u_d:
            answer.votes = answer.votes + 1
            receiver.totalvotes = receiver.totalvotes + 1
            code = 1
        else:
            answer.votes = answer.votes - 1
            receiver.totalvotes = receiver.totalvotes - 1
            code = 2
        notification = Notification(receiver=receiver, sender=sender, sendername=sender.name, question=question, code=code)
        notification.save()
        answer.save()
        receiver.save()
        questionid = question.id
        return QuestionResponse(sender.id, questionid)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def AddUser(request):
    """!@brief Function to create a new user after signup
    @details the user object is part of the request body, the function will extract this and create a new entry in the user table.
    @post New entry has been created in the user table
    @return returns the same user data if correctly created and error message in case user can't be created
    """
    serializer = UserSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data, status=status.HTTP_200_OK)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET'])
def GetUser(request, ldap, password):
    """!@brief Function that returns a user given an ldap and a password, used for login
    @details checks if a user with a given ldap exists and if it does checks if the entered password is correct. If yes, returns the user data, otherwise returns an error message
    @return the user object if the given ldap and password match. Else, return an error message
    """
    message = {'error': 'Wrong Query'}
    try:
        user = User.objects.get(ldapid=ldap)
    except:
        return Response(message, status=status.HTTP_201_CREATED)
    if user.password == password:
        serializer = UserSerializer(user)
        return Response(serializer.data)
    return Response(message, status=status.HTTP_201_CREATED)

@api_view(['POST'])
def RemoveUser(request, ldap):
    user = User.objects.get(ldapid=ldap)
    ujson = UserSerializer(user)
    user.delete()
    return Response(ujson.data)

@api_view(['POST'])
def RemoveQuestion(request, questionid):
    q = Question.objects.get(id=questionid)
    qjson = QuestionSerializer(q)
    q.delete()
    return Response(qjson.data)

@api_view(['POST'])
def RemoveAnswer(request, answerid):
    a = Answer.objects.get(id=answerid)
    ajson = AnswerSerializer(a)
    a.delete()
    return Response(ajson.data)

@api_view(['POST'])
def RemoveVote(request, voteid):
    v = Vote.objects.get(id=voteid)
    vjson = VoteSerializer(v)
    v.delete()
    return Response(vjson.data)
