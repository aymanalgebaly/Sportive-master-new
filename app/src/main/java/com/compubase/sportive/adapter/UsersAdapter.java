package com.compubase.sportive.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.compubase.sportive.R;
import com.compubase.sportive.model.ActivityListResponse;
import com.compubase.sportive.model.UsersJoinsResponse;
import com.compubase.sportive.ui.activity.SendActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHlder> {

    private Context context;
    private List<UsersJoinsResponse> usersJoinsResponses;

    public UsersAdapter(List<UsersJoinsResponse> usersJoinsResponseList) {
        this.usersJoinsResponses = usersJoinsResponseList;
    }

    public UsersAdapter(Context context) {
        this.context = context;
    }

    private String id_resieved;


    @NonNull
    @Override
    public ViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_design, viewGroup, false);
        return new ViewHlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHlder viewHlder, int i) {

        UsersJoinsResponse usersJoinsResponse1 = usersJoinsResponses.get(i);


            viewHlder.name.setText(usersJoinsResponse1.getName());

            Glide.with(context).load(usersJoinsResponse1.getImages()).placeholder(R.drawable.bg_sportive).into(viewHlder.img);

        final int idUser = usersJoinsResponse1.getIdUser();

        viewHlder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, SendActivity.class);
                    intent.putExtra("id_reciever", idUser);
                    context.startActivity(intent);
                }
            });
        }


    @Override
    public int getItemCount() {
        return usersJoinsResponses != null ? usersJoinsResponses.size():0;
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
