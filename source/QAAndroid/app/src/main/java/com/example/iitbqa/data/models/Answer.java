package com.example.iitbqa.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Answer {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user")
    @Expose
    private String userId;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("question")
    @Expose
    private int quesId;

    @SerializedName("votes")
    @Expose
    private int votes;

    @SerializedName("timestamp")
    @Expose
    private Date timestamp;

    @SerializedName("username")
    @Expose
    private String userName;

    @SerializedName("userbio")
    @Expose
    private String userBio;

    @SerializedName("userdepartment")
    @Expose
    private String userDept;

    @SerializedName("userdegree")
    @Expose
    private String userDegree;

    @SerializedName("userspecialization")
    @Expose
    private String userSpecialization;

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

    //    @SerializedName("user")

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuesId() {
        return quesId;
    }

    public void setQuesId(int quesId) {
        this.quesId = quesId;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
