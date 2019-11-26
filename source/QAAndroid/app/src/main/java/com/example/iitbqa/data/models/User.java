package com.example.iitbqa.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("ldapid")
    private String ldapId;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("department")
    private String department;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("subscribed_topics")
    private List<Integer> subscribedTopics;

    @Expose
    @SerializedName("bio")
    private String bio;

    @Expose
    @SerializedName("degree")
    private String degree;

    @Expose
    @SerializedName("year")
    private String year;

    @Expose
    @SerializedName("totalvotes")
    private int upvotes;

    @Expose
    @SerializedName("specialization")
    private String specialization;


    public User(int id, String ldapId, String name, String department, String password, List<Integer> subscribedTopics, String bio, String degree, String year, int upvotes, String specialization) {
        this.id = id;
        this.ldapId = ldapId;
        this.name = name;
        this.department = department;
        this.password = password;
        this.subscribedTopics = subscribedTopics;
        this.bio = bio;
        this.degree = degree;
        this.year = year;
        this.upvotes = upvotes;
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLdapId() {
        return ldapId;
    }

    public void setLdapId(String ldapId) {
        this.ldapId = ldapId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getSubscribedTopics() {
        return subscribedTopics;
    }

    public void setSubscribedTopics(List<Integer> subscribedTopics) {
        this.subscribedTopics = subscribedTopics;
    }
}
