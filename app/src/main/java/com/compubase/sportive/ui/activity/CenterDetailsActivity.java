package com.compubase.sportive.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.compubase.sportive.adapter.CommentAdapter;
import com.compubase.sportive.adapter.GameAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.helper.TinyDB;
import com.compubase.sportive.model.CommentsListActivity;
import com.compubase.sportive.model.GameModel;
import com.compubase.sportive.ui.fragment.CustomDialogFragmentCenter;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

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
    @BindView(R.id.rcv_center_home_details)
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
    @BindView(R.id.add_comment_center_btn)
    Button addCommentBtn;
    @BindView(R.id.rcv_center_comments_list)
    RecyclerView rcvCenterCommentsList;
    @BindView(R.id.txt_linked)
    TextView txtLinked;
    @BindView(R.id.txt_value_linked_details)
    TextView txtValueLinkedDetails;
    @BindView(R.id.frame_rcv_comment)
    FrameLayout frameRcvComment;
    @BindView(R.id.txt_face)
    TextView txtFace;
    @BindView(R.id.txt_value_face_details)
    TextView txtValueFaceDetails;
    @BindView(R.id.txt_web)
    TextView txtWeb;
    @BindView(R.id.txt_value_web_details)
    TextView txtValueWebDetails;
    @BindView(R.id.txt_ser)
    TextView txtSer;
    @BindView(R.id.txt_value_ser_details)
    TextView txtValueSerDetails;
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
    private CommentsListActivity commentsListActivitie;
    private ArrayList<CommentsListActivity> commentsListActivityArrayList = new ArrayList<>();
    private CommentAdapter adapter_comments;
    private CustomDialogFragmentCenter dialogFragmentCenter;
    private int id1;
    private String type;
    private Dialog dialog;
    private Button submitBtn;
    private String comment_txt;
    private MaterialRatingBar ratingBar;
    private EditText comment_;
    private float rating;
    private String name1;
    private String type1;
    private String linked;
    private String fb, web, ser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_details);
        ButterKnife.bind(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_center);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        tinyDB = new TinyDB(this);
        id1 = tinyDB.getInt("id");

        lang = Objects.requireNonNull(getIntent().getExtras()).getString("long");
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
        type = getIntent().getExtras().getString("type");
        linked = getIntent().getExtras().getString("linked");
        fb = getIntent().getStringExtra("fb");
        web = getIntent().getStringExtra("web");
        ser = getIntent().getStringExtra("ser");


        txtValueDesDetails.setText(des);
        txtValueHisDetails.setText(history);
        nameCenter.setText(name);
        phoneCenter.setText(phone);
        mailCenter.setText(mail);
        txtValueSerDetails.setText(ser);
        txtValueWebDetails.setText(web);
        txtValueWebDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.isEmpty()) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
                }
                startActivity(intent);

            }
        });
        txtValueFaceDetails.setText(fb);
        txtValueFaceDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(fb));
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> list = packageManager.queryIntentActivities(intent1, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.isEmpty()) {
                    intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(fb));
                }
                startActivity(intent1);
            }
        });
        txtValueLinkedDetails.setText(linked);
        txtValueLinkedDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(linked));
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> list = packageManager.queryIntentActivities(intent2, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.isEmpty()) {
                    intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(linked));
                }
                startActivity(intent2);
            }
        });


        Glide.with(this).load(imagess).placeholder(R.drawable.center_defult_img).into(centerImg);

        Glide.with(this).load(img1).placeholder(R.drawable.back_img).into(imgOneDetails);
        Glide.with(this).load(img2).placeholder(R.drawable.back_img).into(imgTwoDetails);
        Glide.with(this).load(img3).placeholder(R.drawable.back_img).into(imgThreeDetails);
        Glide.with(this).load(img4).placeholder(R.drawable.back_img).into(imgFourDetails);


        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        myid = (shared.getString("id", ""));
        image = shared.getString("image", "");
        name1 = shared.getString("name", "");
        type1 = shared.getString("type", "");
//        linked = shared.getString("linked", "");

        assert type1 != null;
        if (type1.equals("trainer") || type1.equals("center")) {
            addCommentBtn.setVisibility(View.INVISIBLE);
        }

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tinyDB.putString("id", CenterDetailsActivity.this.id);

                Intent intent = new Intent(CenterDetailsActivity.this, UserJoinActivity.class);
                startActivity(intent);

            }
        });

        JSON_DATA_WEB_CALL();

        setupRecyclerGame();
        setupRecycler();
        fetchData();
    }

    private void setupRecyclerGame() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rcvCenterHome.setNestedScrollingEnabled(true);
//        rcvCenterCommentsList.setNestedScrollingEnabled(true);
        rcvCenterHome.setLayoutManager(linearLayoutManager);

    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rcvCenterCommentsList.setNestedScrollingEnabled(true);
//        rcvCenterCommentsList.setNestedScrollingEnabled(true);
        rcvCenterCommentsList.setLayoutManager(linearLayoutManager);

    }

    private void fetchData() {

        commentsListActivityArrayList.clear();

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).ViewComment(id);

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                assert response.body() != null;
                try {

                    List<CommentsListActivity> commentsListActivities = Arrays.asList(gson.fromJson(response.body().string(), CommentsListActivity[].class));
                    if (response.isSuccessful()) {

                        for (int i = 0; i < commentsListActivities.size(); i++) {

                            commentsListActivitie = new CommentsListActivity();

                            commentsListActivitie.setImages(commentsListActivities.get(i).getImages());
                            commentsListActivitie.setRate(commentsListActivities.get(i).getRate());
                            commentsListActivitie.setComment(commentsListActivities.get(i).getComment());
                            commentsListActivitie.setName(commentsListActivities.get(i).getName());
                            commentsListActivitie.setIdCenter(commentsListActivities.get(i).getIdCenter());

                            commentsListActivityArrayList.add(commentsListActivitie);

                        }

                        adapter_comments = new CommentAdapter(commentsListActivityArrayList);
                        rcvCenterCommentsList.setAdapter(adapter_comments);
                        adapter_comments.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    Toast.makeText(CenterDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CenterDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    .target(new LatLng(la, lo))      // Sets the center of the map to location user
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

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.add_comment_center_btn)
    public void onViewClicked() {


        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fragment);
        TextView titleTV = (TextView) dialog.findViewById(R.id.txt_titte);
        submitBtn = (Button) dialog.findViewById(R.id.btn_confirm);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRate();
            }
        });
        ratingBar = (MaterialRatingBar) dialog.findViewById(R.id.ratbar);
        comment_ = (EditText) dialog.findViewById(R.id.edit_txt_dialog);
        dialog.show();
    }

    private void insertRate() {

        comment_txt = comment_.getText().toString();
        rating = ratingBar.getRating();

        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.insertComment(myid, id, comment_txt, String.valueOf(rating));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    if (string.equals("True")) {

                        CommentsListActivity commentsListActivity = new CommentsListActivity();
                        commentsListActivity.setComment(comment_txt);
                        commentsListActivity.setRate(String.valueOf(rating));
                        commentsListActivity.setName(name1);
                        commentsListActivity.setImages(image);
                        commentsListActivity.setIdCenter(Integer.valueOf(id));


                        commentsListActivityArrayList.add(commentsListActivity);
                        adapter.notifyDataSetChanged();

                        dialog.dismiss();

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CenterDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
