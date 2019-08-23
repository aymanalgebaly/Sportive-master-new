package com.compubase.sportive.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.compubase.sportive.R;
import com.compubase.sportive.adapter.CentersAdapter;
import com.compubase.sportive.adapter.FamousAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.model.FamousActivityResponse;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
public class FamousFragment extends Fragment {


    @BindView(R.id.rcv_famous)
    RecyclerView rcvFamous;
    Unbinder unbinder;
    private FamousAdapter adapter;
    private List<FamousActivityResponse> famousList;
    private FamousActivityResponse famous;
    private ArrayList<FamousActivityResponse> centerArrayList = new ArrayList<>();

//    private FamousAdapter adapter;


    public FamousFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_famous, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupRecycler();
        fetchData();
        return view;
    }

    private void setupRecycler() {

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),2);
        linearLayoutManager.setReverseLayout(true);
        rcvFamous.setLayoutManager(linearLayoutManager);

    }
    private void fetchData (){

        //to clear the array first
        centerArrayList.clear();

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).ListOfCenters();

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                try {
                    assert response.body() != null;
                    famousList = Arrays.asList(gson.fromJson(response.body().string(), FamousActivityResponse[].class));

                    if (response.isSuccessful()){

                        for (int j = 0; j <famousList.size() ; j++) {

                            famous = new FamousActivityResponse();

                            famous.setName(famousList.get(j).getName());
                            famous.setImages(famousList.get(j).getType());
                            famous.setId(famousList.get(j).getId());


                            centerArrayList.add(famous);
                        }
                        adapter = new FamousAdapter(centerArrayList);
                        rcvFamous.setAdapter(adapter);
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
