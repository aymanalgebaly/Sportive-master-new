package com.compubase.sportive.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.compubase.sportive.MainActivity;
import com.compubase.sportive.R;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.schibstedspain.leku.LocationPickerActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.TRANSITION_BUNDLE;
import static com.schibstedspain.leku.LocationPickerActivityKt.ZIPCODE;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.full_name)
    EditText fullName;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.phone_num)
    EditText phoneNum;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.center_btn)
    RadioButton centerBtn;
    @BindView(R.id.user_btn)
    RadioButton userBtn;
    @BindView(R.id.lin_one)
    RadioGroup linOne;
    @BindView(R.id.lin_radio)
    LinearLayout linRadio;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.location)
    EditText location;
    @BindView(R.id.RV_OR)
    RelativeLayout RVOR;
    @BindView(R.id.BTN_signUp_registar)
    Button BTNSignUpRegistar;
    @BindView(R.id.text_qs)
    TextView textQs;
    @BindView(R.id.text_login)
    TextView textLogin;

    private String userName, userMail, userphone, userpass,userLocation;
    int PLACE_PICKER_REQUEST = 1;

    private String radio;
    private SharedPreferences preferences;
    private String string;
    private double latitude,longitude;
    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }

    private void ValidateCenter() {
        userName = fullName.getText().toString();
        userMail = email.getText().toString();
        userphone = phoneNum.getText().toString();
        userpass = password.getText().toString();
        userLocation = location.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            fullName.setError("Name is required");
        } else if (TextUtils.isEmpty(userMail)) {
            email.setError("Email is required");
        } else if (TextUtils.isEmpty(userphone)) {
            phoneNum.setError("PhoneNumber is required");
        } else if (TextUtils.isEmpty(userpass)) {
            password.setError("Password is required");
        }
        else if (TextUtils.isEmpty(userLocation)){
            location.setError("Location is required");
        }else {
            Retrofit retrofit = RetrofitClient.getInstant();
            API api = retrofit.create(API.class);
            Call<ResponseBody> responseBodyCall = api.UserRegister(userName, userMail, userpass, userphone, radio, longitude
                    , latitude, "image", "famous", "des");
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        try {
                            assert response.body() != null;
                            String string = response.body().string();

                            //Toast.makeText(RegisterActivity.this, string, Toast.LENGTH_SHORT).show();

                            if (string.equals("True")) {
                                //onBackPressed();
                                startActivity(new Intent(RegisterActivity.this, CenterHomeActivity.class));
                            }else
                                {
                                    Toast.makeText(RegisterActivity.this, string, Toast.LENGTH_SHORT).show();
                                }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void Validate() {

        userName = fullName.getText().toString();
        userMail = email.getText().toString();
        userphone = phoneNum.getText().toString();
        userpass = password.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            fullName.setError("User name is required");
        } else if (TextUtils.isEmpty(userMail)) {
            email.setError("Email is required");
        } else if (TextUtils.isEmpty(userphone)) {
            phoneNum.setError("Birthday is required");
        } else if (TextUtils.isEmpty(userpass)) {
            password.setError("Blood Type is required");
        } else {
            Retrofit retrofit = RetrofitClient.getInstant();
            API api = retrofit.create(API.class);
            Call<ResponseBody> responseBodyCall = api.UserRegister(userName, userMail, userpass, userphone, radio, 0.0000
                    , 0.0000, "image", "famous", "des");
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        try {
                            assert response.body() != null;
                            String string = response.body().string();

                            if (string.equals("True")) {

                                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnClick({R.id.center_btn, R.id.user_btn, R.id.location, R.id.BTN_signUp_registar, R.id.text_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.center_btn:
                radio = "center";
                location.setVisibility(View.VISIBLE);
                break;
            case R.id.user_btn:
                radio = "user";
                location.setVisibility(View.GONE);
                break;
            case R.id.location:
                placePacker();
                break;
            case R.id.BTN_signUp_registar:

                if (centerBtn.isChecked()) {
                    ValidateCenter();
                } else if (userBtn.isChecked()) {
                    Validate();
                }

                break;
            case R.id.text_login:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
        }
    }

    private void placePacker() {

        Intent locationPickerIntent = new LocationPickerActivity.Builder()
                .withLocation(41.4036299, 2.1743558)
                .withSearchZone("es_ES")
                .shouldReturnOkOnBackPressed()
                .withStreetHidden()
                .withCityHidden()
                .withZipCodeHidden()
                .withSatelliteViewHidden()
                .build(RegisterActivity.this);

        startActivityForResult(locationPickerIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Log.d("RESULT****", "OK");
//            Toast.makeText(RegisterActivity.this, String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
            if (requestCode == 1) {

                latitude = data.getDoubleExtra(LATITUDE, 0.0);
//                Log.d("LATITUDE****", latitude.toString());
//                Toast.makeText(RegisterActivity.this, String.valueOf(latitude), Toast.LENGTH_SHORT).show();
                longitude = data.getDoubleExtra(LONGITUDE, 0.0);
//                Log.d("LONGITUDE****", longitude.toString());
//                Toast.makeText(RegisterActivity.this, String.valueOf(longitude), Toast.LENGTH_SHORT).show();
                address = data.getStringExtra(LOCATION_ADDRESS);
                location.setText(address);
                //Log.d("ADDRESS****", address);
                String postalcode = data.getStringExtra(ZIPCODE);
                // Log.d("POSTALCODE****", postalcode);
                Bundle bundle = data.getBundleExtra(TRANSITION_BUNDLE);
                //Log.d("BUNDLE TEXT****", bundle.getString("test"));

               /* val fullAddress = data.getParcelableExtra<Address>(ADDRESS);
                if (fullAddress != null) {
                    Log.d("FULL ADDRESS****", fullAddress.toString());
                }
                val timeZoneId = data.getStringExtra(TIME_ZONE_ID)
                Log.d("TIME ZONE ID****", timeZoneId);
                val timeZoneDisplayName = data.getStringExtra(TIME_ZONE_DISPLAY_NAME)
                Log.d("TIME ZONE NAME****", timeZoneDisplayName)
            } else if (requestCode == 2) {
                val latitude = data.getDoubleExtra(LATITUDE, 0.0)
                Log.d("LATITUDE****", latitude.toString())
                val longitude = data.getDoubleExtra(LONGITUDE, 0.0)
                Log.d("LONGITUDE****", longitude.toString())
                val address = data.getStringExtra(LOCATION_ADDRESS)
                Log.d("ADDRESS****", address.toString())
                val lekuPoi = data.getParcelableExtra<LekuPoi>(LEKU_POI)
                        Log.d("LekuPoi****", lekuPoi.toString());
            }*/

            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED");
        }


    }
}
