package com.compubase.sportive.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.compubase.sportive.R;
import com.compubase.sportive.adapter.CommentAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.helper.TinyDB;
import com.compubase.sportive.model.CommentsListActivity;
import com.compubase.sportive.ui.fragment.CustomDialogFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrainerProfileActivity extends AppCompatActivity {

    @BindView(R.id.center_img)
    ImageView centerImg;
    @BindView(R.id.name_center)
    TextView nameCenter;
    @BindView(R.id.mail_center)
    TextView mailCenter;
    @BindView(R.id.phone_center)
    TextView phoneCenter;
    @BindView(R.id.lin_center_one)
    LinearLayout linCenterOne;
    @BindView(R.id.txt_des_details)
    TextView txtDesDetails;
    @BindView(R.id.txt_value_des_details)
    TextView txtValueDesDetails;
    @BindView(R.id.img_trainer_one_details)
    ImageView imgOneDetails;
    @BindView(R.id.img_trainer_two_details)
    ImageView imgTwoDetails;
    @BindView(R.id.lin_img_one)
    LinearLayout linImgOne;
    @BindView(R.id.img_trainer_three_details)
    ImageView imgThreeDetails;
    @BindView(R.id.img_trainer_four_details)
    ImageView imgFourDetails;
    @BindView(R.id.lin_img_two)
    LinearLayout linImgTwo;
    @BindView(R.id.add_comment_btn)
    Button addCommentBtn;
    @BindView(R.id.rcv_center_home)
    RecyclerView rcvCenterHome;
    @BindView(R.id.frame_rcv)
    FrameLayout frameRcv;
    @BindView(R.id.txt_linked)
    TextView txtLinked;
    @BindView(R.id.txt_value_linked_details)
    TextView txtValueLinkedDetails;
    private String m_Text;
    private SharedPreferences preferences;
    private int id_profile;
    private int id1;
    private String name1, imagess, img1, img2, img3, img4, des1;
    private CustomDialogFragment dialogFragment;
    private TinyDB tinyDB;
    private String id;
    private CommentsListActivity commentsListActivitie;
    private ArrayList<CommentsListActivity> commentsListActivityArrayList = new ArrayList<>();
    private CommentAdapter adapter;
    private String type1;
    private Dialog dialog;
    private EditText comment_;
    private Button submitBtn;
    private MaterialRatingBar ratingBar;
    private String comment_txt;
    private float rating;
    private String name;
    private String image;
    private String email;
    private String linked;
    private String history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent != null) {
            id1 = intent.getIntExtra("id", id_profile);
            name1 = intent.getStringExtra("name");
            imagess = intent.getStringExtra("images");
            img1 = intent.getStringExtra("imgone");
            img2 = intent.getStringExtra("imgtwo");
            img3 = intent.getStringExtra("imgthree");
            img4 = intent.getStringExtra("imgfour");
            des1 = intent.getStringExtra("desc");
            type1 = intent.getStringExtra("type");
            linked = intent.getStringExtra("linked");
            email = intent.getStringExtra("email");
            history = intent.getStringExtra("history");

        }

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        String type = preferences.getString("type", "");


        assert type != null;
        if (type.equals("trainer") || type.equals("center")) {
            addCommentBtn.setVisibility(View.INVISIBLE);
        }


        tinyDB = new TinyDB(this);
        tinyDB.putInt("id", id1);


        Glide.with(this).load(imagess).placeholder(R.drawable.user_defualt_img).into(centerImg);
        Glide.with(this).load(img1).placeholder(R.drawable.back_img).into(imgOneDetails);
        Glide.with(this).load(img2).placeholder(R.drawable.back_img).into(imgTwoDetails);
        Glide.with(this).load(img3).placeholder(R.drawable.back_img).into(imgThreeDetails);
        Glide.with(this).load(img4).placeholder(R.drawable.back_img).into(imgFourDetails);

        txtValueDesDetails.setText(des1);
        nameCenter.setText(name1);
        mailCenter.setText(email);
        txtValueLinkedDetails.setText(linked);
        txtValueLinkedDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linked));
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.isEmpty()) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linked));
                }
                startActivity(intent);
            }
        });
        phoneCenter.setText(history);

        Toast.makeText(this, linked, Toast.LENGTH_SHORT).show();

        setupRecycler();
        fetchData();

    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
//        rcvCenterHome.setNestedScrollingEnabled(true);
        rcvCenterHome.setLayoutManager(linearLayoutManager);

    }

    private void fetchData() {

        commentsListActivityArrayList.clear();

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).ViewComment(String.valueOf(id1));

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                assert response.body() != null;
                try {

                    List<CommentsListActivity> commentsListActivities = Arrays.asList(gson.fromJson(response.body().string(), CommentsListActivity[].class));
                    if (response.isSuccessful()) {

                        for (int i = 0; i < commentsListActivities.size(); i++) {

                            commentsListActivitie = new CommentsListActivity();

                            commentsListActivitie.setImages(commentsListActivities.get(i).getImages());
                            commentsListActivitie.setRate(commentsListActivities.get(i).getRate());
                            commentsListActivitie.setComment(commentsListActivities.get(i).getComment());
                            commentsListActivitie.setName(commentsListActivities.get(i).getName());
                            commentsListActivitie.setIdCenter(commentsListActivities.get(i).getIdCenter());

                            commentsListActivityArrayList.add(commentsListActivitie);

                        }

                        adapter = new CommentAdapter(commentsListActivityArrayList);
                        rcvCenterHome.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    Toast.makeText(TrainerProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TrainerProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.add_comment_btn)
    public void onViewClicked() {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fragment);
        TextView titleTV = (TextView) dialog.findViewById(R.id.txt_titte);
        submitBtn = (Button) dialog.findViewById(R.id.btn_confirm);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRate();
            }
        });
        ratingBar = (MaterialRatingBar) dialog.findViewById(R.id.ratbar);
        comment_ = (EditText) dialog.findViewById(R.id.edit_txt_dialog);
        dialog.show();
    }

    private void insertRate() {

        comment_txt = comment_.getText().toString();
        rating = ratingBar.getRating();

        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.insertComment(id, String.valueOf(id1), comment_txt, String.valueOf(rating));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();

                    if (string.equals("True")) {

                        CommentsListActivity commentsListActivity = new CommentsListActivity();
                        commentsListActivity.setComment(comment_txt);
                        commentsListActivity.setRate(String.valueOf(rating));
                        commentsListActivity.setName(name);
                        commentsListActivity.setImages(image);
                        commentsListActivity.setIdCenter(id1);


                        commentsListActivityArrayList.add(commentsListActivity);
                        adapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TrainerProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        setupRecycler();
        fetchData();

        Glide.with(this).load(imagess).placeholder(R.drawable.user_defualt_img).into(centerImg);
        Glide.with(this).load(img1).placeholder(R.drawable.back_img).into(imgOneDetails);
        Glide.with(this).load(img2).placeholder(R.drawable.back_img).into(imgTwoDetails);
        Glide.with(this).load(img3).placeholder(R.drawable.back_img).into(imgThreeDetails);
        Glide.with(this).load(img4).placeholder(R.drawable.back_img).into(imgFourDetails);

        txtValueDesDetails.setText(des1);
        nameCenter.setText(name1);
        mailCenter.setText(email);
        phoneCenter.setText(history);
        txtValueLinkedDetails.setText(linked);
    }
}
