package com.compubase.sportive.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.compubase.sportive.R;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.helper.TinyDB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditProfileCenterActivity extends AppCompatActivity {

    @BindView(R.id.center_profile_toolbar)
    Toolbar centerProfileToolbar;
    @BindView(R.id.abb_bar_center_profile)
    AppBarLayout abbBarCenterProfile;
    @BindView(R.id.img_center_edit_profile)
    CircleImageView imgCenterEditProfile;
    @BindView(R.id.change_img)
    TextView changeImg;
    @BindView(R.id.rel_img)
    RelativeLayout relImg;
    @BindView(R.id.full_name_center_profile)
    EditText fullNameCenterProfile;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.email_center_profile)
    EditText emailCenterProfile;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.password_center_profile)
    EditText passwordCenterProfile;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.phone_num_center_profile)
    EditText phoneNumCenterProfile;
    @BindView(R.id.view4)
    View view4;
//    @BindView(R.id.view5)
//    View view5;
    @BindView(R.id.btn_edit_center_profile)
    Button btnEditCenterProfile;
    @BindView(R.id.RV_OR)
    RelativeLayout RVOR;
    @BindView(R.id.txt_profile)
    TextView txtProfile;
    @BindView(R.id.desc_center_profile)
    EditText descCenterProfile;
    @BindView(R.id.view10)
    View view10;
    @BindView(R.id.history_center_profile)
    EditText historyCenterProfile;
    @BindView(R.id.view20)
    View view20;
    @BindView(R.id.txt_upload_img)
    TextView txtUploadImg;
    @BindView(R.id.lin_img)
    LinearLayout linImg;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.RV_profile)
    RelativeLayout RVProfile;
    @BindView(R.id.txt_add_game)
    TextView txtAddGame;
    @BindView(R.id.add_btn_center_profile)
    Button addBtnCenterProfile;

    private int GALLERY_REQUEST_CODE = 1;

    private SharedPreferences preferences ;
    private Bitmap bitmap;
    private String name,email,pass,phone,id;
    private String userName,userMail,userphone,userpass;

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    
    TinyDB tinyDB;

    StorageReference storageReference;
    FirebaseStorage storage;

    Uri filePath;

    String imageURL;
    private String type;
    private String des;
    private String userDesc;
    private int GALLERY_REQUEST_CODE_TWO = 2;

    private int GALLERY_REQUEST_CODE_THREE = 3;
    private int GALLERY_REQUEST_CODE_FOUR = 4;
    private int GALLERY_REQUEST_CODE_FIVE = 5;
    private String imgone,imgtwo,imgthree,imgfour;
    private String history;
    private String description;
    private String hist;
    private String lat,s_Long;

    String pic1,pic2,pic3,pic4,pic5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_center);
        ButterKnife.bind(this);

//        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

