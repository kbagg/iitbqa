package com.example.iitbqa.presentation.home.feed;

import com.example.iitbqa.Constants;
import com.example.iitbqa.data.models.FeedQuestionModel;
import com.example.iitbqa.data.models.Question;
import com.example.iitbqa.interactors.GetFeedUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Scheduler;

public class FeedPresenter implements FeedContract.Presenter {

    private Scheduler networkScheduler;
    private Scheduler mainScheduler;
    GetFeedUseCase getFeedUseCase;

    private FeedContract.View view;


    public FeedPresenter(Scheduler networkScheduler, Scheduler mainScheduler, GetFeedUseCase getFeedUseCase) {
        this.networkScheduler = networkScheduler;
        this.mainScheduler = mainScheduler;
        this.getFeedUseCase = getFeedUseCase;
    }

    @Override
    public void attachView(FeedContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void getFeed(boolean fromRemote) {
        Map<String, Boolean> queryMap = new HashMap<>();
        queryMap.put(Constants.MapKeys.FROM_REMOTE, fromRemote);
        getFeedUseCase.execute(queryMap)
                .subscribeOn(networkScheduler)
                .observeOn(mainScheduler)
                .subscribe(
                        questions -> {
                            List<FeedQuestionModel> feedQuestionModels = new ArrayList<>();
                            for (Question question : questions) {
                                feedQuestionModels.add(new FeedQuestionModel(question));
                            }
                            view.showFeedOnView(feedQuestionModels);
                        },
                        error -> {

                        }
                );
    }
}
