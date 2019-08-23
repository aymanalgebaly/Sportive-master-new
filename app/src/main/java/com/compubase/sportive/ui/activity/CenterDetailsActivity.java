package com.compubase.sportive.ui.activity;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.compubase.sportive.R;
import com.compubase.sportive.adapter.GameAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.GameModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CenterDetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.center_img)
    ImageView centerImg;
    @BindView(R.id.name_center)
    TextView nameCenter;
    @BindView(R.id.phone_center)
    TextView phoneCenter;
    @BindView(R.id.lin_center_one)
    LinearLayout linCenterOne;
    @BindView(R.id.frame_map_center)
    FrameLayout frameMapCenter;
    @BindView(R.id.btn_join)
    Button btnJoin;
    @BindView(R.id.lin_txt)
    LinearLayout linTxt;
    @BindView(R.id.rcv_center_home)
    RecyclerView rcvCenterHome;
    @BindView(R.id.frame_rcv)
    FrameLayout frameRcv;

    GoogleMap mgoogleMap;
    SupportMapFragment mapFragment;

    String id;

    String myid;

    String lang;

    String lat;

    String name;
    private GameAdapter adapter;
    private String[] game,name_game;
    private int i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_details);
        ButterKnife.bind(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_center);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        lang = getIntent().getExtras().getString("long");
        lat = getIntent().getExtras().getString("lat");
        name = getIntent().getExtras().getString("name");
        String phone = getIntent().getExtras().getString("phone");
        id = getIntent().getExtras().getString("id");

       // Toast.makeText(CenterDetailsActivity.this, lang, Toast.LENGTH_SHORT).show();

        nameCenter.setText(name);
        phoneCenter.setText(phone);

        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        myid = (shared.getString("id", "5"));


        setup();
        data();

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                join();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        assert lang != null;
        assert lat != null;
        if(!lang.equals("") || !lat.equals(""))
        {
            double lo = Double.parseDouble(lang);
            double la = Double.parseDouble(lat);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(lo,la)).title(name);
            googleMap.addMarker(marker);
        }
    }


    private void join() {

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).Join("5","10","game");

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    if (string.equals("True")){
                        Toast.makeText(CenterDetailsActivity.this, "Joined", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CenterDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setup() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        rcvCenterHome.setLayoutManager(gridLayoutManager);
        adapter = new GameAdapter(this);
        rcvCenterHome.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void data() {
        List<GameModel> gameModels = new ArrayList<>();

        game = new String[]{"Football","Football","Football","Football","Football"};
        name_game = new String[]{"ahmed", "ahmed", "ahmed", "ahmed", "ahmed"};

        for ( i = 0; i <game.length ; i++) {
            gameModels.add(new GameModel(game[i],name_game[i]));

        }

        adapter.setData(gameModels);
        adapter.notifyDataSetChanged();
    }
}
