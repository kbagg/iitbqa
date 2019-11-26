package com.example.iitbqa;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.iitbqa.data.models.DegreeChoice;
import com.example.iitbqa.data.models.DepartmentChoice;
import com.example.iitbqa.data.models.SplashData;
import com.example.iitbqa.data.models.Topic;
import com.example.iitbqa.data.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.constraintlayout.widget.ConstraintLayout;

public class AuthManager {

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

//    String[] topicsArray
//            = {"General", "Computer Science", "Theory", "Systems", "Machine Learning", "Artificial Intelligence", "Cryptography", "Algorithms", "Complexity", "Quantum Physics", "Physics", "Chemistry", "Biology", "Sciences"};


    public String[] getTopicList() {
//        return topicsArray;

        List<String> topics = new ArrayList<>();

        SplashData splashData = gson.fromJson(sharedPreferences.getString(Constants.PrefKeys.SPLASH, null), SplashData.class);
        List<Topic> topicList = splashData.getTopics();

        for (Topic t: topicList) {
            topics.add(t.getName());
        }
        String[] topicsArray = new String[topics.size()];
        topicsArray = topics.toArray(topicsArray);
        return topicsArray;
    }

    public Map<String, String> getDepartmentChoices() {
        return  gson.fromJson(sharedPreferences.getString(Constants.PrefKeys.SPLASH, null), SplashData.class).getDepartmentMap();
    }

    public Map<String, String> getDegreeChoice() {
        return  gson.fromJson(sharedPreferences.getString(Constants.PrefKeys.SPLASH, null), SplashData.class).getDegreeMap();
    }


    public AuthManager(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    public void saveUser(User user) {
//        List<Integer> subscribedTopics1 = new ArrayList<>();
//        List<Integer> subscribedTopics2 = new ArrayList<>();
//        subscribedTopics1.add(1);
//        subscribedTopics1.add(2);
//        subscribedTopics1.add(7);
//        subscribedTopics2.add(2);
//        subscribedTopics2.add(9);
//        subscribedTopics2.add(10);
//        subscribedTopics2.add(13);
        //subscribedTopics.add(13);
//        User user1 = new User(1, "roshanp", "Roshan", "CSE", "$Money#", subscribedTopics1, "PMRF fellow", "PHD", "F", 10);
//        User user2 = new User(2, "muthumani", "Muthu", "PHY", "NoFan%", subscribedTopics2, "South Indian Guy", "PHD", "F", 10);
        //Log.d("user", user.getBio());
        String userJson = gson.toJson(user, User.class);
        Log.d("user", userJson);
        sharedPreferences.edit().putString(Constants.PrefKeys.USER, userJson).commit();
    }

    public int getUserId() {
        return gson.fromJson(sharedPreferences.getString(Constants.PrefKeys.USER, null), User.class).getId();
    }

    public String getLdapId() {
        return gson.fromJson(sharedPreferences.getString(Constants.PrefKeys.USER, null), User.class).getLdapId();
    }

    public List<String> getCustomerTopics() {
        List<Integer> customerTopics = gson.fromJson(sharedPreferences.getString(Constants.PrefKeys.USER, null), User.class).getSubscribedTopics();
        List<String> topics = new ArrayList<>();
        String[] topicsArray = getTopicList();
        for (int i: customerTopics) {
            topics.add(topicsArray[i-1]);
        }
        return topics;
    }

    public String getBio() {
        return gson.fromJson(sharedPreferences.getString(Constants.PrefKeys.USER, null), User.class).getBio();
    }

    public String getName() {
        return gson.fromJson(sharedPreferences.getString(Constants.PrefKeys.USER, null), User.class).getName();
    }

    public String getDegree() {
        return gson.fromJson(sharedPreferences.getString(Constants.PrefKeys.USER, null), User.class).getDegree();
    }

    public String getDepartment() {
        return gson.fromJson(sharedPreferences.getString(Constants.PrefKeys.USER, null), User.class).getDepartment();
    }

    public int getUpvotes() {
        return gson.fromJson(sharedPreferences.getString(Constants.PrefKeys.USER, null), User.class).getUpvotes();
    }

    public void setUserLoggedIn(boolean loggedin) {
        if (loggedin) {
            sharedPreferences.edit().putBoolean(Constants.PrefKeys.LOGIN, true).commit();
        }
        else {
            sharedPreferences.edit().putBoolean(Constants.PrefKeys.LOGIN, false).commit();
        }
    }

    public boolean getUserLoggedIn() {
        return sharedPreferences.getBoolean(Constants.PrefKeys.LOGIN, false);
    }


    public void setSplashData(SplashData splashData) {

        String splashJson = gson.toJson(splashData, SplashData.class);
        Log.d("splash",splashJson);
        sharedPreferences.edit().putString(Constants.PrefKeys.SPLASH, splashJson).commit();
    }
}
