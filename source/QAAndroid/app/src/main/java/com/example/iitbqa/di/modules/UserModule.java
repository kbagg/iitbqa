package com.example.iitbqa.di.modules;

import com.example.iitbqa.data.repository.UserRepository;
import com.example.iitbqa.interactors.GetUserUseCase;
import com.example.iitbqa.presentation.home.profile.ProfileContract;
import com.example.iitbqa.presentation.home.profile.ProfilePresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class UserModule {
    @Provides
    ProfileContract.Presenter provideProfilePresenter(@Named("NetworkThread") Scheduler networkScheduler,
                                                         @Named("MainThread")Scheduler mainScheduler,
                                                         GetUserUseCase getUserUseCase) {
        return new ProfilePresenter(networkScheduler, mainScheduler, getUserUseCase);
    }

    @Provides
    GetUserUseCase provideGetUserUseCase(UserRepository userRepository) {
        return new GetUserUseCase(userRepository);
    }
}
