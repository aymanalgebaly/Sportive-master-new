package com.compubase.sportive.ui.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.compubase.sportive.R;
import com.compubase.sportive.adapter.CentersAdapter;
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
    private ArrayList<UsersJoinsResponse> usersJoinsResponseArrayList;
    private String id_center;

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

        SharedPreferences preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        id_center = preferences.getString("id", "");
        setup();
        data();


        return view;
    }

    private void setup() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
        rcvUsers.setLayoutManager(gridLayoutManager);
    }

    private void data() {

            Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).UsersJoin(id_center);

            call2.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    try {
                        assert response.body() != null;
                        List<UsersJoinsResponse> usersJoinsResponses =
                                Arrays.asList(gson.fromJson(response.body().string(), UsersJoinsResponse[].class));

                        if (response.isSuccessful()){

                            for (int j = 0; j <usersJoinsResponses.size() ; j++) {

                                Toast.makeText(getActivity(), usersJoinsResponses.get(j).getName(), Toast.LENGTH_SHORT).show();

                                Log.i( "onResponse: ",usersJoinsResponses.get(j).getName());

                                UsersJoinsResponse usersJoins = new UsersJoinsResponse();

                                usersJoins.setName(usersJoinsResponses.get(j).getName());

                                usersJoinsResponseArrayList = new ArrayList<>();

                                usersJoinsResponseArrayList.add(usersJoins);
                            }
                            adapter = new UsersAdapter(usersJoinsResponseArrayList);
                            rcvUsers.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
