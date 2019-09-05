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
import com.compubase.sportive.helper.TinyDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserJoinActivity extends AppCompatActivity {

    @BindView(R.id.pay_cash_btn)
    Button payCashBtn;
    @BindView(R.id.pay_online_btn)
    Button payOnlineBtn;
    private String myid;
    private String id;
    private String id_center;
    private String s_id_center;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_join);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(this);

        id = tinyDB.getString("id");

        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        myid = (shared.getString("id", ""));

//         id_center = Objects.requireNonNull(getIntent().getExtras()).getString("id_center","");
//         s_id_center = String.valueOf(id_center);

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

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
                Payment();
                break;
        }
    }

    private void Payment() {

        Intent in = new Intent(getApplicationContext(), PayTabActivity.class);
        in.putExtra(PaymentParams.MERCHANT_EMAIL, "sportive2050@gmail.com"); //this a demo account for testing the sdk
        in.putExtra(PaymentParams.SECRET_KEY,"t5eeZqLRUSZ2lTCzYhruLiKShpuKFwb9CqnCR9tL2tOomrXlIoPuHznYZSIEoUO1kcDbl7XoBMMXdKjW98qQHNPGGxl5s96MmJYH");//Add your Secret Key Here
        in.putExtra(PaymentParams.LANGUAGE,PaymentParams.ENGLISH);
        in.putExtra(PaymentParams.TRANSACTION_TITLE, "Payment");
        in.putExtra(PaymentParams.AMOUNT, 25.0);

        in.putExtra(PaymentParams.CURRENCY_CODE, "SAU");
        in.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "00966515435133");
        in.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com");
        in.putExtra(PaymentParams.ORDER_ID, "123456");
        in.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2");

//Billing Address
        in.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345");
        in.putExtra(PaymentParams.CITY_BILLING, "jeddah");
        in.putExtra(PaymentParams.STATE_BILLING, "jeddah");
        in.putExtra(PaymentParams.COUNTRY_BILLING, "SAR");
        in.putExtra(PaymentParams.POSTAL_CODE_BILLING, "21577"); //Put Country Phone code if Postal code not available '00973'

//Shipping Address
        in.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345");
        in.putExtra(PaymentParams.CITY_SHIPPING, "jeddah");
        in.putExtra(PaymentParams.STATE_SHIPPING, "jeddah");
        in.putExtra(PaymentParams.COUNTRY_SHIPPING, "SAR");
        in.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, "21577"); //Put Country Phone code if Postal code not available '00973'

//Payment Page Style
        in.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#2474bc");
//        in.putExtra(PaymentParams.THEME, PaymentParams.THEME_LIGHT);

//Tokenization
        in.putExtra(PaymentParams.IS_TOKENIZATION, true);
        startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);

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
