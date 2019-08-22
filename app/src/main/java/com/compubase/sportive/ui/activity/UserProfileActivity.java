package com.compubase.sportive.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.img_user_profile)
    CircleImageView imgUserProfile;
    @BindView(R.id.rel_img)
    RelativeLayout relImg;
    @BindView(R.id.full_name_user_profile)
    EditText fullNameUserProfile;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.email_user_profile)
    EditText emailUserProfile;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.password_user_profile)
    EditText passwordUserProfile;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.phone_num_user_profile)
    EditText phoneNumUserProfile;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.RV_OR)
    RelativeLayout RVOR;
    @BindView(R.id.change_img)
    TextView changeImg;

    private int GALLERY_REQUEST_CODE = 1;

    private SharedPreferences preferences ;
    private Bitmap bitmap;
    private String name,email,pass,phone,id;
    private String userName,userMail,userphone,userpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        name =preferences.getString("name","");
        email = preferences.getString("email", "");
        pass = preferences.getString("pass", "");
        phone = preferences.getString("phone", "");
        id = preferences.getString("id", "");

        fullNameUserProfile.setText(name);
        emailUserProfile.setText(email);
        passwordUserProfile.setText(pass);
        phoneNumUserProfile.setText(phone);

    }

    @OnClick({R.id.img_user_profile, R.id.btn_edit, R.id.change_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_user_profile:
                break;
            case R.id.btn_edit:
                updateData();
                break;
            case R.id.change_img:
                pickFromGallery();
                break;
        }
    }

    private void updateData() {

        userName = fullNameUserProfile.getText().toString();
        userMail = emailUserProfile.getText().toString();
        userphone = phoneNumUserProfile.getText().toString();
        userpass = passwordUserProfile.getText().toString();

        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.UpdateData(userName, userMail, userpass, userphone,0.000 ,
                0.0000, "image", "famous", "des",id);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        String string = response.body().string();


                        if (string.equals("True")) {
                            startActivity(new Intent(UserProfileActivity.this, CenterHomeActivity.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void pickFromGallery() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                imgUserProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
