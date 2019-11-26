package com.example.iitbqa.presentation;

public interface BasePresenter<T extends BaseView> {
    void attachView(T t);
    void detachView();
}
