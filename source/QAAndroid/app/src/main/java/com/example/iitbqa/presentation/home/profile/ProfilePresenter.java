package com.example.iitbqa.presentation.home.profile;

import com.example.iitbqa.interactors.GetUserUseCase;

import io.reactivex.Scheduler;

public class ProfilePresenter implements ProfileContract.Presenter {

    private Scheduler networkScheduler;
    private Scheduler mainScheduler;
    private GetUserUseCase getUserUseCase;

    public ProfilePresenter(Scheduler networkScheduler, Scheduler mainScheduler, GetUserUseCase getUserUseCase) {
        this.networkScheduler = networkScheduler;
        this.mainScheduler =  mainScheduler;
        this.getUserUseCase = getUserUseCase;
    }

    @Override
    public void attachView(ProfileContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
