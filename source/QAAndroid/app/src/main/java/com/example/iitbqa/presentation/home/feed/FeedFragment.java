package com.example.iitbqa.presentation.home.feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iitbqa.Constants;
import com.example.iitbqa.IITBQA;
import com.example.iitbqa.R;
import com.example.iitbqa.data.models.FeedQuestionModel;
import com.example.iitbqa.presentation.question.QuestionActivity;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedFragment extends Fragment implements FeedContract.View, FeedListAdapter.ClickListener{

    private static FeedFragment feedFragment = new FeedFragment();
    @BindView(R.id.rv_feed)
    RecyclerView rvFeed;

    @Inject
    FeedContract.Presenter presenter;

    public static FeedFragment getInstance() {
        return feedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((IITBQA)getActivity().getApplication()).createFeedComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
//        presenter.start(orderId);
        presenter.getFeed(true);
    }

    @Override
    public void onStop() {
        presenter.detachView();
        super.onStop();
    }

    @Override
    public void showInternetError() {

    }

    @Override
    public void showSnackBar(String message) {

    }

    @Override
    public void onQuestionClicked(FeedQuestionModel feedQuestionModel) {
        Intent intent = new Intent(getContext(), QuestionActivity.class);
        intent.putExtra(Constants.IntentKeys.QUESTION_ID, feedQuestionModel.getId());
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        ((IITBQA)getActivity().getApplication()).releaseFeedComponent();
        super.onDestroy();
    }

    @Override
    public void showFeedOnView(List<FeedQuestionModel> feedQuestionModelList) {
        rvFeed.setAdapter(new FeedListAdapter(feedQuestionModelList, this, getContext()));
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
