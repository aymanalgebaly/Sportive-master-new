package com.compubase.sportive.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.compubase.sportive.R;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SendActivity extends AppCompatActivity {
    public static int id_recievd;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.msg)
    EditText msg;
    @BindView(R.id.send_btn)
    Button sendBtn;

    List<String> types = new ArrayList<>();

    String myid;

    RequestQueue requestQueue;
    private String id_id_user;
    private int id;
    private String s_id;
    private int id_recieved;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);

        types.add("Rate");
        types.add("Update");
        types.add("Message");
        types.add("Comment");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SendActivity.this, android.R.layout.simple_spinner_item, types);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        myid = (shared.getString("id", ""));


        Intent intent = getIntent();
        id_recieved = intent.getIntExtra("id_recieved", id);
        s_id = String.valueOf(id_recieved);

//        Toast.makeText(this, id_id_user, Toast.LENGTH_SHORT).show();

       // showMessage(String.valueOf(id_id_user));

    }

    @OnClick(R.id.send_btn)
    public void onViewClicked() {

        JSON_DATA_WEB_CALL();
    }

    private void JSON_DATA_WEB_CALL(){


        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.InsertActiv(myid, id_recieved, msg.getText().toString(), spinner.getSelectedItem().toString().trim());
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();

                    if (string.equals("True")){
                        onBackPressed();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(SendActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


//        String url;
//
//        url = "http://sportive.technowow.net/sportive.asmx/insert_activites?id_send="+myid+"&id_recived="+id_recieved+"&message="+msg.getText().toString().trim()+"&type="+spinner.getSelectedItem().toString().trim();
//
//        Toast.makeText(this, String.valueOf(id_id_user), Toast.LENGTH_SHORT).show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                showMessage(response);
//                onBackPressed();
//
//
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                showMessage(error.getMessage());
//            }
//        });
//
//
//        requestQueue = Volley.newRequestQueue(this);
//
//        requestQueue.add(stringRequest);
    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

}
