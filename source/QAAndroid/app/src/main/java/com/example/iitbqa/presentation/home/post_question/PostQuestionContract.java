package com.example.iitbqa.presentation.home.post_question;

import com.example.iitbqa.presentation.BasePresenter;
import com.example.iitbqa.presentation.BaseView;

import java.util.List;

public interface PostQuestionContract {
    interface View extends BaseView {

        void showSuccess();
    }

    interface Presenter extends BasePresenter<PostQuestionContract.View> {

        void postQuestion(String question, String description, List<Integer> selectedTopics);
    }
}
