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
import com.compubase.sportive.adapter.ActivitiesAdapter;
import com.compubase.sportive.adapter.CentersAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.ActivityListResponse;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.model.UserActivityActivityResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivitiesFragment extends Fragment {


    @BindView(R.id.rcv_activities)
    RecyclerView rcvActivities;
    Unbinder unbinder;

    private ActivitiesAdapter adapter;
    List<ActivityListResponse> userActivityActivityResponses = new ArrayList<>();

    private ArrayList<ActivityListResponse> userActivityActivityResponseArrayList = new ArrayList<>();
    private ActivityListResponse userActivityResponse;
    private String id;
    private String id_user;
    private SharedPreferences preferences;


    public ActivitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        unbinder = ButterKnife.bind(this, view);

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        id_user = preferences.getString("id", "");

        Log.i( "id",id_user);
        setupRecycler();
        fetchData();

        return view;
    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(false);
        rcvActivities.setLayoutManager(linearLayoutManager);

    }
    private void fetchData (){

        //to clear the array first
        userActivityActivityResponseArrayList.clear();

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).ListOfActivities(id_user);

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                try {
                    assert response.body() != null;
                    userActivityActivityResponses = Arrays.asList(gson.fromJson(response.body().string(),ActivityListResponse[].class));

                    if (response.isSuccessful()){

                        for (int j = 0; j <userActivityActivityResponses.size() ; j++) {

                            userActivityResponse = new ActivityListResponse();

                            userActivityResponse.setName(userActivityActivityResponses.get(j).getName());
                            userActivityResponse.setImages(userActivityActivityResponses.get(j).getImages());
                            userActivityResponse.setMessage(userActivityActivityResponses.get(j).getMessage());
                            userActivityResponse.setType(userActivityActivityResponses.get(j).getType());

                            userActivityActivityResponseArrayList.add(userActivityResponse);
                        }
                        adapter = new ActivitiesAdapter(userActivityActivityResponseArrayList);
                        rcvActivities.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                Log.i("onFailure: ",t.getMessage());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
