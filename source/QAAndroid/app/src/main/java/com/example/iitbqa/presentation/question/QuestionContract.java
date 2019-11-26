package com.example.iitbqa.presentation.question;

import com.example.iitbqa.data.models.PostAnswerRequest;
import com.example.iitbqa.data.models.QuestionResponse;
import com.example.iitbqa.presentation.BasePresenter;
import com.example.iitbqa.presentation.BaseView;

public interface QuestionContract {
    interface View extends BaseView {

        void displayQuestion(QuestionResponse questionResponse);
    }

    interface Presenter extends BasePresenter<QuestionContract.View> {

        void getQuestion(int id);

        void postQuestion(PostAnswerRequest postAnswerRequest);

        void addVote(int id, boolean isUpvote);
    }

}
