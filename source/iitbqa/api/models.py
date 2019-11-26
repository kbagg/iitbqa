from django.db import models

# Create your models here.
# Each model has a default Autofield 'id' to denote the primary key

## @package models
# Different tables to be stored in the db

class Topic(models.Model):
    """!@brief Model for Topic 
    @param name the name of the topic
    """
    name = models.CharField(max_length=100, unique=True)
    def __str__(self):
        return self.name

## @brief this describes the possible choices for gender of a user 
gender_choices = [
    ('M', 'Male'),
    ('F', 'Female'),
]

## @brief this describes the possible choices for department of a user 
department_choices = [
    ('AE', 'Aerospace Engineering'),
    ('BSBE', 'Biosciences and Bioengineering'),
    ('CHE', 'Chemical Engineering'),
    ('CHM', 'Chemistry'),
    ('CE', 'Civil Engineering'),
    ('CSE', 'Computer Science & Engineering'),
    ('ES', 'Earth Sciences'),
    ('EE', 'Electrical Engineering'),
    ('ESE', 'Energy Science and Engineering'),
    ('CESE', 'Environmental Science and Engineering (CESE)'),
    ('HSS', 'Humanities & Social Sciences'),
    ('IDC', 'IDC School of Design'),
    ('MTH', 'Mathematics'),
    ('ME', 'Mechanical Engineering'),
    ('MEMS', 'Metallurgical Engineering & Materials Science'),
    ('PHY', 'Physics'),
]

## @brief this describes the possible choices for degree being pursued by a user
# @note NA will be used for a professor
degree_choices = [
    ('BTECH', 'Bachelors of Technology'),
    ('DUAL', 'Dual Degree'),
    ('BS', 'Bachelors of Sciences'),
    ('BDES', 'Bachelors of Design'),
    ('MTECH', 'Masters of Technology'),
    ('MTECH-PHD', 'Masters of Technology with PhD'),
    ('MDES', 'Masters of Design'),
    ('MPHIL', 'Masters of Philosophy'),
    ('MMGT', 'Masters of Management'),
    ('EMBA', 'Masters of Business Administration'),
    ('MSC', 'Masters of Sciences'),
    ('MSC-PHD', 'Masters of Sciences with PhD'),
    ('PHD', 'Doctors of Philosophy'),
    # The last option is only for professor
    ('NA', 'Not Applicable')
]

## @brief this describes the possible choices for current year of a user
# @note NA will be used for a professor
year_choices = [
    ('F', 'First'),
    ('S', 'Second'),
    ('T', 'Third'),
    ('FO', 'Fourth'),
    ('FI', 'Fifth'),
    # The last option is for professor
    ('NA', 'Not Applicable')
]

class User(models.Model):
    """!@brief Model for User 
    @param name the name of the user
    @param ldapid the ldap id of the user associated with the IITB account
    @param gender this is gender of the user with 2 options - M and F
    @param department this is the department of the user with possible options - ('AE', 'BSBE', 'CHE', 'CHM', 'CE', 'CSE', 'ES', 'EE', 'ESE', 'CESE', 'HSS', 'IDC', 'MTH', 'ME', 'MEMS', 'PHY')
    @param bio this is a brief description of the user (optional)
    @param student_professor this is a boolean variable representing whether the user is a student or a professorr
    @param degree this is the degree of the student user among the following options - ('BTECH', 'DUAL', 'BS', 'BDES', 'MTECH', 'MTECH-PHD', 'MDES', 'MPHIL', 'MMGT', 'EMBA', 'MSC', 'MSC-PHD', 'PHD')
    @param year this is the year of the student user among - ('F', 'S', 'T', 'FO', 'FI')
    @param specialization this applies to students pursuing higher studies as well as professors
    @param password this is the password associated with the user in our app
    @param subscribed_topics This represents the list of topics subscribed with the user. Since each user can be subscribed with multiple topics and each topic can be associated to multiple users, there is a many-to-many relationship between model User and Topic which is identified by the ManyToManyField option
    @param totalvotes this is an integer describing the total cumulative votes of a user (can be both positive or negative)
    """
    name = models.CharField(max_length=100)
    ldapid = models.CharField(max_length=100, unique=True)
    gender = models.CharField(max_length=1, choices=gender_choices, default='M')
    department = models.CharField(max_length=4, choices=department_choices, default='CSE')
    bio = models.CharField(max_length=1000)
    # True refers to a student while False refers to a professor
    student_professor = models.BooleanField(default=True)
    degree = models.CharField(max_length=9, choices=degree_choices, default='NA')
    year = models.CharField(max_length=2, choices=year_choices, default='NA')
    # Only for professors or post graduate students as well
    specialization = models.CharField(max_length=100, blank=True)
    # Can be any string
    password = models.CharField(max_length=20)
    # Should be a list of topics
    subscribed_topics = models.ManyToManyField(Topic)
    totalvotes = models.IntegerField(default=0)

    def __str__(self):
        return self.name

