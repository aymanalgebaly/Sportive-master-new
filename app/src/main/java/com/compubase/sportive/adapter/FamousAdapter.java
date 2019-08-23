package com.compubase.sportive.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.compubase.sportive.R;
import com.compubase.sportive.model.FamousActivityResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FamousAdapter extends RecyclerView.Adapter<FamousAdapter.ViewHolderFamous>{

    private Context context;
    private List<FamousActivityResponse> famousModelList;

    public FamousAdapter(List<FamousActivityResponse> famousModelList) {
        this.famousModelList = famousModelList;
    }

    public FamousAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderFamous onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.famous_design, viewGroup, false);
        return new ViewHolderFamous(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFamous viewHolderFamous, int i) {

        FamousActivityResponse famousActivityResponse = famousModelList.get(i);

        viewHolderFamous.type.setText(famousActivityResponse.getType());
        viewHolderFamous.name.setText(famousActivityResponse.getName());

//        Picasso.get().load(famousActivityResponse.getImages()).into(viewHolderFamous.imageView);
    }

    @Override
    public int getItemCount() {
        return famousModelList != null ? famousModelList.size():0;
    }

    public void setData(List<FamousActivityResponse> famousModels) {
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
