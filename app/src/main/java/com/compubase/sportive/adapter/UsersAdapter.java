package com.compubase.sportive.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.compubase.sportive.R;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.model.UsersJoinsResponse;
import com.compubase.sportive.model.UsersModel;
import com.compubase.sportive.ui.activity.CenterDetailsActivity;
import com.compubase.sportive.ui.activity.SendActivity;
import com.compubase.sportive.ui.fragment.UsersFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHlder> {

    private Context context;
    private List<Center> usersModelsist;


    public UsersAdapter(List<Center> usersModelsist) {
        this.usersModelsist = usersModelsist;
    }

    @NonNull
    @Override
    public ViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_design, viewGroup, false);
        return new ViewHlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHlder viewHlder, int i) {

        final Center user = usersModelsist.get(i);

        viewHlder.name.setText(user.getName());

        Glide.with(context).load(user.getImages()).placeholder(R.drawable.bg_sportive).into(viewHlder.img);

        viewHlder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,SendActivity.class);
                intent.putExtra("id_user",user.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersModelsist != null ? usersModelsist.size():0;
    }


    class ViewHlder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img;

        ViewHlder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_name);
            img = itemView.findViewById(R.id.user_img);

        }
    }
}
