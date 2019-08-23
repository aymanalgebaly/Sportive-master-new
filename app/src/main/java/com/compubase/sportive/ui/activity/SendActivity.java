package com.compubase.sportive.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class SendActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.msg)
    EditText msg;
    @BindView(R.id.send_btn)
    Button sendBtn;
    private String myid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this,R.array.type,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(this);


        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        myid = (shared.getString("id", ""));

        Intent intent = getIntent();
    }

    @OnClick(R.id.send_btn)
    public void onViewClicked() {
//        send_activity();
    }

//    private void send_activity() {
//
//            Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).InsertActiv();
//
//            call2.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                    GsonBuilder builder = new GsonBuilder();
//                    Gson gson = builder.create();
//
//                    try {
//                        assert response.body() != null;
//                        String string = response.body().string();
//                        if (string.equals("True")){
//                            Toast.makeText(SendActivity.this, "Joined", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(SendActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String txt = String.valueOf(parent.getItemIdAtPosition(position));

    }
}
