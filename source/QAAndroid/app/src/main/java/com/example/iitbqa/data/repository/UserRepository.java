package com.example.iitbqa.data.repository;

import android.content.SharedPreferences;

import com.example.iitbqa.AuthManager;
import com.example.iitbqa.data.ApiService;
import com.example.iitbqa.data.models.User;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class UserRepository {

    private final Retrofit retrofit;
    private final SharedPreferences sharedPreferences;
    private final AuthManager authManager;


    public UserRepository(Retrofit retrofit, SharedPreferences sharedPreferences, AuthManager authManager) {
        this.retrofit = retrofit;
        this.sharedPreferences = sharedPreferences;
        this.authManager = authManager;
    }

    public Observable<User> loginUser(String ldap, String password) {
        return retrofit.create(ApiService.class).getUser(ldap, password)
                .flatMap(user -> {
                    authManager.saveUser(user);
                    authManager.setUserLoggedIn(true);
                    return Observable.just(user);
                });
    }

    public Observable<User> signupUser(User user) {
        return retrofit.create(ApiService.class).addUser(user)
                .flatMap(user1 -> {
                   authManager.saveUser(user);
                   authManager.setUserLoggedIn(true);
                   return Observable.just(user1);
                });
    }
}
