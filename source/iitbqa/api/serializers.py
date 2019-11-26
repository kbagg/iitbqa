from rest_framework.serializers import ModelSerializer

from api.models import Topic, User, Question, Answer, Vote, Notification

class TopicSerializer(ModelSerializer):
    class Meta:
        model = Topic
        fields = ('id', 'name')

class UserSerializer(ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'name', 'ldapid', 'gender', 'department', 'bio', 'student_professor', 'degree', 'year', 'specialization', 'password', 'subscribed_topics', 'totalvotes')

class QuestionSerializer(ModelSerializer):
    class Meta:
        model = Question
        fields = ('id', 'user', 'username', 'userdepartment', 'userbio', 'userdegree', 'userspecialization', 'topics', 'question', 'description', 'numAnswers', 'timestamp')

class AnswerSerializer(ModelSerializer):
    class Meta:
        model = Answer
        fields = ('id', 'user', 'username', 'userdepartment', 'userbio', 'userdegree', 'userspecialization', 'content', 'question', 'votes', 'timestamp')

class VoteSerializer(ModelSerializer):
    class Meta:
        model = Vote
        fields = ('id', 'upvote_downvote', 'user', 'answer')

class NotificationSerializer(ModelSerializer):
    class Meta:
        model = Notification
        fields = ('id', 'receiver', 'sender', 'sendername', 'question', 'code', 'timestamp')
