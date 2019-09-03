package com.compubase.sportive.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.compubase.sportive.R;
import com.compubase.sportive.adapter.GameAdapter;
import com.compubase.sportive.helper.TinyDB;
import com.compubase.sportive.model.GameModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    RequestQueue requestQueue;

    TinyDB tinyDB;

    GoogleMap mgoogleMap;
    SupportMapFragment mapFragment;

    String id;

    String myid;

    String lang;

    String lat;

    String name;
    @BindView(R.id.txt_des_details)
    TextView txtDesDetails;
    @BindView(R.id.txt_value_des_details)
    TextView txtValueDesDetails;
    @BindView(R.id.img_one_details)
    ImageView imgOneDetails;
    @BindView(R.id.img_two_details)
    ImageView imgTwoDetails;
    @BindView(R.id.lin_img_one)
    LinearLayout linImgOne;
    @BindView(R.id.img_three_details)
    ImageView imgThreeDetails;
    @BindView(R.id.img_four_details)
    ImageView imgFourDetails;
    @BindView(R.id.lin_img_two)
    LinearLayout linImgTwo;
    @BindView(R.id.txt_his_details)
    TextView txtHisDetails;
    @BindView(R.id.txt_value_his_details)
    TextView txtValueHisDetails;
    @BindView(R.id.mail_center)
    TextView mailCenter;
//    @BindView(R.id.map_center)
//    android.widget.fragment mapCenter;
    private GameAdapter adapter;
    private int i;

    List<GameModel> gamesList = new ArrayList<>();
    private SharedPreferences preferences;
    private String name_shared, email_shared;

    private ImageView imageView;
    private TextView center_name, center_mail;
    private String phone;
    private String image;
    private String des, history, imagess, img1, img2, img3, img4;
    private String mail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_details);
        ButterKnife.bind(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_center);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        tinyDB = new TinyDB(this);

//        preferences = getSharedPreferences("user",MODE_PRIVATE);
//        name_shared = preferences.getString("name", "");
//        email_shared = preferences.getString("email", "");
//
//        imageView = findViewById(R.id.imageView_center);
//        center_name = findViewById(R.id.center_name_system);
//        center_mail = findViewById(R.id.center_mail_system);
//
//        center_name.setText(name_shared);
//        center_mail.setText(email_shared);

        lang = getIntent().getExtras().getString("long");
        lat = getIntent().getExtras().getString("lat");
        name = getIntent().getExtras().getString("name");
        mail = getIntent().getExtras().getString("email");
        phone = getIntent().getExtras().getString("phone");
        id = String.valueOf(getIntent().getExtras().getInt("id_center"));
        des = getIntent().getExtras().getString("des");
        history = getIntent().getExtras().getString("history");
        imagess = getIntent().getExtras().getString("image");
        img1 = getIntent().getExtras().getString("imageone");
        img2 = getIntent().getExtras().getString("imagetwo");
        img3 = getIntent().getExtras().getString("imagethree");
        img4 = getIntent().getExtras().getString("imagefour");

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        txtValueDesDetails.setText(des);
        txtValueHisDetails.setText(history);
        nameCenter.setText(name);
        phoneCenter.setText(phone);
        mailCenter.setText(mail);

        Glide.with(this).load(imagess).placeholder(R.drawable.center_defult_img).into(centerImg);


        Glide.with(this).load(img1).placeholder(R.drawable.back_img).into(imgOneDetails);
        Glide.with(this).load(img2).placeholder(R.drawable.back_img).into(imgTwoDetails);
        Glide.with(this).load(img3).placeholder(R.drawable.back_img).into(imgThreeDetails);
        Glide.with(this).load(img4).placeholder(R.drawable.back_img).into(imgFourDetails);


        // Toast.makeText(CenterDetailsActivity.this, id, Toast.LENGTH_SHORT).show();

        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        myid = (shared.getString("id", ""));
        image = shared.getString("image", "");

//        Picasso.get().load(image).into(centerImg);


        rcvCenterHome.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcvCenterHome.setLayoutManager(llm);

        //setup();
        //data();

        JSON_DATA_WEB_CALL();

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tinyDB.putString("id",id);

                Intent intent = new Intent(CenterDetailsActivity.this,UserJoinActivity.class);
//                intent.putExtra("id_center",id);
                startActivity(intent);

//                startActivity(new Intent(CenterDetailsActivity.this, UserJoinActivity.class));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        assert lang != null;
        assert lat != null;
        if (lang != null || lat != null) {
            double lo = Double.parseDouble(lang);
            double la = Double.parseDouble(lat);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(lo, la)).title(name);
            googleMap.addMarker(marker);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(la,lo))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


    private void JSON_DATA_WEB_CALL() {

        String url;

        url = "http://sportive.technowow.net/sportive.asmx/select_game?id_center=" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // showMessage(response);

                JSON_PARSE_DATA_AFTER_WEBCALL2(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showMessage(error.getMessage());
            }
        });


        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL2(String Jobj) {


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


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(String Jobj) {


        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        List<GameModel> games = Arrays.asList(gson.fromJson(Jobj, GameModel[].class));

        showMessage(games.get(0).getGame());

        for (int i = 0; i < games.size(); i++) {

            GameModel game = new GameModel();

            game.setName(games.get(i).getName());
            game.setGame(games.get(i).getGame());
            game.setId(games.get(i).getId());

            gamesList.add(game);
        }


        adapter = new GameAdapter(gamesList);
        rcvCenterHome.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setup() {

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        rcvCenterHome.setLayoutManager(gridLayoutManager);
        //adapter = new GameAdapter(this);
        rcvCenterHome.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

//    private void data() {
//
//
//        List<GameModel> gameModels = new ArrayList<>();
//
//        game = new String[]{"Football","Football","Football","Football","Football"};
//        name_game = new String[]{"ahmed", "ahmed", "ahmed", "ahmed", "ahmed"};
//
//        for ( i = 0; i <game.length ; i++) {
//            gameModels.add(new GameModel(game[i],name_game[i]));
//
//        }
//
//        //adapter.setData(gameModels);
//        adapter.notifyDataSetChanged();
//    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
