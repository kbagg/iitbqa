package com.example.iitbqa.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.iitbqa.AuthManager;
import com.example.iitbqa.IITBQA;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class AppModule {

    private IITBQA app;

    public AppModule(IITBQA app) {
        this.app = app;
    }

    @Provides
    @Singleton
    IITBQA provideIITBQA() {
        return app;
    }

    @Provides
    @Singleton
    Context provideAppContext(IITBQA app) {
        return app.getApplicationContext();
    }

    @Provides
    @Named("NetworkThread")
    @Singleton
    Scheduler providesNetworkThreadScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Named("MainThread")
    @Singleton
    Scheduler providesMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Named("ComputationThread")
    @Singleton
    Scheduler providesComputationThreadScheduler() {
        return Schedulers.computation();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides
    @Singleton
    AuthManager provideAuthManager(SharedPreferences sharedPreferences, Gson gson) {
        return new AuthManager(sharedPreferences, gson);
    }
}
