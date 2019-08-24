package com.compubase.sportive.ui.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.compubase.sportive.R;
import com.compubase.sportive.adapter.GameAdapter;
import com.compubase.sportive.adapter.UsersAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.helper.TinyDB;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.model.GameModel;
import com.compubase.sportive.model.UsersJoinsResponse;
import com.compubase.sportive.model.UsersModel;
import com.compubase.sportive.ui.activity.CenterDetailsActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {


    @BindView(R.id.rcv)
    RecyclerView rcv;
    Unbinder unbinder;
    private UsersAdapter adapter;
    private String[] name;
    private int i;
    private String id,name_user;
    private int[] img;

    RequestQueue requestQueue;

    TinyDB tinyDB;

    List<Center> userList = new ArrayList<>();

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        unbinder = ButterKnife.bind(this, view);

        SharedPreferences shared = Objects.requireNonNull(getActivity()).getSharedPreferences("user", MODE_PRIVATE);
        id = (shared.getString("id", ""));

//        id = getArguments().getString("id", "");
//        name_user = getArguments().getString("name", "");
        setup();
        JSON_DATA_WEB_CALL();
//        //data();


        return view;
    }

    private void setup() {

        rcv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(llm);

    }





    private void JSON_DATA_WEB_CALL(){

        String url;

        url = "http://sportive.technowow.net/sportive.asmx/select_join_center?id_center="+id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // showMessage(response);

                JSON_PARSE_DATA_AFTER_WEBCALL2(response);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showMessage(error.getMessage());
            }
        });


        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        requestQueue.add(stringRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL2(String Jobj) {

        //to clear the array first
        userList.clear();

        try {

            JSONArray js = new JSONArray(Jobj);

            for (int i = 0; i < js.length(); i++) {

                JSONObject childJSONObject = js.getJSONObject(i);

                Center user = new Center();

                user.setName(childJSONObject.getString("name"));

                user.setPhone(childJSONObject.getString("phone"));

                user.setEmail(childJSONObject.getString("email"));

                user.setImages(childJSONObject.getString("images"));


                userList.add(user);

            }

            adapter = new UsersAdapter(userList);
            rcv.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        }catch (JSONException e) {
            e.printStackTrace();
        }
    }













    private void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }






    private void data() {

        List<UsersModel> usersModels = new ArrayList<>();

//        img = new int[]{R.drawable.bg_sportive, R.drawable.bg_sportive, R.drawable.bg_sportive,
//                R.drawable.bg_sportive, R.drawable.bg_sportive, R.drawable.bg_sportive,
//                R.drawable.bg_sportive, R.drawable.bg_sportive, R.drawable.bg_sportive};
        name = new String[]{"ahmed","ayman","amir","ali","hassan","hussien","hanan","hany","sameh"};

        for ( i = 0; i <name.length ; i++) {
            usersModels.add(new UsersModel(name[i]));

//            ratingBar.setRating(num[i]);
        }

       // adapter.setData(usersModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
