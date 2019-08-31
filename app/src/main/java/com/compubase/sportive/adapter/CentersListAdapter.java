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
import com.compubase.sportive.model.UsersJoinsResponse;
import com.compubase.sportive.ui.activity.SendActivity;

import java.util.List;

public class CentersListAdapter extends RecyclerView.Adapter<CentersListAdapter.ViewHolder> {

    private Context context;
    private List<UsersJoinsResponse> usersJoinsResponses;

    public CentersListAdapter(List<UsersJoinsResponse> usersJoinsResponses) {
        this.usersJoinsResponses = usersJoinsResponses;
    }

    public CentersListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_design, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        UsersJoinsResponse usersJoinsResponse = usersJoinsResponses.get(i);

        viewHolder.name.setText(usersJoinsResponse.getName());

        Glide.with(context).load(usersJoinsResponse.getImages()).placeholder(R.drawable.bg_sportive).into(viewHolder.img);

        final int idCenter = usersJoinsResponse.getIdCenter();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SendActivity.class);
                intent.putExtra("id_center", idCenter);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersJoinsResponses != null ? usersJoinsResponses.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_name);
            img = itemView.findViewById(R.id.user_img);
        }
    }
}
