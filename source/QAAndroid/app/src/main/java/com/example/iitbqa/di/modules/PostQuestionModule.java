package com.example.iitbqa.di.modules;

import com.example.iitbqa.data.repository.QuestionRepository;
import com.example.iitbqa.interactors.PostQuestionUseCase;
import com.example.iitbqa.presentation.home.post_question.PostQuestionContract;
import com.example.iitbqa.presentation.home.post_question.PostQuestionPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class PostQuestionModule {

    @Provides
    PostQuestionContract.Presenter providePostQuestionPresenter(@Named("NetworkThread") Scheduler networkScheduler,
                                                                @Named("MainThread") Scheduler mainScheduler,
                                                                PostQuestionUseCase postQuestionUseCase) {
        return new PostQuestionPresenter(networkScheduler, mainScheduler, postQuestionUseCase);
    }

    @Provides
    PostQuestionUseCase providePostQuestionUseCase(QuestionRepository questionRepository) {
        return new PostQuestionUseCase(questionRepository);
    }
}