class Question(models.Model):
    """!@brief Model for Question
    @param user each question can be asked by a unique user but a user can ask multiple questions thus implying a one-to-many relationship between User and Question which is represented by using User as foreignkey in Question
    @param username the name of the user who asked the question
    @param userdepartment the department of the user who asked the question
    @param userbio bio of the user who asked the question
    @param userdegree the degree of the user who asked the question
    @param userspecialization the specialization of the user who asked the question
    @param topics each question can be tagged by multiple topics and each topic may have multiple questions associated with it
    @param question the main problem statement
    @param description the detailed description of the problem statement
    @param timestamp the timestamp when the question was last modified. By setting auto_now paramter to be True we automatically ensure that the timestamp will be updated whenever the question entry is saved
    """
    user = models.ForeignKey(User)
    username = models.CharField(max_length=100, blank=True)
    userdepartment = models.CharField(max_length=4, choices=department_choices, default='CSE')
    userbio = models.CharField(max_length=1000, blank=True)
    userdegree = models.CharField(max_length=9, choices=degree_choices, default='NA')
    userspecialization = models.CharField(max_length=100, blank=True)
    topics = models.ManyToManyField(Topic)
    question = models.CharField(max_length=500, unique=True)
    description = models.CharField(max_length=5000)
    numAnswers = models.IntegerField(default=0)
    timestamp = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.question

class Answer(models.Model):
    """!@brief Model for Answer
    @param user each answer will be answered by a user but a user can give many answers hence the foreignkey
    @param username the name of the user who gave the answer
    @param userdepartment the department of the user who gave the answer
    @param userbio bio of the user who gave the answer
    @param userdegree the degree of the user who gave the answer
    @param userspecialization the specialization of the user who gave the answer
    @param content the actual answer
    @param question each answer must be in response to a question but a question can have many answers hence the one-to-many relationship between these models
    @param votes an integer specifying the cumulative votes of an answer by combining upvotes with downvotes
    @param timestamp last modified timestamp for the answer
    """
    user = models.ForeignKey(User)
    username = models.CharField(max_length=100, blank=True)
    userdepartment = models.CharField(max_length=4, choices=department_choices, default='CSE')
    userbio = models.CharField(max_length=1000, blank=True)
    userdegree = models.CharField(max_length=9, choices=degree_choices, default='NA')
    userspecialization = models.CharField(max_length=100, blank=True)
    content = models.CharField(max_length=10000)
    question = models.ForeignKey(Question)
    votes = models.IntegerField(default=0)
    timestamp = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.content

class Vote(models.Model):
    """!@brief Model for Vote
    @param upvote_downvote boolean variable such that True denotes an upvote while False denotes a downvote
    @param user the user who gave the vote
    @param answer the answer for which the vote was given
    """
    #True denotes upvote while false denotes downvote
    upvote_downvote = models.BooleanField(default=True)
    user = models.ForeignKey(User)
    answer = models.ForeignKey(Answer)

    def __str__(self):
        if self.upvote_downvote == True:
            return "Upvote"
        return "Downvote"
            
class Notification(models.Model):
    """!@brief Model for Notification
    @param receiver the user for which the notification needs to be shown; either an answer has been submitted to his question or his answer has been upvoted/downvoted
    @param sender the user who has answered or voted
    @param sendername name of the user who answered or voted
    @param question the question for which the change has happened
    @param code this is an integer field with 1 denoting upvote, 2 denoting downvote and 3 denoting that an answer was posted
    @param timestamp last modified timestamp for the notification
    """
    receiver = models.ForeignKey(User, related_name='receiver')
    sender = models.ForeignKey(User, related_name='sender')
    sendername = models.CharField(max_length=100, blank=True)
    question = models.ForeignKey(Question)
    # 1 stands for upvote, 2 stands for downvote, 3 stands for post answer
    code = models.IntegerField(default=1)
    timestamp = models.DateTimeField(auto_now=True)

    def __str__(self):
        if self.code == 1:
            return "Upvoted"
        elif self.code == 2:
            return "Downvoted"
        else:
            return "Answer Posted"
