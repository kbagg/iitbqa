from django.conf.urls import url

from api import views

urlpatterns = [
    url(r'^AddTopic$', views.AddTopic),
    url(r'^GeneralData$', views.GeneralData),
    url(r'^TopicList$', views.TopicList),
    url(r'^GetQuestion/(?P<ldap>.+)/(?P<questionid>\d+)$', views.GetQuestion),
    url(r'^PostQuestion$', views.PostQuestion),
    url(r'^QuestionList/(?P<ldap>.+)$', views.QuestionList),
    url(r'^PostAnswer$', views.PostAnswer),
    url(r'^GetNotifications/(?P<ldap>.+)$', views.GetNotifications),
    url(r'^AddVote$', views.AddVote),
    url(r'^GetUser/(?P<ldap>.+)/(?P<password>.+)$', views.GetUser),
    url(r'^AddUser$', views.AddUser),
    url(r'^RemoveUser/(?P<ldap>.+)$', views.RemoveUser),
    url(r'^RemoveQuestion/(?P<questionid>\d+)$', views.RemoveQuestion),
    url(r'^RemoveAnswer/(?P<answerid>\d+)$', views.RemoveAnswer),
    url(r'^RemoveVote/(?P<voteid>\d+)$', views.RemoveVote),
]
