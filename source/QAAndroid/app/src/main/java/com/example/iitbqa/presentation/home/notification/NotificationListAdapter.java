package com.example.iitbqa.presentation.home.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iitbqa.R;
import com.example.iitbqa.data.models.NotificationModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    List<NotificationModel> notificationModelList;
    NotificationListAdapter.ClickListener listener;
    Context context;

    public NotificationListAdapter(List<NotificationModel> notificationModels, ClickListener listener, Context context) {
        this.notificationModelList = notificationModels;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notification_card, parent, false);
        return new NotificationListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindViews(notificationModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public interface ClickListener {
        void onNotifClicked(NotificationModel notificationModel);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_notif)
        TextView tvNotif;

        @BindView(R.id.ll_notif)
        LinearLayout llNotif;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            llNotif.setOnClickListener(
                    v -> listener.onNotifClicked(notificationModelList.get(getAdapterPosition()))
            );
        }

        public void bindViews(NotificationModel notificationModel) {
            String action = "";
            if (notificationModel.getCode() == 1) {
                action = notificationModel.getSenderName() + " Upvoted Your answer";
            } else if (notificationModel.getCode() == 2) {
                action = notificationModel.getSenderName() + " Downvoted Your answer";
            } else if (notificationModel.getCode() == 3) {
                action = notificationModel.getSenderName() + " Posted an answer for your question";
            }

            tvNotif.setText(action);
        }
    }
}
