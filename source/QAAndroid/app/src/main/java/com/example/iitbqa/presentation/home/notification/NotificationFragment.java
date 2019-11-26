package com.example.iitbqa.presentation.home.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iitbqa.AuthManager;
import com.example.iitbqa.Constants;
import com.example.iitbqa.IITBQA;
import com.example.iitbqa.R;
import com.example.iitbqa.data.ApiService;
import com.example.iitbqa.data.models.NotificationModel;
import com.example.iitbqa.presentation.home.feed.FeedFragment;
import com.example.iitbqa.presentation.home.feed.FeedListAdapter;
import com.example.iitbqa.presentation.question.QuestionActivity;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

public class NotificationFragment extends Fragment implements NotificationListAdapter.ClickListener{

    @BindView(R.id.rv_notify)
    RecyclerView rvNotify;

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

    private static NotificationFragment notificationFragment = new NotificationFragment();

    public static NotificationFragment getInstance() {
        return notificationFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((IITBQA)getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        retrofit.create(ApiService.class).getNotification(authManager.getLdapId())
                .subscribeOn(networkScheduler)
                .observeOn(mainScheduler)
                .subscribe(notificationModels -> {
                            rvNotify.setAdapter(new NotificationListAdapter(notificationModels, this, getContext()));
                            rvNotify.setLayoutManager(new LinearLayoutManager(getContext()));
                },
                        error -> {

                        });
    }

    @Override
    public void onNotifClicked(NotificationModel notificationModel) {
        Intent intent = new Intent(getContext(), QuestionActivity.class);
        intent.putExtra(Constants.IntentKeys.QUESTION_ID, notificationModel.getQuestion());
        startActivity(intent);
    }
}
