package com.example.iitbqa.di.modules;

import com.example.iitbqa.AuthManager;
import com.example.iitbqa.data.repository.QuestionRepository;
import com.example.iitbqa.interactors.AddVoteUseCase;
import com.example.iitbqa.interactors.GetQuestionUseCase;
import com.example.iitbqa.interactors.PostAnswerUseCase;
import com.example.iitbqa.interactors.PostQuestionUseCase;
import com.example.iitbqa.presentation.home.post_question.PostQuestionContract;
import com.example.iitbqa.presentation.home.post_question.PostQuestionPresenter;
import com.example.iitbqa.presentation.question.QuestionContract;
import com.example.iitbqa.presentation.question.QuestionPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class QuestionModule {

    @Provides
    QuestionContract.Presenter provideQuestionContract(@Named("NetworkThread") Scheduler networkScheduler,
                                                       @Named("MainThread") Scheduler mainScheduler,
                                                       GetQuestionUseCase getQuestionUseCase,
                                                       PostAnswerUseCase postAnswerUseCase,
                                                       AddVoteUseCase addVoteUseCase,
                                                       AuthManager authManager) {
        return new QuestionPresenter(networkScheduler, mainScheduler, getQuestionUseCase, postAnswerUseCase, addVoteUseCase, authManager);
    }

    @Provides
    GetQuestionUseCase provideGetQuestionUseCase(QuestionRepository questionRepository) {
        return new GetQuestionUseCase(questionRepository);
    }

    @Provides
    PostAnswerUseCase providePostAnswerUseCase(QuestionRepository questionRepository) {
        return new PostAnswerUseCase(questionRepository);
    }

    @Provides
    AddVoteUseCase provideAddVoteUseCase(QuestionRepository questionRepository) {
        return new AddVoteUseCase(questionRepository);
    }
}
