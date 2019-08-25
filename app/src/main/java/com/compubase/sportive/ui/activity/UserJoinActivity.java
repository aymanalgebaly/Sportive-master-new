package com.compubase.sportive.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.compubase.sportive.R;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserJoinActivity extends AppCompatActivity {

    public static String id;
    @BindView(R.id.pay_cash_btn)
    Button payCashBtn;
    @BindView(R.id.pay_online_btn)
    Button payOnlineBtn;
    private String myid;
//    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_join);
        ButterKnife.bind(this);

        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        myid = (shared.getString("id", ""));

//        id = String.valueOf(getIntent().getExtras().getInt("id_center"));

//        Intent intent = new Intent(this,CentersActivity.class);
//        intent.putExtra("id_center",id);
//        startActivity(intent);

     //   Toast.makeText(this, id, Toast.LENGTH_SHORT).show();


    }

    @OnClick({R.id.pay_cash_btn, R.id.pay_online_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_cash_btn:
                join();
                break;
            case R.id.pay_online_btn:
                break;
        }
    }

    private void join() {

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).Join(id,myid,"game");

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    if (string.equals("True")){
                        Toast.makeText(UserJoinActivity.this, "Joined", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserJoinActivity.this,HomeActivity.class));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UserJoinActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