//        preferences = getSharedPreferences("user", MODE_PRIVATE);
//
//        editor.putBoolean("login", true);
//
//        editor.putString("des",description);
//        editor.putString("history",hist);
//
//        editor.apply();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        img1 = findViewById(R.id.pic1);
        img2 = findViewById(R.id.pic2);
        img3 = findViewById(R.id.pic3);
        img4 = findViewById(R.id.pic4);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGalleryTwo(GALLERY_REQUEST_CODE_TWO);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGalleryTwo(GALLERY_REQUEST_CODE_THREE);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGalleryTwo(GALLERY_REQUEST_CODE_FOUR);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGalleryTwo(GALLERY_REQUEST_CODE_FIVE);
            }
        });

        FirebaseApp.initializeApp(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        name =preferences.getString("name","");
        email = preferences.getString("email", "");
        pass = preferences.getString("pass", "");
        phone = preferences.getString("phone", "");
        id = preferences.getString("id", "");
        type = preferences.getString("type", "");
        des = preferences.getString("des", "");
        imgone = preferences.getString("imgone", "");
        imgtwo = preferences.getString("imgtwo", "");
        imgthree = preferences.getString("imgthree", "");
        imgfour = preferences.getString("imgfour", "");
        history = preferences.getString("history", "");
        lat = preferences.getString("lat", "");
        s_Long = preferences.getString("long", "");

        imageURL = preferences.getString("image","image");

        Glide.with(this).load(imageURL).placeholder(R.drawable.center_defult_img).into(imgCenterEditProfile);


        fullNameCenterProfile.setText(name);
        emailCenterProfile.setText(email);
        passwordCenterProfile.setText(pass);
        phoneNumCenterProfile.setText(phone);
        descCenterProfile.setText(des);
        historyCenterProfile.setText(history);

        Glide.with(this).load(imgone).placeholder(R.drawable.back_img).into(img1);
        Glide.with(this).load(imgtwo).placeholder(R.drawable.back_img).into(img2);
        Glide.with(this).load(imgthree).placeholder(R.drawable.back_img).into(img3);
        Glide.with(this).load(imgfour).placeholder(R.drawable.back_img).into(img4);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
    }

    @OnClick({R.id.change_img, R.id.btn_edit_center_profile, R.id.btn_save, R.id.add_btn_center_profile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_img:
                pickFromGallery();
                break;
            case R.id.btn_edit_center_profile:
                updateData();
                break;
            case R.id.btn_save:
                updateProfile();
                break;
            case R.id.add_btn_center_profile:
                startActivity(new Intent(EditProfileCenterActivity.this,AddGameActivity.class));
                break;
        }
    }

    private void updateProfile() {

        des = descCenterProfile.getText().toString();
        history = historyCenterProfile.getText().toString();

        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.UpdateProfile(des, history, imgone, imgtwo, imgthree, imgfour, id);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String string = response.body().string();

                        if (string.equals("True")){

                            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

                            preferences = getSharedPreferences("user", MODE_PRIVATE);

                            editor.putBoolean("login", true);

                            editor.putString("des",des);
                            editor.putString("history",history);

                            editor.apply();

                            descCenterProfile.setText(des);
                            historyCenterProfile.setText(history);

                            Toast.makeText(EditProfileCenterActivity.this, des, Toast.LENGTH_SHORT).show();


                            startActivity(new Intent(EditProfileCenterActivity.this,CenterHomeActivity.class));

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditProfileCenterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData() {

        name = fullNameCenterProfile.getText().toString();
        email = emailCenterProfile.getText().toString();
        phone = phoneNumCenterProfile.getText().toString();
        pass = passwordCenterProfile.getText().toString();
        des = descCenterProfile.getText().toString();

        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.UpdateData(name, email, pass, phone, Double.valueOf(s_Long),
                lat, imageURL, "famous",des,id);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {

                        assert response.body() != null;
                        String string = response.body().string();


                        if (string.equals("True")) {

                            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

                            preferences = getSharedPreferences("user", MODE_PRIVATE);

                            editor.putBoolean("login", true);

                            editor.putString("name",name);
                            editor.putString("email",email);
                            editor.putString("phone",phone);
                            editor.putString("pass",pass);
//                            editor.putString("des",des);

                            editor.apply();

                            fullNameCenterProfile.setText(name);
                            emailCenterProfile.setText(email);
                            passwordCenterProfile.setText(pass);
                            phoneNumCenterProfile.setText(phone);
//                            descCenterProfile.setText(des);
                            Toast.makeText(EditProfileCenterActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                            if (type.equals("center")){
                                startActivity(new Intent(EditProfileCenterActivity.this,CenterHomeActivity.class));
                            }else {
                                startActivity(new Intent(EditProfileCenterActivity.this,HomeActivity.class));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditProfileCenterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pickFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    private void pickFromGalleryTwo(int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), code);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Toast.makeText(this, String.valueOf(requestCode), Toast.LENGTH_SHORT).show();

        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            Bitmap bitmap;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(EditProfileCenterActivity.this.getContentResolver(), filePath);

                imgCenterEditProfile.setImageBitmap(bitmap);

                uploadImage(filePath,GALLERY_REQUEST_CODE);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(requestCode == GALLERY_REQUEST_CODE_TWO && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            Bitmap bitmap;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(EditProfileCenterActivity.this.getContentResolver(), filePath);

                img1.setImageBitmap(bitmap);

                uploadImage(filePath,GALLERY_REQUEST_CODE_TWO);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(requestCode == GALLERY_REQUEST_CODE_THREE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            Bitmap bitmap;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(EditProfileCenterActivity.this.getContentResolver(), filePath);

                img2.setImageBitmap(bitmap);

                uploadImage(filePath,GALLERY_REQUEST_CODE_THREE);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(requestCode == GALLERY_REQUEST_CODE_FOUR && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            Bitmap bitmap;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(EditProfileCenterActivity.this.getContentResolver(), filePath);

                img3.setImageBitmap(bitmap);

                uploadImage(filePath,GALLERY_REQUEST_CODE_FOUR);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(requestCode == GALLERY_REQUEST_CODE_FIVE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            Bitmap bitmap;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(EditProfileCenterActivity.this.getContentResolver(), filePath);

                img4.setImageBitmap(bitmap);

                uploadImage(filePath,GALLERY_REQUEST_CODE_FIVE);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImage(Uri customfilepath, final int requestCode) {

        if(customfilepath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(customfilepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                @Override
                                public void onSuccess(Uri uri) {

                                    progressDialog.dismiss();

                                    Toast.makeText(EditProfileCenterActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                   if (requestCode == GALLERY_REQUEST_CODE){
                                       imageURL = uri.toString();
                                   }else if (requestCode == GALLERY_REQUEST_CODE_TWO){
                                       imgone = uri.toString();
                                   }else if (requestCode == GALLERY_REQUEST_CODE_THREE){
                                       imgtwo = uri.toString();
                                   }else if (requestCode == GALLERY_REQUEST_CODE_FOUR){
                                       imgthree = uri.toString();
                                   }else if (requestCode == GALLERY_REQUEST_CODE_FIVE){
                                       imgfour = uri.toString();
                                   }

                                    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

                                    preferences = getSharedPreferences("user", MODE_PRIVATE);

                                    editor.putBoolean("login", true);

                                    editor.putString("image", imageURL);
                                    editor.putString("name",name);
                                    editor.putString("email",email);
                                    editor.putString("phone",phone);
                                    editor.putString("pass",pass);
                                    editor.putString("imgone",imgone);
                                    editor.putString("imgtwo",imgtwo);
                                    editor.putString("imgthree",imgthree);
                                    editor.putString("imgfour",imgfour);

                                    editor.apply();

                                    Glide.with(EditProfileCenterActivity.this).load(imageURL).into(imgCenterEditProfile);
                                    Glide.with(EditProfileCenterActivity.this).load(imgone).into(img1);
                                    Glide.with(EditProfileCenterActivity.this).load(imgtwo).into(img2);
                                    Glide.with(EditProfileCenterActivity.this).load(imgthree).into(img3);
                                    Glide.with(EditProfileCenterActivity.this).load(imgfour).into(img4);

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditProfileCenterActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}
