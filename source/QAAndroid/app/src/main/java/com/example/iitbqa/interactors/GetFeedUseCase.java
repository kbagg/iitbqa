package com.example.iitbqa.interactors;

import com.example.iitbqa.Constants;
import com.example.iitbqa.data.models.Question;
import com.example.iitbqa.data.repository.QuestionRepository;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class GetFeedUseCase implements BaseUseCase<List<Question>> {

    private final QuestionRepository questionRepository;

    public GetFeedUseCase(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Observable<List<Question>> execute(Map<?, ?> queryMap) {
        boolean fromRemote = (Boolean) queryMap.get(Constants.MapKeys.FROM_REMOTE);
        return questionRepository.getUserFeed(fromRemote);
    }
}
