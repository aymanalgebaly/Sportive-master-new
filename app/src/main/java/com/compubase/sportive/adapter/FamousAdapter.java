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
import com.compubase.sportive.model.FamousActivityResponse;
import com.compubase.sportive.model.FamousListModel;
import com.compubase.sportive.ui.activity.CenterDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FamousAdapter extends RecyclerView.Adapter<FamousAdapter.ViewHolderFamous>{

    private Context context;
    private List<FamousListModel> famousModelList;

    public FamousAdapter(List<FamousListModel> famousModelList) {
        this.famousModelList = famousModelList;
    }

    public FamousAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderFamous onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.famous_design, viewGroup, false);
        return new ViewHolderFamous(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFamous viewHolderFamous, int i) {

        final FamousListModel famousListModel = famousModelList.get(i);

        viewHolderFamous.type.setText(famousListModel.getType());
        viewHolderFamous.name.setText(famousListModel.getName());

        Glide.with(context).load(famousListModel.getImages()).placeholder(R.drawable.center_defult_img).into(viewHolderFamous.imageView);

        viewHolderFamous.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CenterDetailsActivity.class);
                intent.putExtra("name",famousListModel.getName());
                intent.putExtra("email",famousListModel.getEmail());
                intent.putExtra("id_center",famousListModel.getId());
                intent.putExtra("image",famousListModel.getImages());
                intent.putExtra("lat",famousListModel.getLat());
                intent.putExtra("long",famousListModel.getLang());
                intent.putExtra("phone",famousListModel.getPhone());
                context.startActivity(intent);
            }
        });

//        Picasso.get().load(famousActivityResponse.getImages()).into(viewHolderFamous.imageView);
    }

    @Override
    public int getItemCount() {
        return famousModelList != null ? famousModelList.size():0;
    }

    public void setData(List<FamousListModel> famousModels) {
        this.famousModelList = famousModels;
    }

    public class ViewHolderFamous extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,type;

        public ViewHolderFamous(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_famous);
            name = itemView.findViewById(R.id.txt_center_name_famous);
            type = itemView.findViewById(R.id.type_center_famous);
        }
    }
}
