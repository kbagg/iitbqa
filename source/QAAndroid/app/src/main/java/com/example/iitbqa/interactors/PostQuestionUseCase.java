package com.example.iitbqa.interactors;

import com.example.iitbqa.Constants;
import com.example.iitbqa.data.models.Question;
import com.example.iitbqa.data.repository.QuestionRepository;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class PostQuestionUseCase implements BaseUseCase<List<Question>> {

    private QuestionRepository questionRepository;

    public PostQuestionUseCase(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Observable<List<Question>> execute(Map<?, ?> queryMap) {
        String question = (String) queryMap.get(Constants.MapKeys.QUESTION);
        String description = (String)queryMap.get(Constants.MapKeys.DESCRIPTION);
        List<Integer> topics = (List<Integer>) queryMap.get(Constants.MapKeys.TOPICS);
        return questionRepository.postQuestion(question, description, topics);
    }
}
