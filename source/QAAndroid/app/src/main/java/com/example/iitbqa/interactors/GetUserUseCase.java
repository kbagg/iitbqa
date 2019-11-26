package com.example.iitbqa.interactors;

import com.example.iitbqa.data.models.User;
import com.example.iitbqa.data.repository.UserRepository;

import java.util.Map;

import io.reactivex.Observable;

public class GetUserUseCase implements BaseUseCase<User> {

    private final UserRepository userRepository;

    public GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Observable<User> execute(Map<?, ?> queryMap) {
        return null;
    }
}
