package com.compubase.sportive.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.compubase.sportive.R;
import com.compubase.sportive.adapter.UsersAdapter;
import com.compubase.sportive.helper.TinyDB;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.model.UsersModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CentersActivity extends AppCompatActivity {


    @BindView(R.id.rcv_users)
    RecyclerView rcvUsers;
    private UsersAdapter adapter;
    private String[] name;
    private int i;
    private String id, name_user;
    private int[] img;

    RequestQueue requestQueue;

    TinyDB tinyDB;

    List<Center> userList = new ArrayList<>();
    private String id_center;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centers);
        ButterKnife.bind(this);

        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        id = (shared.getString("id", ""));

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        setup();
        JSON_DATA_WEB_CALL();
    }

    private void JSON_DATA_WEB_CALL() {

        String url;

        url = "http://sportive.technowow.net/sportive.asmx/select_join_center?id_center=" + id;

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


        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));

        requestQueue.add(stringRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL2(String Jobj) {

        //to clear the array first
        userList.clear();

        try {

            JSONArray js = new JSONArray(Jobj);

            for (int i = 0; i < js.length(); i++) {

                JSONObject childJSONObject = js.getJSONObject(i);

                Center user = new Center();

                user.setName(childJSONObject.getString("name"));

                user.setPhone(childJSONObject.getString("phone"));

                user.setEmail(childJSONObject.getString("email"));

                user.setImages(childJSONObject.getString("images"));


                userList.add(user);

            }

            adapter = new UsersAdapter(userList);
            rcvUsers.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showMessage(String s) {
        Toast.makeText(CentersActivity.this, s, Toast.LENGTH_LONG).show();
    }


    private void data() {

        List<UsersModel> usersModels = new ArrayList<>();

//        img = new int[]{R.drawable.bg_sportive, R.drawable.bg_sportive, R.drawable.bg_sportive,
//                R.drawable.bg_sportive, R.drawable.bg_sportive, R.drawable.bg_sportive,
//                R.drawable.bg_sportive, R.drawable.bg_sportive, R.drawable.bg_sportive};
        name = new String[]{"ahmed", "ayman", "amir", "ali", "hassan", "hussien", "hanan", "hany", "sameh"};

        for (i = 0; i < name.length; i++) {
            usersModels.add(new UsersModel(name[i]));

//            ratingBar.setRating(num[i]);
        }

        // adapter.setData(usersModels);
        adapter.notifyDataSetChanged();
    }

    private void setup() {

        rcvUsers.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcvUsers.setLayoutManager(llm);

    }
}
