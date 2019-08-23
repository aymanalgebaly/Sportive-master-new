package com.compubase.sportive.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.compubase.sportive.R;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.model.LoginActivityResponse;
import com.compubase.sportive.model.UserActivityActivityResponse;
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
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.mail_login)
    EditText mailLogin;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.view6)
    View view6;
    @BindView(R.id.RV_LOGIN)
    RelativeLayout RVLOGIN;
    @BindView(R.id.text_forgot_pass)
    TextView textForgotPass;
    @BindView(R.id.BTN_signIn_login)
    Button BTNSignInLogin;
    @BindView(R.id.BTN_signup_log)
    Button BTNSignupLog;
    private String email,password;
    private SharedPreferences preferences;
    private String string;
    int id;
    String name,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }

    public void userLogin() {
        email = mailLogin.getText().toString();
        password = editText.getText().toString();

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

            Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).UserLogin(email,password);

            call2.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                        try {
                            assert response.body() != null;
                            List<Center> loginActivityResponses = Arrays.asList(gson.fromJson(response.body().string(), Center[].class));
                            if (response.isSuccessful()){

                                id = loginActivityResponses.get(0).getId();
                                name = loginActivityResponses.get(0).getName();

                                type = loginActivityResponses.get(0).getType();

                                if(type.equals("center"))
                                {
                                    startActivity(new Intent(LoginActivity.this,CenterHomeActivity.class));
                                }else
                                    {
                                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                    }

                                //Toast.makeText(LoginActivity.this, String.valueOf(id), Toast.LENGTH_SHORT).show();

                                sharedLogin();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
    }

    public void sharedLogin() {

        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

        preferences = getSharedPreferences("user", MODE_PRIVATE);

        editor.putBoolean("login", true);

        editor.putString("id", String.valueOf(id));
        editor.putString("name", name);
        //editor.putString("email", email);
        //editor.putString("pass", password);
        //editor.putString("phone", userphone);
        //editor.putString("type", radio);
        //editor.putString("address",address);

        editor.apply();
    }


    @OnClick({R.id.text_forgot_pass, R.id.BTN_signIn_login, R.id.BTN_signup_log})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_forgot_pass:
                break;
            case R.id.BTN_signIn_login:
                userLogin();
                break;
            case R.id.BTN_signup_log:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }
}
