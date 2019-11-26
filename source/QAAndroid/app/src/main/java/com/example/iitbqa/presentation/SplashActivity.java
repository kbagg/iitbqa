package com.example.iitbqa.presentation;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.iitbqa.AuthManager;
import com.example.iitbqa.IITBQA;
import com.example.iitbqa.R;
import com.example.iitbqa.data.ApiService;
import com.example.iitbqa.presentation.home.HomeActivity;
import com.example.iitbqa.presentation.home.LoginActivity;

import javax.inject.Inject;
import javax.inject.Named;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    @Inject
    Retrofit retrofit;

    @Inject
    @Named("NetworkThread")
    Scheduler networkScheduler;

    @Inject
    @Named("MainThread")
    Scheduler mainScheduler;

    @Inject
    AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        ((IITBQA)getApplication()).getAppComponent().inject(this);

        retrofit.create(ApiService.class).getGeneralData()
                .subscribeOn(networkScheduler)
                .observeOn(mainScheduler)
                .subscribe(
                        splashData -> {
                            authManager.setSplashData(splashData);
                            Log.d("splash", "No");
                        },
                        error -> {
                            Log.d("splash", error.getMessage());
                        }
                );
        int j =0;
        while (j< 1800000) {
            j++;
        }

        if (authManager.getUserLoggedIn()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        finish();

    }
}
