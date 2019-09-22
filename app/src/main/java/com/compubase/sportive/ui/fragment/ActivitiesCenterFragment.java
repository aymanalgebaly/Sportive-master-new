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
import com.compubase.sportive.adapter.CentersListAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.ActivityListResponse;
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
public class ActivitiesCenterFragment extends Fragment {

    @BindView(R.id.rcv_center_activ)
    RecyclerView rcvCenterActiv;
    Unbinder unbinder;
    private ActivitiesAdapter adapter;

    private ArrayList<ActivityListResponse> userActivityActivityResponseArrayList = new ArrayList<>();
    private ActivityListResponse userActivityResponse;
    private String id;
    private String id_user;
    private SharedPreferences preferences;
    private List<ActivityListResponse> activityListResponses = new ArrayList<>();


    public ActivitiesCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activities_center, container, false);
        unbinder = ButterKnife.bind(this, view);

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        id_user = preferences.getString("id", "");

        setupRecycler();
        fetchData();
        return view;
    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(false);
        rcvCenterActiv.setLayoutManager(linearLayoutManager);

    }

    private void fetchData() {

        userActivityActivityResponseArrayList.clear();

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).ListOfActivities(id_user);

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                try {
                    assert response.body() != null;
                    activityListResponses = Arrays.asList(gson.fromJson(response.body().string(), ActivityListResponse[].class));

                    if (response.isSuccessful()) {

                        for (int j = 0; j < activityListResponses.size(); j++) {

                            userActivityResponse = new ActivityListResponse();

                            userActivityResponse.setName(activityListResponses.get(j).getName());
                            userActivityResponse.setImages(activityListResponses.get(j).getImages());
                            userActivityResponse.setMessage(activityListResponses.get(j).getMessage());
                            userActivityResponse.setType(activityListResponses.get(j).getType());

                            userActivityActivityResponseArrayList.add(userActivityResponse);
                        }
                        adapter = new ActivitiesAdapter(userActivityActivityResponseArrayList);
                        rcvCenterActiv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                Log.i("onFailure: ", t.getMessage());
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
