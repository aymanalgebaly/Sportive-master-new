package com.compubase.sportive.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.compubase.sportive.R;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.GameModel;
import com.compubase.sportive.model.LoginActivityResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGameActivity extends AppCompatActivity {

    @BindView(R.id.edit_game)
    EditText editGame;
    @BindView(R.id.edit_trainer)
    EditText editTrainer;
    @BindView(R.id.dialog_btn)
    Button dialogBtn;
    private String game,trainer;
    private SharedPreferences preferences;
    private String id;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        ButterKnife.bind(this);

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        id = preferences.getString("id", "");

        Log.i( "id",id);
    }

    @OnClick(R.id.dialog_btn)
    public void onViewClicked() {
        JSON_DATA_WEB_CALL();
    }

    private void JSON_DATA_WEB_CALL(){

        final String url;

        game = editGame.getText().toString();
        trainer = editTrainer.getText().toString();

        url = "http://sportive.technowow.net/sportive.asmx/insert_game?id_center="+id+"&name_game="+game+"&coach="+trainer;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Toast.makeText(AddGameActivity.this, response, Toast.LENGTH_SHORT).show();
                if (response.equals("True")){

                    startActivity(new Intent(AddGameActivity.this,CenterHomeActivity.class));
                    onBackPressed();
                }
                // showMessage(response);

//                JSON_PARSE_DATA_AFTER_WEBCALL2(response);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showMessage(error.getMessage());
            }
        });


        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));

        requestQueue.add(stringRequest);
    }
    private void showMessage(String s) {
        Toast.makeText(AddGameActivity.this, s, Toast.LENGTH_LONG).show();
    }

//    private void add() {
//        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).InsertGame();
//
//        call2.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                if (response.isSuccessful()){
//                    try {
//                        String string = response.body().string();
//
//                        Toast.makeText(AddGameActivity.this, string, Toast.LENGTH_SHORT).show();
//
//                        Log.i( "string",string);
//                        if (string.equals("True")){
//
//                            onBackPressed();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(AddGameActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

}

