package com.example.iitbqa.presentation.home.profile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iitbqa.AuthManager;
import com.example.iitbqa.IITBQA;
import com.example.iitbqa.R;
import com.example.iitbqa.presentation.home.LoginActivity;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment implements ProfileContract.View{

    private static ProfileFragment profileFragment = new ProfileFragment();

    @Inject
    ProfileContract.Presenter presenter;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_bio)
    TextView tvBio;

    @BindView(R.id.tv_cred)
    TextView tvCred;

    @BindView(R.id.tv_degree)
    TextView tvDegree;

    @BindView(R.id.tv_upvote)
    TextView tvUpvote;

    @BindView(R.id.tv_dept)
    TextView tvDept;


    @BindView(R.id.tv_ldap)
    TextView tvLdap;

    @BindView(R.id.btn_logout)
    Button btnLogout;

    @BindView(R.id.ll_topics)
    LinearLayout llTopics;

    @Inject
    AuthManager authManager;

    public static ProfileFragment getInstance() {
        return profileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((IITBQA)getActivity().getApplication()).createUserComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_page, container, false);
        ButterKnife.bind(this, view);

        tvBio.setText(authManager.getBio());
        tvLdap.setText(authManager.getLdapId());
        tvName.setText(authManager.getName());
        if(authManager.getDegree().isEmpty()) {
            tvDegree.setText(authManager.getSpecialization());
        }
        else {
            tvDegree.setText(authManager.getDegree());
        }
        tvDept.setText(authManager.getDepartment());
        tvUpvote.setText(authManager.getUpvotes() + " Upvotes");



        List<String> topics = authManager.getCustomerTopics();
        llTopics.removeAllViews();
        for (String topic: topics) {
            TextView textView = new TextView(getContext());
            textView.setText(topic);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(12,12,0,0);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(4,4,4,4);
//            textView.setBackgroundColor(Color.parseColor("#808080"));
            textView.setTextColor(Color.parseColor("#000000"));

            llTopics.addView(textView);
        }

        btnLogout.setOnClickListener(v -> {
            authManager.setUserLoggedIn(false);
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void showInternetError() {

    }

    @Override
    public void showSnackBar(String message) {

    }
}
