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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.compubase.sportive.R;
import com.compubase.sportive.model.TrainersListModel;
import com.compubase.sportive.ui.activity.TrainerProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class TrainersAdapter extends RecyclerView.Adapter<TrainersAdapter.ViewHolderTrainers> {

    private Context context;

    public TrainersAdapter(ArrayList<TrainersListModel> trainersListModels) {
        this.trainersListModels = trainersListModels;
    }

    public TrainersAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<TrainersListModel>trainersListModels;
    @NonNull
    @Override
    public ViewHolderTrainers onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.centers_design, viewGroup, false);
        return new ViewHolderTrainers(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTrainers viewHolderTrainers, final int i) {

        final TrainersListModel trainersListModel = trainersListModels.get(i);
        viewHolderTrainers.des.setText(trainersListModel.getDes());
        viewHolderTrainers.name.setText(trainersListModel.getName());

        Glide.with(context).load(trainersListModel.getImages()).placeholder(R.drawable.center_defult_img).into(viewHolderTrainers.img);

        viewHolderTrainers.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, TrainerProfileActivity.class);

                intent.putExtra("desc",trainersListModel.getDes());
                intent.putExtra("id",trainersListModel.getId());
                intent.putExtra("type",trainersListModel.getType());
                intent.putExtra("name",trainersListModel.getName());
                intent.putExtra("imgone",trainersListModel.getImg1());
                intent.putExtra("imgtwo",trainersListModel.getImg2());
                intent.putExtra("imgthree",trainersListModel.getImg3());
                intent.putExtra("imgfour",trainersListModel.getImg4());
                intent.putExtra("images",trainersListModel.getImages());


                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainersListModels != null ? trainersListModels.size():0;
    }

    public class ViewHolderTrainers extends RecyclerView.ViewHolder {

        TextView name,des;
        ImageView img;

        public ViewHolderTrainers(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.center_name);
            img = itemView.findViewById(R.id.center_img_design);
            des = itemView.findViewById(R.id.center_des);
        }
    }
}
