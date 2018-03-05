package com.stoyanivanov.tastethat.view_utils.recyclerview_utils.my_achievements_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.stoyanivanov.tastethat.R;
import com.stoyanivanov.tastethat.db.models.Achievement;
import com.stoyanivanov.tastethat.TasteThatApplication;
import com.stoyanivanov.tastethat.view_utils.custom_views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by stoyan-ivanov on 25.11.17.
 */

public class MyAchievementViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_achievement) CircleImageView ivAchievement;
    @BindView(R.id.tv_achievement_name) CustomTextView ctvAchievementName;

    public MyAchievementViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bind(Achievement achievement) {
        ctvAchievementName.setText(achievement.getName());
        Glide.with(TasteThatApplication.getStaticContext())
                .load(achievement.getImageUrl())
                .fitCenter()
                .into(ivAchievement);
    }
}
