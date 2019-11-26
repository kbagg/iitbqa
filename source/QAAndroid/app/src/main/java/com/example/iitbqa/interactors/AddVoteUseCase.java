package com.example.iitbqa.interactors;

import com.example.iitbqa.Constants;
import com.example.iitbqa.data.models.QuestionResponse;
import com.example.iitbqa.data.models.UpvoteRequest;
import com.example.iitbqa.data.repository.QuestionRepository;

import java.util.Map;

import io.reactivex.Observable;

public class AddVoteUseCase implements BaseUseCase<QuestionResponse> {

    private final QuestionRepository questionRepository;

    public AddVoteUseCase(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Observable<QuestionResponse> execute(Map<?, ?> queryMap) {
        UpvoteRequest upvoteRequest = (UpvoteRequest) queryMap.get(Constants.MapKeys.ADD_VOTE);
        return questionRepository.addVote(upvoteRequest);
    }
}
