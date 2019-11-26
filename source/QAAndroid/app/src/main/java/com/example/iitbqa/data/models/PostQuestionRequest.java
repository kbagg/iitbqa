package com.example.iitbqa.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostQuestionRequest {

    @SerializedName("user")
    @Expose
    private int userId;

    @SerializedName("topics")
    @Expose
    private List<Integer> topics;

    @SerializedName("question")
    @Expose
    private String ques;

    @SerializedName("description")
    @Expose
    private String description;

    public PostQuestionRequest(int userId, List<Integer> topics, String ques, String description) {
        this.userId = userId;
        this.topics = topics;
        this.ques = ques;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getTopics() {
        return topics;
    }

    public void setTopics(List<Integer> topics) {
        this.topics = topics;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
