package com.example.iitbqa;

import android.app.Application;

import com.example.iitbqa.di.component.AppComponent;
import com.example.iitbqa.di.component.DaggerAppComponent;
import com.example.iitbqa.di.component.FeedComponent;
import com.example.iitbqa.di.component.PostQuestionComponent;
import com.example.iitbqa.di.component.QuestionComponent;
import com.example.iitbqa.di.component.UserComponent;
import com.example.iitbqa.di.modules.AppModule;
import com.example.iitbqa.di.modules.FeedModule;
import com.example.iitbqa.di.modules.NetworkModule;
import com.example.iitbqa.di.modules.PostQuestionModule;
import com.example.iitbqa.di.modules.QuestionModule;
import com.example.iitbqa.di.modules.UserModule;

public class IITBQA extends Application {

    private AppComponent appComponent;
    private UserComponent userComponent;
    private FeedComponent feedComponent;
    private PostQuestionComponent postQuestionComponent;
    private QuestionComponent questionComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        createAppComponent(HttpUtils.getBaseUrl());
    }


    public void createAppComponent(String baseUrl) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(baseUrl))
                .build();
    }

    public void releaseAppComponent() {
        appComponent = null;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public UserComponent createUserComponent() {
        userComponent = appComponent.plus(new UserModule());
        return userComponent;
    }

    public void releaseUserComponent() {
        userComponent = null;
    }

    public FeedComponent createFeedComponent() {
        feedComponent = appComponent.plus(new FeedModule());
        return feedComponent;
    }

    public void releaseFeedComponent() {
        feedComponent = null;
    }

    public PostQuestionComponent createPostQuestionComponent() {
        postQuestionComponent = appComponent.plus(new PostQuestionModule());
        return postQuestionComponent;
    }

    public void releasePostQuestionComponent() {
        postQuestionComponent = null;
    }

    public QuestionComponent createQuestionComponent() {
        questionComponent = appComponent.plus(new QuestionModule());
        return questionComponent;
    }

    public void releaseQuestionComponent() {
        questionComponent = null;
    }
}
