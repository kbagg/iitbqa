package com.example.iitbqa.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpvoteRequest {

    @SerializedName("upvote_downvote")
    @Expose
    private boolean upvote;

    @SerializedName("user")
    @Expose
    private int userId;

    @SerializedName("answer")
    @Expose
    private int answerId;

    public UpvoteRequest(boolean upvote, int userId, int answerId) {
        this.upvote = upvote;
        this.userId = userId;
        this.answerId = answerId;
    }

    public boolean isUpvote() {
        return upvote;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}
