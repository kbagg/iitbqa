package com.example.iitbqa.presentation.home.feed;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iitbqa.R;
import com.example.iitbqa.data.models.FeedQuestionModel;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.iitbqa.presentation.question.AnswerAdapter.DATE_FORMAT_NOW;

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {

    List<FeedQuestionModel> feedQuestionModelList;
    FeedListAdapter.ClickListener clickListener;
    Context context;

    public FeedListAdapter(List<FeedQuestionModel> feedQuestionModelList, ClickListener clickListener, Context context) {
        this.feedQuestionModelList = feedQuestionModelList;
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public FeedListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.feed_card, parent, false);
        return new FeedListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedListAdapter.ViewHolder holder, int position) {
        holder.bindViews(feedQuestionModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return feedQuestionModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_user)
        TextView tvUser;

        @BindView(R.id.hsv_topics)
        HorizontalScrollView hsvTopics;

        @BindView(R.id.ll_topics)
        LinearLayout llTopics;

        @BindView(R.id.tv_question)
        TextView tvQuestion;

        @BindView(R.id.tv_desc)
        TextView tvDescription;

        @BindView(R.id.tv_num_ans)
        TextView tvNumAns;

        @BindView(R.id.cl_ques)
        ConstraintLayout clQuestion;

        @BindView(R.id.tv_date)
        TextView tvDate;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            clQuestion.setOnClickListener(
                    v -> clickListener.onQuestionClicked(feedQuestionModelList.get(getAdapterPosition()))
            );
//            tvBtnDetails.setOnClickListener(
//                    v -> clickListener.onDetailsClicked(orderSummaryList.get(getAdapterPosition()).getOrderId())
//            );
        }

        public void bindViews(FeedQuestionModel feedQuestionModel) {
            tvQuestion.setText(feedQuestionModel.getQuestion());
            tvDescription.setText(feedQuestionModel.getDescription());
            tvNumAns.setText(feedQuestionModel.getNumAnswers());
            tvUser.setText(feedQuestionModel.getUserName());
            tvDate.setText("Answered on " + new SimpleDateFormat(DATE_FORMAT_NOW).format(feedQuestionModel.getTimestamp()));

            llTopics.removeAllViews();
            for (String topic : feedQuestionModel.getTopicsList()) {
                TextView textView = new TextView(context);
                textView.setText(topic);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(12, 0, 12, 0);
                textView.setLayoutParams(layoutParams);
                textView.setPadding(8, 8, 8, 8);
                textView.setBackgroundColor(Color.parseColor("#00574B"));
                textView.setTextColor(Color.parseColor("#ffffff"));

                llTopics.addView(textView);
            }
        }
    }

    public interface ClickListener {
        void onQuestionClicked(FeedQuestionModel feedQuestionModel);
    }
}
