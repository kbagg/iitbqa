package com.example.iitbqa.presentation.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iitbqa.R;
import com.example.iitbqa.data.models.Answer;
import com.example.iitbqa.data.models.Vote;
import com.example.iitbqa.presentation.home.feed.FeedListAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private List<Answer> answerList;
    List<Vote> voteList;
    private AnswerAdapter.ClickListener listener;
    private Context context;

    public static final String DATE_FORMAT_NOW = "dd MMM";

    public AnswerAdapter(List<Answer> answerList, List<Vote> voteList,  ClickListener listener, Context context) {
        this.answerList = answerList;
        this.voteList = voteList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.answer_card, parent, false);
        return new AnswerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindViews(answerList.get(position));
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public interface ClickListener {
        void upvoteClicked(int id);
        void downvoteClicked(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_user)
        TextView tvUser;

        @BindView(R.id.tv_date)
        TextView tvDate;

        @BindView(R.id.tv_answer)
        TextView tvAnswer;

        @BindView(R.id.ll_upvote)
        LinearLayout llUpvote;

        @BindView(R.id.iv_upvote)
        ImageView ivUpvote;

        @BindView(R.id.tv_upvote)
        TextView tvUpvote;

        @BindView(R.id.iv_downvote)
        ImageView ivDownvote;

        @BindView(R.id.tv_upvoted)
        TextView tvUpvoted;

        @BindView(R.id.cl_upvote)
        ConstraintLayout clUpvote;

        @BindView(R.id.tv_up)
        TextView tvUp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(Answer answer) {
            tvUser.setText(answer.getUserName() + " , " + answer.getUserDegree());
            tvDate.setText("Answered on " + new SimpleDateFormat(DATE_FORMAT_NOW).format(answer.getTimestamp()));
            tvUpvote.setText(String.valueOf(answer.getVotes()));
            tvAnswer.setText(answer.getContent());

            llUpvote.setVisibility(View.VISIBLE);
            clUpvote.setVisibility(View.GONE);


            for (Vote vote: voteList) {
                if (vote.getAnswerId() == answer.getId()) {
                    clUpvote.setVisibility(View.VISIBLE);
                    if (vote.isUpvote()) {
                        tvUpvoted.setText("You have already upvoted this answer");
                    } else {
                        tvUpvoted.setText("You have already downvoted this answer");
                    }
                    tvUp.setText(answer.getVotes() + " upvotes");
                    llUpvote.setVisibility(View.GONE);
                }
            }

            ivUpvote.setOnClickListener(
                    v -> {
                        listener.upvoteClicked(answer.getId());
                    }
            );

            ivDownvote.setOnClickListener(
                    v -> {
                        listener.downvoteClicked(answer.getId());
                    }
            );


        }
    }
}
