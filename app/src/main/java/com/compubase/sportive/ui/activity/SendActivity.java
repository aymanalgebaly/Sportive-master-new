package com.compubase.sportive.ui.activity;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.compubase.sportive.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendActivity extends AppCompatActivity {
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.msg)
    EditText msg;
    @BindView(R.id.send_btn)
    Button sendBtn;

    List<String> types = new ArrayList<>();

    String myid,user_id;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);

        types.add("Rate");
        types.add("Update");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SendActivity.this, android.R.layout.simple_spinner_item, types);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        myid = (shared.getString("id", ""));


        user_id = String.valueOf(Objects.requireNonNull(getIntent().getExtras()).getInt("id_center"));
    }

    @OnClick(R.id.send_btn)
    public void onViewClicked() {

        JSON_DATA_WEB_CALL();
    }


    private void JSON_DATA_WEB_CALL(){

        String url;

        url = "http://sportive.technowow.net/sportive.asmx/insert_activites?id_send="+myid+"&id_recived="+user_id+"&message="+msg.getText().toString().trim()+"&type="+spinner.getSelectedItem().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                 showMessage(response);


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showMessage(error.getMessage());
            }
        });


        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

}
