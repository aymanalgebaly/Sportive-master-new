package com.compubase.sportive.ui.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.compubase.sportive.R;
import com.compubase.sportive.adapter.CentersAdapter;
import com.compubase.sportive.adapter.GameAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.model.GameActivityResponse;
import com.compubase.sportive.model.GameModel;
import com.compubase.sportive.ui.activity.AddGameActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeCenterFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.center_img)
    ImageView centerImg;
    @BindView(R.id.name_center)
    TextView nameCenter;
    @BindView(R.id.phone_center)
    TextView phoneCenter;
    @BindView(R.id.lin_center_one)
    LinearLayout linCenterOne;
//    @BindView(R.id.map_center)
//    android.widget.fragment mapCenter;
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
    @BindView(R.id.add_btn)
    Button addBtn;
    Unbinder unbinder;

    private GoogleMap mgoogleMap;
    private SupportMapFragment mapFragment;

    private GameAdapter adapter;
    List<GameActivityResponse> gameActivityResponseList = new ArrayList<>();

    private ArrayList<GameActivityResponse> gameActivityResponseArrayList = new ArrayList<>();
    private GameActivityResponse gameActivityResponse;

    private SharedPreferences preferences;
    private String id;
    private String[] game,name;
    private int i ;


    public HomeCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_center, container, false);
        unbinder = ButterKnife.bind(this, view);

        phoneCenter.setText("0123456789");
        nameCenter.setText("Power Gym");

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        id = preferences.getString("id", "");

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_center);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

//        setupRecycler();
//        fetchData();

        setup();
        data();

        return view;
    }

    private void setup() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
        rcvCenterHome.setLayoutManager(gridLayoutManager);
        adapter = new GameAdapter(getActivity());
        rcvCenterHome.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void data() {
        List<GameModel> gameModels = new ArrayList<>();

        game = new String[]{"Football","Football","Football","Football","Football"};
        name = new String[]{"ahmed", "ahmed", "ahmed", "ahmed", "ahmed"};

        for ( i = 0; i <game.length ; i++) {
            gameModels.add(new GameModel(game[i],name[i]));

        }

        adapter.setData(gameModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mgoogleMap = googleMap;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_join, R.id.add_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_join:
                break;
            case R.id.add_btn:
                startActivity(new Intent(getContext(), AddGameActivity.class));
                break;
        }
    }

//    private void setupRecycler() {
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        rcvCenterHome.setLayoutManager(linearLayoutManager);
//
//    }
//    private void fetchData (){
//        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).ListOfGames(id);
//
//        call2.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                GsonBuilder builder = new GsonBuilder();
//                Gson gson = builder.create();
//
//
//                try {
//                    assert response.body() != null;
//                    gameActivityResponseList = Arrays.asList(gson.fromJson(response.body().string(), GameActivityResponse[].class));
//
//                    if (response.isSuccessful()){
//
//                        for (int j = 0; j <gameActivityResponseList.size() ; j++) {
//
//                            Toast.makeText(getActivity(), gameActivityResponseList.get(j).getNameGame(), Toast.LENGTH_SHORT).show();
//                            gameActivityResponse = new GameActivityResponse();
//
//                            gameActivityResponse.setNameGame(gameActivityResponseList.get(j).getNameGame());
//                            gameActivityResponse.setCoach(gameActivityResponseList.get(j).getCoach());
//
//                            gameActivityResponseArrayList.add(gameActivityResponse);
//                        }
//                        adapter = new GameAdapter(gameActivityResponseArrayList);
//                        rcvCenterHome.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }
}
