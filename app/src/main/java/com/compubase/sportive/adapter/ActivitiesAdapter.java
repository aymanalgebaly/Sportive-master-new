package com.compubase.sportive.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.compubase.sportive.R;
import com.compubase.sportive.model.UserActivityActivityResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolderActities> {

    private Context context;
    private List<UserActivityActivityResponse>userActivityList;

    public ActivitiesAdapter(List<UserActivityActivityResponse> userActivityList) {
        this.userActivityList = userActivityList;
    }

    public ActivitiesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderActities onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activites_design, viewGroup, false);
        return new ViewHolderActities(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderActities viewHolderActities, int i) {

        UserActivityActivityResponse userActivityActivityResponse = userActivityList.get(i);

        viewHolderActities.name.setText(userActivityActivityResponse.getName());
        viewHolderActities.title.setText(userActivityActivityResponse.getType());
        viewHolderActities.txt.setText(userActivityActivityResponse.getMessage());

        Glide.with(context).load(userActivityActivityResponse.getImages()).placeholder(R.drawable.user_defualt_img).into(viewHolderActities.imageView);

    }

    @Override
    public int getItemCount() {
        return userActivityList != null ? userActivityList.size():0;
    }

    public void setData(List<UserActivityActivityResponse> activitesModels) {
        this.userActivityList = activitesModels;
    }

    public class ViewHolderActities extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,txt,title;
        public ViewHolderActities(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_activity_design);
            name = itemView.findViewById(R.id.name_center_activity);
            txt = itemView.findViewById(R.id.txt_center_activity);
            title = itemView.findViewById(R.id.title_center_activity);
        }
    }
}
