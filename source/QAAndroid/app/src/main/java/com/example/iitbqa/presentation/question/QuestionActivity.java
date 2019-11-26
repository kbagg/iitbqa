package com.example.iitbqa.presentation.question;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iitbqa.Constants;
import com.example.iitbqa.IITBQA;
import com.example.iitbqa.R;
import com.example.iitbqa.data.models.PostAnswerRequest;
import com.example.iitbqa.data.models.QuestionResponse;
import com.example.iitbqa.presentation.home.feed.FeedListAdapter;

import javax.inject.Inject;

public class QuestionActivity extends AppCompatActivity implements QuestionContract.View, AnswerAdapter.ClickListener,
AnswerDialog.ReturnInterface{

    @BindView(R.id.rv_answer)
    RecyclerView rvAnswer;

    @BindView(R.id.tv_question)
    TextView tvQuestion;

    @BindView(R.id.btn_post_answer)
    Button btnPostAnswer;


    @Inject
    QuestionContract.Presenter presenter;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ButterKnife.bind(this);

        ((IITBQA)getApplication()).createQuestionComponent().inject(this);

        id = getIntent().getIntExtra(Constants.IntentKeys.QUESTION_ID, 0);

        btnPostAnswer.setOnClickListener( v -> {
            AnswerDialog answerDialog = new AnswerDialog();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.IntentKeys.QUESTION_ID, id);
            answerDialog.setArguments(bundle);
//            orderCancelDialog.setCancelable(false);
            answerDialog.show(getSupportFragmentManager(), "answer_dialog");
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
        presenter.getQuestion(id);
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        super.onStop();
    }

    @Override
    public void displayQuestion(QuestionResponse questionResponse) {
//        Toast.makeText(this, "salkdad", Toast.LENGTH_LONG).show();
        tvQuestion.setText(questionResponse.getQuestion().get(0).getQues());
        rvAnswer.setAdapter(new AnswerAdapter(questionResponse.getAnswerList(), questionResponse.getVoteList(), this, this));
        rvAnswer.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showInternetError() {

    }

    @Override
    public void showSnackBar(String message) {

    }


    @Override
    public void postAnswer(PostAnswerRequest postAnswerRequest) {
        presenter.postQuestion(postAnswerRequest);
    }

    @Override
    public void upvoteClicked(int id) {
        presenter.addVote(id, true);
    }

    @Override
    public void downvoteClicked(int id) {
        presenter.addVote(id, false);
    }
}
