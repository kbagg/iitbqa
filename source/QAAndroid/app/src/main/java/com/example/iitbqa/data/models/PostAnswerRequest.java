package com.example.iitbqa.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostAnswerRequest {

    @SerializedName("user")
    @Expose
    private int user;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("question")
    @Expose
    private int question;

    public PostAnswerRequest(int user, String content, int question) {
        this.user = user;
        this.content = content;
        this.question = question;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }
}
