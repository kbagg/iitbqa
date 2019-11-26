package com.example.iitbqa.interactors;

import com.example.iitbqa.Constants;
import com.example.iitbqa.data.models.PostAnswerRequest;
import com.example.iitbqa.data.models.QuestionResponse;
import com.example.iitbqa.data.repository.QuestionRepository;

import java.util.Map;

import io.reactivex.Observable;

public class PostAnswerUseCase implements BaseUseCase<QuestionResponse> {

    private final QuestionRepository questionRepository;

    public PostAnswerUseCase(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Observable<QuestionResponse> execute(Map<?, ?> queryMap) {
        PostAnswerRequest postAnswerRequest = (PostAnswerRequest) queryMap.get(Constants.MapKeys.POST_ANSWER);
        return questionRepository.postAnswer(postAnswerRequest);
    }
}
