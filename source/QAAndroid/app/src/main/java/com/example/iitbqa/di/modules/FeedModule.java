package com.example.iitbqa.di.modules;

import com.example.iitbqa.data.repository.QuestionRepository;
import com.example.iitbqa.interactors.GetFeedUseCase;
import com.example.iitbqa.interactors.GetUserUseCase;
import com.example.iitbqa.presentation.home.feed.FeedContract;
import com.example.iitbqa.presentation.home.feed.FeedPresenter;
import com.example.iitbqa.presentation.home.profile.ProfileContract;
import com.example.iitbqa.presentation.home.profile.ProfilePresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class FeedModule {

    @Provides
    FeedContract.Presenter provideProfilePresenter(@Named("NetworkThread") Scheduler networkScheduler,
                                                   @Named("MainThread")Scheduler mainScheduler,
                                                   GetFeedUseCase getFeedUseCase) {
        return new FeedPresenter(networkScheduler, mainScheduler, getFeedUseCase);
    }

    @Provides
    GetFeedUseCase provideGetFeed(QuestionRepository questionRepository) {
        return new GetFeedUseCase(questionRepository);
    }
}
