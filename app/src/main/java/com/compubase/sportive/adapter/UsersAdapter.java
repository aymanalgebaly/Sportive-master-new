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
    private List<UsersModel>usersModelsist;
    onItemClickListner onItemClickedListner;


    public UsersAdapter(List<UsersModel> usersModels) {
        this.usersModelsist = usersModels;
    }
    public interface onItemClickListner {
        void onClick(UsersModel usersModel);//pass your object types.
    }

    public void onItemClickedListner(UsersAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickedListner = onItemClickListner;
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
        UsersModel usersModel = usersModelsist.get(i);

        viewHlder.name.setText(usersModel.getName());

        Picasso.get().load(usersModel.getImg()).into(viewHlder.img);

//        viewHlder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context,SendActivity.class);
//
////                UsersJoinsResponse center = new Center();
////                Intent intent = new Intent(context,SendActivity.class);
////                intent.putExtra("name",center.getName());
////                intent.putExtra("email",center.getEmail());
////                intent.putExtra("id_user",center.getId());
////                intent.putExtra("image",center.getImages());
//                  context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return usersModelsist != null ? usersModelsist.size():0;
    }


    public void setData(List<UsersModel> usersModels) {
        this.usersModelsist = usersModels;
    }

    public class ViewHlder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img;
        public ViewHlder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_name);
            img = itemView.findViewById(R.id.user_img);

        }
    }
}
