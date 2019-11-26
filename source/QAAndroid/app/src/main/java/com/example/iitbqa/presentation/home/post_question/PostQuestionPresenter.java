package com.example.iitbqa.presentation.home.post_question;

import android.util.Log;

import com.example.iitbqa.Constants;
import com.example.iitbqa.interactors.PostQuestionUseCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Scheduler;

public class PostQuestionPresenter implements PostQuestionContract.Presenter {

    private final Scheduler mainScheduler;
    private final Scheduler networkScheduler;
    private final PostQuestionUseCase postQuestionUseCase;

    private PostQuestionContract.View view;

    public PostQuestionPresenter(Scheduler networkScheduler, Scheduler mainScheduler, PostQuestionUseCase postQuestionUseCase) {
        this.mainScheduler = mainScheduler;
        this.networkScheduler = networkScheduler;
        this.postQuestionUseCase = postQuestionUseCase;
    }

    @Override
    public void attachView(PostQuestionContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void postQuestion(String question, String description, List<Integer> selectedTopics) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(Constants.MapKeys.QUESTION, question);
        queryMap.put(Constants.MapKeys.DESCRIPTION, description);
        queryMap.put(Constants.MapKeys.TOPICS, selectedTopics);
        postQuestionUseCase.execute(queryMap)
                .subscribeOn(networkScheduler)
                .observeOn(mainScheduler)
                .subscribe(
                        questions -> {
                            view.showSuccess();
                        },
                        error -> {
                            Log.d("error", error.getMessage());
                        }
                );
    }
}
