package com.compubase.sportive.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.compubase.sportive.R;
import com.compubase.sportive.adapter.GameAdapter;
import com.compubase.sportive.helper.TinyDB;
import com.compubase.sportive.model.GameActivityResponse;
import com.compubase.sportive.model.GameModel;
import com.compubase.sportive.ui.activity.AddGameActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    SupportMapFragment mapFragment;

    GameAdapter adapter;
    List<GameActivityResponse> gameActivityResponseList = new ArrayList<>();

    private ArrayList<GameActivityResponse> gameActivityResponseArrayList = new ArrayList<>();
    private GameActivityResponse gameActivityResponse;

    private SharedPreferences preferences;
    private String id,center_name,center_phone;
    private String[] game,name;
    private int i ;

    RequestQueue requestQueue;

    TinyDB tinyDB;

    List<GameModel> gamesList = new ArrayList<>();


    public HomeCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_center, container, false);
        unbinder = ButterKnife.bind(this, view);


        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        id = preferences.getString("id", "");
        center_name = preferences.getString("name", "");
        center_phone = preferences.getString("phone", "");

        nameCenter.setText(center_name);
        phoneCenter.setText(center_phone);

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_center);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        rcvCenterHome.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rcvCenterHome.setLayoutManager(llm);

        JSON_DATA_WEB_CALL();


        //setupMarker();


        return view;
    }


    private void setupMarker()
    {
        String lang =  preferences.getString("long", "0.000000");
        String lat =  preferences.getString("lat", "0.00000000");
        assert lang != null;
        assert lat != null;
        if(!lang.equals("") || !lat.equals(""))
        {
            double lo = Double.parseDouble(lang);
            double la = Double.parseDouble(lat);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(la,lo)).title(center_name);
            mgoogleMap.addMarker(marker);
        }
    }

    private void JSON_DATA_WEB_CALL(){

        String url;

        url = "http://sportive.technowow.net/sportive.asmx/select_game?id_center="+id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // showMessage(response);

                JSON_PARSE_DATA_AFTER_WEBCALL2(response);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showMessage(error.getMessage());
            }
        });


        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        requestQueue.add(stringRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL2(String Jobj) {

        //to clear the array first
        gamesList.clear();

        try {

            JSONArray js = new JSONArray(Jobj);

            for (int i = 0; i < js.length(); i++) {

                JSONObject childJSONObject = js.getJSONObject(i);

                GameModel game = new GameModel();

                game.setGame(childJSONObject.getString("name_game"));

                game.setName(childJSONObject.getString("coach"));

                gamesList.add(game);

            }

            adapter = new GameAdapter(gamesList);
            rcvCenterHome.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        }catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }













    private void setup() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
        rcvCenterHome.setLayoutManager(gridLayoutManager);
        //adapter = new GameAdapter(getActivity());
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

       // adapter.setData(gameModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        String lang =  preferences.getString("long", "0.000000");
        String lat =  preferences.getString("lat", "0.00000000");
        assert lang != null;
        assert lat != null;
        if(!lang.equals("") || !lat.equals(""))
        {
            double lo = Double.parseDouble(lang);
            double la = Double.parseDouble(lat);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(la,lo)).title(center_name);
            googleMap.addMarker(marker);
        }
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

}
