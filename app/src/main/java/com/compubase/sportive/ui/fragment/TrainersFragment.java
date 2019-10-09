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
import com.compubase.sportive.adapter.CentersListAdapter;
import com.compubase.sportive.adapter.TrainersAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.TrainersListModel;
import com.compubase.sportive.model.UsersJoinsResponse;
import com.compubase.sportive.ui.activity.CentersActivity;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainersFragment extends Fragment {


    @BindView(R.id.rcv_trainers)
    RecyclerView rcvTrainers;
    Unbinder unbinder;
    private String id,image,des;
    private TrainersAdapter adapter;
    private TrainersListModel trainersListModel;
    private ArrayList<TrainersListModel> trainersListModelsList = new ArrayList<>();


    public TrainersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trainers, container, false);
        unbinder = ButterKnife.bind(this, view);

        SharedPreferences shared = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        id = shared.getString("id", "");
        image = shared.getString("image", "");
        des = shared.getString("des", "");

        setup();
        fetchData();

        return view;
    }

    private void setup() {

        rcvTrainers.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rcvTrainers.setLayoutManager(llm);

    }

    private void fetchData (){

        //to clear the array first
        trainersListModelsList.clear();

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).ListOfTrainers();

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                try {
                    assert response.body() != null;
                    List<TrainersListModel> trainersListModels = Arrays.asList(gson.fromJson(response.body().string(), TrainersListModel[].class));

                    if (response.isSuccessful()){

                        for (int j = 0; j <trainersListModels.size() ; j++) {

                            trainersListModel = new TrainersListModel();

                            trainersListModel.setId(trainersListModels.get(j).getId());
                            trainersListModel.setName(trainersListModels.get(j).getName());
                            trainersListModel.setImages(trainersListModels.get(j).getImages());
                            trainersListModel.setDes(trainersListModels.get(j).getDes());
                            trainersListModel.setImg1(trainersListModels.get(j).getImg1());
                            trainersListModel.setImg2(trainersListModels.get(j).getImg2());
                            trainersListModel.setImg3(trainersListModels.get(j).getImg3());
                            trainersListModel.setImg4(trainersListModels.get(j).getImg4());
                            trainersListModel.setHistory(trainersListModels.get(j).getHistory());
                            trainersListModel.setEmail(trainersListModels.get(j).getEmail());
                            trainersListModel.setLinkedIn(trainersListModels.get(j).getLinkedIn());
//                            usersJoinsResponse.setType(userActivityActivityResponses.get(j).getType());

                            trainersListModelsList.add(trainersListModel);
                        }
                        adapter = new TrainersAdapter(trainersListModelsList);
                        rcvTrainers.setAdapter(adapter);
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
