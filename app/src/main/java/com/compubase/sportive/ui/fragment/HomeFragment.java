package com.compubase.sportive.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.compubase.sportive.R;
import com.compubase.sportive.adapter.CentersAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.Center;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
public class HomeFragment extends Fragment implements OnMapReadyCallback {


    @BindView(R.id.edite_search_center)
    EditText editeSearchCenter;
    @BindView(R.id.lin_one_home)
    LinearLayout linOneHome;
    @BindView(R.id.map)
    FrameLayout mapFrame;
    @BindView(R.id.rcv_home)
    RecyclerView rcvHome;
    Unbinder unbinder;

    private CentersAdapter adapter;
    List<Center> centerList = new ArrayList<>();

    private ArrayList<Center> centerArrayList = new ArrayList<>();
    private Center center;

    private GoogleMap mMap;
    SupportMapFragment mapFragment;


    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        editeSearchCenter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());

            }
        });

        setupRecycler();
        fetchData();
        return view;
    }

    private void filter(String  s) {
        ArrayList<Center>centers = new ArrayList<>();

        for (Center cent:centerList) {

            if (cent.getName().toLowerCase().contains(s.toLowerCase())){

                centers.add(cent);
            }
        }
        adapter.filtersearch(centers);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }


    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvHome.setLayoutManager(linearLayoutManager);

    }
    private void fetchData (){
        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).ListOfCenters();

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                try {
                    assert response.body() != null;
                    centerList = Arrays.asList(gson.fromJson(response.body().string(), Center[].class));

                    if (response.isSuccessful()){

                        for (int j = 0; j <centerList.size() ; j++) {

                            center = new Center();

                            center.setName(centerList.get(j).getName());
                            center.setImages(centerList.get(j).getImages());
                            center.setId(centerList.get(j).getId());
                            center.setPhone(centerList.get(j).getPhone());
                            center.setLang(centerList.get(j).getLang());
                            center.setLat(centerList.get(j).getLat());

                            String lang = centerList.get(j).getLang();
                            String lat = centerList.get(j).getLat();
                            if(!lang.equals("") || !lat.equals(""))
                            {
                                double lo = Double.parseDouble(lang);
                                double la = Double.parseDouble(lat);
                                MarkerOptions marker = new MarkerOptions().position(new LatLng(lo,la)).title(centerList.get(j).getName());
                                mMap.addMarker(marker);
                            }

                            centerArrayList.add(center);
                        }
                        adapter = new CentersAdapter(centerArrayList);
                        rcvHome.setAdapter(adapter);
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
