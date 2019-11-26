package com.example.iitbqa.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class SplashData {
    @Expose
    @SerializedName("department_choices")
    Map<String, String> departmentMap;

    @Expose
    @SerializedName("topics")
    List<Topic> topics;

    @Expose
    @SerializedName("degree_choices")
    Map<String, String> degreeMap;

    public Map<String, String> getDepartmentMap() {
        return departmentMap;
    }

    public void setDepartmentMap(Map<String, String> departmentMap) {
        this.departmentMap = departmentMap;
    }

    public Map<String, String> getDegreeMap() {
        return degreeMap;
    }

    public void setDegreeMap(Map<String, String> degreeMap) {
        this.degreeMap = degreeMap;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

}
