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

import com.compubase.sportive.R;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.ui.activity.CenterDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class CentersAdapter extends RecyclerView.Adapter<CentersAdapter.ViewHolderCenters>{

    private Context context;
    private List<Center> centerList;

    public CentersAdapter(List<Center> centerList) {
        this.centerList = centerList;
    }

    public CentersAdapter(Context context, List<Center> centerList) {
        this.context = context;
        this.centerList = centerList;
    }

    public CentersAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolderCenters onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.centers_rcv_design, viewGroup, false);
        return new ViewHolderCenters(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCenters viewHolderCenters, int i) {

        final Center center = centerList.get(i);

        viewHolderCenters.name.setText(center.getName());
        
//        viewHolderCenters.km.setText(center.);

//        Picasso.get().load(centersModel.getImg()).into(viewHolderCenters.imageView);

        viewHolderCenters.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,CenterDetailsActivity.class);
                intent.putExtra("name",center.getName());
                intent.putExtra("email",center.getEmail());
                intent.putExtra("id_center",center.getId());
                intent.putExtra("image",center.getImages());
                intent.putExtra("lat",center.getLat());
                intent.putExtra("long",center.getLang());
                intent.putExtra("phone",center.getPhone());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return centerList != null ? centerList.size():0;
    }

    public void setTOAdapter(List<Center> centers) {
        this.centerList = centers;
    }

    public void filtersearch(ArrayList<Center> centers) {
        this.centerList = centers;
        notifyDataSetChanged();
    }

    class ViewHolderCenters extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,km;
        ViewHolderCenters(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_centers_design);
            km = itemView.findViewById(R.id.txt_km);
            name = itemView.findViewById(R.id.txt_name_of_center);

        }
    }
}
