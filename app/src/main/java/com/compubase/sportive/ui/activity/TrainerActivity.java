package com.compubase.sportive.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.compubase.sportive.R;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
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

public class TrainerActivity extends AppCompatActivity {

//    @BindView(R.id.center_trainer_toolbar)
//    Toolbar centerTrainerToolbar;
//    @BindView(R.id.abb_bar_trainer_profile)
//    AppBarLayout abbBarTrainerProfile;
    @BindView(R.id.img_trainer_edit_profile)
    CircleImageView imgTrainerEditProfile;
    @BindView(R.id.change_img_trainer)
    TextView changeImgTrainer;
    @BindView(R.id.rel_img)
    RelativeLayout relImg;
    @BindView(R.id.full_name_trainer_profile)
    EditText fullNameTrainerProfile;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.email_trainer_profile)
    EditText emailTrainerProfile;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.password_trainer_profile)
    EditText passwordTrainerProfile;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.phone_num_trainer_profile)
    EditText phoneNumTrainerProfile;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.btn_edit_trainer_profile)
    Button btnEditTrainerProfile;
    @BindView(R.id.RV_OR)
    RelativeLayout RVOR;
    @BindView(R.id.txt_profile_trainer)
    TextView txtProfileTrainer;
    @BindView(R.id.desc_trainer_profile)
    EditText descTrainerProfile;
    @BindView(R.id.view10)
    View view10;
    @BindView(R.id.history_trainer_profile)
    EditText historyTrainerProfile;
    @BindView(R.id.btn_save_trainer)
    Button btnSaveTrainer;
    @BindView(R.id.RV_profile)
    RelativeLayout RVProfile;

    private int GALLERY_REQUEST_CODE = 1;
    private String imgone,imgtwo,imgthree,imgfour;


    private SharedPreferences preferences;
    private String id, name, email, phone, pass, type ;

    StorageReference storageReference;
    FirebaseStorage storage;

    Uri filePath;

    String imageURL;
    private String history,des;

    private int GALLERY_REQUEST_CODE_TWO = 2;

    private int GALLERY_REQUEST_CODE_THREE = 3;
    private int GALLERY_REQUEST_CODE_FOUR = 4;
    private int GALLERY_REQUEST_CODE_FIVE = 5;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        FirebaseApp.initializeApp(TrainerActivity.this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        id = preferences.getString("id", "");
        name =preferences.getString("name","");
        email = preferences.getString("email", "");
        pass = preferences.getString("pass", "");
        phone = preferences.getString("phone", "");
        id = preferences.getString("id", "");
        type = preferences.getString("type", "");
        history = preferences.getString("history", "");
        des = preferences.getString("des", "");
        imageURL = preferences.getString("image","");

        img1 = findViewById(R.id.pic1);
        img2 = findViewById(R.id.pic2);
        img3 = findViewById(R.id.pic3);
        img4 = findViewById(R.id.pic4);


        fullNameTrainerProfile.setText(name);
        emailTrainerProfile.setText(email);
        phoneNumTrainerProfile.setText(phone);
        passwordTrainerProfile.setText(pass);
        descTrainerProfile.setText(des);
        historyTrainerProfile.setText(history);

        Glide.with(this).load(imageURL).placeholder(R.drawable.user_defualt_img).into(imgTrainerEditProfile);
        Glide.with(this).load(imgone).placeholder(R.drawable.back_img).into(img1);
        Glide.with(this).load(imgtwo).placeholder(R.drawable.back_img).into(img2);
        Glide.with(this).load(imgthree).placeholder(R.drawable.back_img).into(img3);
        Glide.with(this).load(imgfour).placeholder(R.drawable.back_img).into(img4);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

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
    }

    @OnClick({R.id.change_img_trainer, R.id.btn_edit_trainer_profile, R.id.btn_save_trainer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_img_trainer:
                pickFromGallery();
                break;
            case R.id.btn_edit_trainer_profile:
                updateData();
                break;
            case R.id.btn_save_trainer:
                updateProfile();
                break;
        }
    }

    private void updateProfile() {

        des = descTrainerProfile.getText().toString();
        history = historyTrainerProfile.getText().toString();

        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.UpdateProfile(des, history, "", "", "", "", id);
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

                            descTrainerProfile.setText(des);
                            historyTrainerProfile.setText(history);

                            Toast.makeText(TrainerActivity.this, "Saved", Toast.LENGTH_SHORT).show();


                            startActivity(new Intent(TrainerActivity.this,CentersTrainersActivity.class));

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TrainerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData() {

        name = fullNameTrainerProfile.getText().toString();
        email = emailTrainerProfile.getText().toString();
        phone = phoneNumTrainerProfile.getText().toString();
        pass = passwordTrainerProfile.getText().toString();
        des = descTrainerProfile.getText().toString();

        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.UpdateData(name, email, pass, phone, 0.000,
                0.000, imageURL, "famous",des,id);
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

                            fullNameTrainerProfile.setText(name);
                            emailTrainerProfile.setText(email);
                            passwordTrainerProfile.setText(pass);
                            phoneNumTrainerProfile.setText(phone);
//                            descCenterProfile.setText(des);
                            Toast.makeText(TrainerActivity.this, "Updated", Toast.LENGTH_SHORT).show();

//                            if (type.equals("center")){
//                                startActivity(new Intent(TrainerActivity.this,CenterHomeActivity.class));
//                            }else {
//                                startActivity(new Intent(TrainerActivity.this,HomeActivity.class));
//                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TrainerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                bitmap = MediaStore.Images.Media.getBitmap(TrainerActivity.this.getContentResolver(), filePath);

                imgTrainerEditProfile.setImageBitmap(bitmap);

                uploadImage(filePath,GALLERY_REQUEST_CODE);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(requestCode == GALLERY_REQUEST_CODE_TWO && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            Bitmap bitmap;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(TrainerActivity.this.getContentResolver(), filePath);

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

                bitmap = MediaStore.Images.Media.getBitmap(TrainerActivity.this.getContentResolver(), filePath);

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

                bitmap = MediaStore.Images.Media.getBitmap(TrainerActivity.this.getContentResolver(), filePath);

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

                bitmap = MediaStore.Images.Media.getBitmap(TrainerActivity.this.getContentResolver(), filePath);

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

                                    Toast.makeText(TrainerActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

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

                                    Glide.with(TrainerActivity.this).load(imageURL).into(imgTrainerEditProfile);
                                    Glide.with(TrainerActivity.this).load(imgone).into(img1);
                                    Glide.with(TrainerActivity.this).load(imgtwo).into(img2);
                                    Glide.with(TrainerActivity.this).load(imgthree).into(img3);
                                    Glide.with(TrainerActivity.this).load(imgfour).into(img4);

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(TrainerActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // or finish();
    }
}
