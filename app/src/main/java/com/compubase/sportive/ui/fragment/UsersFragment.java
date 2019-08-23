package com.compubase.sportive.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.compubase.sportive.R;
import com.compubase.sportive.adapter.GameAdapter;
import com.compubase.sportive.adapter.UsersAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.model.GameModel;
import com.compubase.sportive.model.UsersJoinsResponse;
import com.compubase.sportive.model.UsersModel;
import com.compubase.sportive.ui.activity.CenterDetailsActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {


    @BindView(R.id.rcv_users)
    RecyclerView rcvUsers;
    Unbinder unbinder;
    private UsersAdapter adapter;
    private String[] name;
    private int i;
    private String id,name_user;
    private int[] img;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        unbinder = ButterKnife.bind(this, view);

//        id = getArguments().getString("id", "");
//        name_user = getArguments().getString("name", "");
        setup();
        data();


        return view;
    }

    private void setup() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
        rcvUsers.setLayoutManager(gridLayoutManager);
        adapter = new UsersAdapter(getActivity());
        rcvUsers.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void data() {

        List<UsersModel> usersModels = new ArrayList<>();

        img = new int[]{R.drawable.bg_sportive, R.drawable.bg_sportive, R.drawable.bg_sportive,
                R.drawable.bg_sportive, R.drawable.bg_sportive, R.drawable.bg_sportive,
                R.drawable.bg_sportive, R.drawable.bg_sportive, R.drawable.bg_sportive};
        name = new String[]{"ahmed","ayman","amir","ali","hassan","hussien","hanan","hany","sameh"};

        for ( i = 0; i <img.length ; i++) {
            usersModels.add(new UsersModel(name[i], img[i]));

//            ratingBar.setRating(num[i]);
        }

        adapter.setData(usersModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
