package com.compubase.sportive.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.compubase.sportive.R;
import com.compubase.sportive.model.UsersModel;
import com.compubase.sportive.ui.activity.CenterDetailsActivity;
import com.compubase.sportive.ui.activity.SendActivity;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHlder> {
    private Context context;
    private List<UsersModel>usersModelsList;

    public UsersAdapter(List<UsersModel> usersModelsList) {
        this.usersModelsList = usersModelsList;
    }

    public UsersAdapter(Context context) {
        this.context = context;
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
        UsersModel usersModel = usersModelsList.get(i);

        viewHlder.name.setText(usersModel.getName());

        viewHlder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,SendActivity.class);
                //intent.putExtra("id",user.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersModelsList != null ? usersModelsList.size():0;
    }

    public void setData(List<UsersModel> usersModels) {
        this.usersModelsList = usersModels;
    }

    public class ViewHlder extends RecyclerView.ViewHolder {

        TextView name;
        public ViewHlder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_name);
        }
    }
}
