package com.example.iitbqa.data.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedQuestionModel {

    int id;
    String user;
    List<String> topicsList;
    String question;
    String description;
    String numAnswers;
    private String userName;
    private String userBio;
    private String userDept;
    private String userDegree;
    private String userSpecialization;
    private Date timestamp;

    String[] topicsArray = {"General", "Computer Science", "Theory", "Systems", "Machine Learning", "Artificial Intelligence", "Cryptography", "Algorithms", "Complexity", "Quantum Physics", "Physics", "Chemistry", "Biology", "Sciences"};

    public FeedQuestionModel(Question question) {
        this.user = question.getUserId();
        this.question = question.getQues();
        this.description = question.getDescription();
        this.numAnswers = question.getNumAnswers() + " have answered this question";
        this.id = question.getId();
        this.userName = question.getUserName();
        this.userBio = question.getUserBio();
        this.userDept = question.getUserDept();
        this.userDegree = question.getUserDegree();
        this.userSpecialization = question.getUserSpecialization();
        this.timestamp = question.getTimestamp();

        List<String> topicList = new ArrayList<>();
        for (int i: question.getTopics()) {
            topicList.add(topicsArray[i-1]);
        }
        this.topicsList = topicList;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    public String getUserDegree() {
        return userDegree;
    }

    public void setUserDegree(String userDegree) {
        this.userDegree = userDegree;
    }

    public String getUserSpecialization() {
        return userSpecialization;
    }

    public void setUserSpecialization(String userSpecialization) {
        this.userSpecialization = userSpecialization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getTopicsList() {
        return topicsList;
    }

    public void setTopicsList(List<String> topicsList) {
        this.topicsList = topicsList;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumAnswers() {
        return numAnswers;
    }

    public void setNumAnswers(String numAnswers) {
        this.numAnswers = numAnswers;
    }
}
