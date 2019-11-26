package com.example.iitbqa.presentation.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.iitbqa.AuthManager;
import com.example.iitbqa.IITBQA;
import com.example.iitbqa.R;
import com.example.iitbqa.data.models.User;
import com.example.iitbqa.presentation.home.feed.FeedFragment;
import com.example.iitbqa.presentation.home.notification.NotificationFragment;
import com.example.iitbqa.presentation.home.post_question.PostQuestionFragment;
import com.example.iitbqa.presentation.home.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.bn_home)
    BottomNavigationView bnHome;

    @BindView(R.id.placeholder)
    FrameLayout placeHolder;

    Fragment selectedFragment;
    String TAG;

    @Inject
    AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ((IITBQA) getApplication()).getAppComponent().inject(this);

//        authManager.saveUser(new User());


        bnHome.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_feed:
                        selectedFragment = FeedFragment.getInstance();
                        TAG = FeedFragment.class.getSimpleName();
                        break;
                    case R.id.action_post:
                        selectedFragment = PostQuestionFragment.getInstance();
                        TAG = PostQuestionFragment.class.getSimpleName();
                        break;
                    case R.id.action_profile:
                        selectedFragment = ProfileFragment.getInstance();
                        TAG = ProfileFragment.class.getSimpleName();
                        break;
                    case R.id.action_notif:
                        selectedFragment = NotificationFragment.getInstance();
                        TAG = NotificationFragment.class.getSimpleName();
                        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.placeholder, selectedFragment, TAG);
                transaction.commit();

                return true;
            }
        });

        bnHome.setSelectedItemId(R.id.action_feed);


    }
}
