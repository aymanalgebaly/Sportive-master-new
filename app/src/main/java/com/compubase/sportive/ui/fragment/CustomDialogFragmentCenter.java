package com.compubase.sportive.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compubase.sportive.R;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.helper.TinyDB;

import java.io.IOException;
import java.util.Objects;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomDialogFragmentCenter extends DialogFragment {

//    @BindView(R.id.txt_titte)
//    TextView txtTitte;
//    @BindView(R.id.ratbar)
//    MaterialRatingBar ratbar;
//    @BindView(R.id.edit_txt_dialog)
//    EditText editTxtDialog;
//    @BindView(R.id.btn_confirm)
//    Button btnConfirm;
//    Unbinder unbinder;
    private String s_edit,s_rating;
    private SharedPreferences preferences;
    private String id;
    private int id_id;
    private int id_center;
    private String s_id_center;
    private TinyDB tinyDB;
    private EditText editTxtDialog;
    private TextView txtTitte;
    private MaterialRatingBar ratingBar;
    private Button confirm;
    private float rating;
    private String type;
    private View rootView;
    private String type_center,type_trainer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.dialog_fragment, container);

        confirm = rootView.findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRate();
            }
        });
        txtTitte = rootView.findViewById(R.id.txt_titte);

        ratingBar = rootView.findViewById(R.id.ratbar);

        editTxtDialog = rootView.findViewById(R.id.edit_txt_dialog);


        tinyDB = new TinyDB(getActivity());
        int id_user = tinyDB.getInt("id");
        s_id_center = String.valueOf(id_user);
        type_center = tinyDB.getString("type_center");
        type_trainer = tinyDB.getString("type_trainer");

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        this.id = preferences.getString("id", "");
        type = preferences.getString("type", "");


        return rootView;
    }

    private void insertRate() {

        s_edit = editTxtDialog.getText().toString();
        rating = ratingBar.getRating();

        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.insertComment(id, s_id_center, s_edit, String.valueOf(rating));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();

                    if (string.equals("True")){

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
