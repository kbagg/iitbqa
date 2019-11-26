package com.example.iitbqa.presentation.question;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.iitbqa.AuthManager;
import com.example.iitbqa.Constants;
import com.example.iitbqa.IITBQA;
import com.example.iitbqa.R;
import com.example.iitbqa.data.ApiService;
import com.example.iitbqa.data.models.PostAnswerRequest;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class AnswerDialog extends DialogFragment {

    @BindView(R.id.et_answer)
    EditText etAnswer;

    @BindView(R.id.btn_post)
    Button btnPost;

    @Inject
    AuthManager authManager;

    @Inject
    Retrofit retrofit;

    int questionId;
    String answer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionId = getArguments().getInt(Constants.IntentKeys.QUESTION_ID);
        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        ((IITBQA)getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.answer_dialog, container, false);
        ButterKnife.bind(this, view);

        etAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                answer =  s.toString();
                monitorPostButtonStatus(answer);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnPost.setOnClickListener( v -> {
            PostAnswerRequest postAnswerRequest = new PostAnswerRequest(authManager.getUserId(), answer, questionId);
            ((ReturnInterface)getContext()).postAnswer(postAnswerRequest);
            dismiss();
        });
        return view;
    }

    private void monitorPostButtonStatus(String answer) {
        if (!answer.isEmpty()) {
            btnPost.setBackgroundResource(R.drawable.rounded_button_red);
            btnPost.setEnabled(true);
        }
        else {
            btnPost.setBackgroundResource(R.drawable.rounded_button_red_disable);
            btnPost.setEnabled(false);
        }
    }


    public interface ReturnInterface {
        void postAnswer(PostAnswerRequest postAnswerRequest);
    }
}
