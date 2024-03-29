package com.compubase.sportive.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.compubase.sportive.R;
import com.compubase.sportive.helper.GPSTracker;
import com.compubase.sportive.helper.TinyDB;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.ui.activity.CenterDetailsActivity;
import com.compubase.sportive.ui.activity.UserJoinActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CentersAdapter extends RecyclerView.Adapter<CentersAdapter.ViewHolderCenters>{

    private Context context;
    private List<Center> centerList;
    private double latitude,longitude;
    private double lo,la;

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

        TinyDB tinyDB = new TinyDB(context);

        latitude = tinyDB.getDouble("latitude", this.latitude);
        longitude = tinyDB.getDouble("longitude", this.longitude);

//        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
//        String image = preferences.getString("image", "");

//        Toast.makeText(context, image, Toast.LENGTH_SHORT).show();

        int id = center.getId();

        Glide.with(context)
                .load(centerList.get(i).getImages())
                .placeholder(R.drawable.center_defult_img)
                .into(viewHolderCenters.imageView);

        viewHolderCenters.name.setText(center.getName());

        Location locationA = new Location("point A");

        String lat = centerList.get(i).getLat();
        String lang = centerList.get(i).getLang();

        if(!lang.equals("") || !lat.equals("")){
            lo = Double.parseDouble(lang);
            la = Double.parseDouble(lat);

            locationA.setLatitude(la);
            locationA.setLongitude(lo);

            Location locationB = new Location("point B");

            locationB.setLatitude(this.latitude);
            locationB.setLongitude(this.longitude);

            float distance = locationA.distanceTo(locationB);
            String s = String.valueOf(distance);

            viewHolderCenters.km.setText(s+" KM");

        }
//        viewHolderCenters.km.setText(center.);

//        Picasso.get().load(centersModel.getImg()).into(viewHolderCenters.imageView);

        viewHolderCenters.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CenterDetailsActivity.class);
                intent.putExtra("name",center.getName());
                intent.putExtra("email",center.getEmail());
                intent.putExtra("id_center",center.getId());
                intent.putExtra("type",center.getType());
                intent.putExtra("image",center.getImages());
                intent.putExtra("lat",center.getLat());
                intent.putExtra("long",center.getLang());
                intent.putExtra("phone",center.getPhone());
                intent.putExtra("des",center.getDes());
                intent.putExtra("history",center.getHistory());
                intent.putExtra("imageone",center.getImg1());
                intent.putExtra("imagetwo",center.getImg2());
                intent.putExtra("imagethree",center.getImg3());
                intent.putExtra("imagefour",center.getImg4());
                intent.putExtra("linked",center.getLinkedIn());
                intent.putExtra("fb",center.getFb());
                intent.putExtra("web",center.getWebsite());
                intent.putExtra("ser",center.getServices());
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
