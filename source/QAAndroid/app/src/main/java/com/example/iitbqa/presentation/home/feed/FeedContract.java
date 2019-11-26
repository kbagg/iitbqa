package com.example.iitbqa.presentation.home.feed;

import com.example.iitbqa.data.models.FeedQuestionModel;
import com.example.iitbqa.presentation.BasePresenter;
import com.example.iitbqa.presentation.BaseView;

import java.util.List;

public interface FeedContract {
    interface View extends BaseView {

        void showFeedOnView(List<FeedQuestionModel> feedQuestionModelList);
    }

    interface Presenter extends BasePresenter<FeedContract.View> {
        void getFeed(boolean fromRemote);
    }
}
