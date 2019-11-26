package com.example.iitbqa.interactors;

import java.util.Map;

import io.reactivex.Observable;

public interface BaseUseCase<T> {
    Observable<T> execute(Map<?, ?> queryMap);
}
