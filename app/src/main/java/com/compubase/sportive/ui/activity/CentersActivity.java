package com.compubase.sportive.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.compubase.sportive.R;
import com.compubase.sportive.adapter.ActivitiesAdapter;
import com.compubase.sportive.adapter.CentersListAdapter;
import com.compubase.sportive.adapter.UsersAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.helper.TinyDB;
import com.compubase.sportive.model.ActivityListResponse;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.model.UserActivityActivityResponse;
import com.compubase.sportive.model.UsersJoinsResponse;
import com.compubase.sportive.model.UsersModel;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CentersActivity extends AppCompatActivity {


    @BindView(R.id.rcv_users)
    RecyclerView rcvUsers;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.rel_title)
    RelativeLayout relTitle;
    @BindView(R.id.toolbar_centers_list)
    Toolbar toolbarCentersList;
    @BindView(R.id.abbBar_centers_list)
    AppBarLayout abbBarCentersList;
    private CentersListAdapter adapter;
    private String[] name;
    private int i;
    private String id, name_user;
    private int[] img;

    RequestQueue requestQueue;

    TinyDB tinyDB;

    List<UsersJoinsResponse> usersJoinsResponsesList = new ArrayList<>();
    private String id_center;
    private String image;
    private String des;
    private UsersJoinsResponse usersJoinsResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centers);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar_centers_list);
        setSupportActionBar(toolbar);

        txtTitle.setText("My centers");

        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        id = shared.getString("id", "");
        image = shared.getString("image", "");
        des = shared.getString("des", "");


       // Toast.makeText(this, id, Toast.LENGTH_SHORT).show();


        setup();
        fetchData();
//        JSON_DATA_WEB_CALL();
    }

    private void fetchData (){

        //to clear the array first
        usersJoinsResponsesList.clear();

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).CentersJoin(id);

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                try {
                    assert response.body() != null;
                    List<UsersJoinsResponse> usersJoinsResponses = Arrays.asList(gson.fromJson(response.body().string(), UsersJoinsResponse[].class));

                    if (response.isSuccessful()){

                        for (int j = 0; j <usersJoinsResponses.size() ; j++) {

                            usersJoinsResponse = new UsersJoinsResponse();

                            usersJoinsResponse.setIdCenter(usersJoinsResponses.get(j).getIdCenter());
                            usersJoinsResponse.setName(usersJoinsResponses.get(j).getName());
                            usersJoinsResponse.setImages(usersJoinsResponses.get(j).getImages());
                            usersJoinsResponse.setDes(usersJoinsResponses.get(j).getDes());
//                            usersJoinsResponse.setType(userActivityActivityResponses.get(j).getType());

                            usersJoinsResponsesList.add(usersJoinsResponse);
                        }
                        adapter = new CentersListAdapter(usersJoinsResponsesList);
                        rcvUsers.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CentersActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                Log.i("onFailure: ",t.getMessage());
            }
        });

    }


//    private void JSON_DATA_WEB_CALL() {
//
//        String url;
//
//        url = "http://sportive.technowow.net/sportive.asmx/select_join_iduser?id_user=" + id;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                // showMessage(response);
//
//                JSON_PARSE_DATA_AFTER_WEBCALL2(response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                showMessage(error.getMessage());
//            }
//        });
//
//
//        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));
//
//        requestQueue.add(stringRequest);
//    }
//
//
//    public void JSON_PARSE_DATA_AFTER_WEBCALL2(String Jobj) {
//
//        //to clear the array first
//        usersJoinsResponses.clear();
//
//        try {
//
//            JSONArray js = new JSONArray(Jobj);
//
//            for (int i = 0; i < js.length(); i++) {
//
//                JSONObject childJSONObject = js.getJSONObject(i);
//
//                UsersJoinsResponse usersJoinsResponse = new UsersJoinsResponse();
//                usersJoinsResponse.setName(childJSONObject.getString("name"));
//
//                usersJoinsResponse.setPhone(childJSONObject.getString("phone"));
//
//                usersJoinsResponse.setEmail(childJSONObject.getString("email"));
//
//                usersJoinsResponse.setImages(childJSONObject.getString("images"));
//
//                usersJoinsResponse.setImages(childJSONObject.getString("des"));
//
//                usersJoinsResponse.setIdCenter(childJSONObject.getInt("id_center"));
//
//
//                usersJoinsResponses.add(usersJoinsResponse);
//
//            }
//
//            adapter = new CentersListAdapter(usersJoinsResponses);
//            rcvUsers.setAdapter(adapter);
//
//            adapter.notifyDataSetChanged();
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }


    private void showMessage(String s) {
        Toast.makeText(CentersActivity.this, s, Toast.LENGTH_LONG).show();
    }

    private void setup() {

        rcvUsers.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcvUsers.setLayoutManager(llm);

    }
}
