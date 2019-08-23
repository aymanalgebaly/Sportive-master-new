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

import com.compubase.sportive.R;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.LoginActivityResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        ButterKnife.bind(this);

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        id = preferences.getString("id", "");

        Log.i( "id",id);


        game = editGame.getText().toString();
         trainer = editTrainer.getText().toString();
    }

    @OnClick(R.id.dialog_btn)
    public void onViewClicked() {
        add();
    }

    private void add() {
        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).InsertGame(id,game,trainer);

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()){
                    try {
                        String string = response.body().string();

                        Log.i( "string",string);
                        if (string.equals("True")){
                            onBackPressed();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddGameActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}

