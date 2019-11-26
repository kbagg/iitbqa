package com.example.iitbqa.interactors;

import com.example.iitbqa.Constants;
import com.example.iitbqa.data.models.Question;
import com.example.iitbqa.data.models.QuestionResponse;
import com.example.iitbqa.data.repository.QuestionRepository;

import java.util.Map;

import io.reactivex.Observable;

public class GetQuestionUseCase implements BaseUseCase<QuestionResponse>{

    private final QuestionRepository questionRepository;

    public GetQuestionUseCase(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Observable<QuestionResponse> execute(Map<?, ?> queryMap) {
        int questionId = (Integer) queryMap.get(Constants.MapKeys.QUESTION_ID);
        return questionRepository.getQuestion(questionId);
    }
}
