package com.example.iitbqa.presentation.home.post_question;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.iitbqa.AuthManager;
import com.example.iitbqa.IITBQA;
import com.example.iitbqa.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PostQuestionFragment extends Fragment implements PostQuestionContract.View {

    @BindView(R.id.et_ques)
    EditText etQuest;

    @BindView(R.id.et_desc)
    EditText etDesc;

    @BindView(R.id.btn_topics)
    Button btnTopics;

    @BindView(R.id.ll_topic_select)
    LinearLayout llTopicSelect;

    @BindView(R.id.btn_post)
    Button btnPost;

    @BindView(R.id.rootView)
    CoordinatorLayout rootView;

    String question = "";
    String description = "";
//    String question = "";

    private static PostQuestionFragment postQuestionFragment = new PostQuestionFragment();

    public static PostQuestionFragment getInstance() {
        return postQuestionFragment;
    }

    @Inject
    PostQuestionContract.Presenter presenter;

    @Inject
    AuthManager authManager;

    String[] topicList;
    boolean[] checkedItems;
    List<Integer> checkedTopics = new ArrayList<>();
//    List<String> selectedTopics = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((IITBQA)getActivity().getApplication()).createPostQuestionComponent().inject(this);
        topicList = authManager.getTopicList();
        checkedItems = new boolean[topicList.length];
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_question_page, container, false);
        ButterKnife.bind(this, view);

        btnTopics.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select Topics");
            builder.setMultiChoiceItems(topicList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked) {
                        if (!checkedTopics.contains(which)) {
                            checkedTopics.add(which);
                        }
                    }
                    else {
                        int j=0;
                        for(int i: checkedTopics) {
                            if (i == which) {
                                checkedTopics.remove(j);
                                break;
                            }
                            j++;
                        }
                    }
                }
            });

            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    for (int i: checkedTopics) {
//                        selectedTopics.add(topicList[i]);
//                    }
                    Toast.makeText(getContext(), String.valueOf(checkedTopics.size()), Toast.LENGTH_LONG).show();
                    monitorPostButtonStatus(question, description, checkedTopics);


                }
            });
            builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        etQuest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                question =  s.toString();
                monitorPostButtonStatus(question, description, checkedTopics);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                description = s.toString();
                monitorPostButtonStatus(question, description, checkedTopics);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnPost.setOnClickListener(v-> {
            List<Integer> finalTopics = new ArrayList<>();
            for (int i: checkedTopics) {
                finalTopics.add(i+1);
            }
            presenter.postQuestion(question, description, finalTopics);
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    public void onStop() {
        presenter.detachView();
        super.onStop();
    }


    @Override
    public void showInternetError() {

    }

    @Override
    public void showSnackBar(String message) {

    }

    void monitorPostButtonStatus(String question, String desc, List<Integer> topics) {
        if (!question.isEmpty() && !desc.isEmpty() && !topics.isEmpty()) {
            btnPost.setBackgroundResource(R.drawable.rounded_button_red);
            btnPost.setEnabled(true);
        }
        else {
            btnPost.setBackgroundResource(R.drawable.rounded_button_red_disable);
            btnPost.setEnabled(false);
        }
    }

    @Override
    public void showSuccess() {
        Snackbar.make(rootView, "Question Posted Successfully", Snackbar.LENGTH_LONG)
                .show();
        etDesc.setText("");
        etQuest.setText("");
        checkedTopics.clear();
    }
}
