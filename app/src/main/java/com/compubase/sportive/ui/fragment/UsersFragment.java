package com.compubase.sportive.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.compubase.sportive.R;
import com.compubase.sportive.adapter.GameAdapter;
import com.compubase.sportive.adapter.UsersAdapter;
import com.compubase.sportive.model.GameModel;
import com.compubase.sportive.model.UsersModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        unbinder = ButterKnife.bind(this, view);
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

        name = new String[]{"ahmed", "ayman", "amir", "hany", "ibra"};

        for ( i = 0; i <name.length ; i++) {
            usersModels.add(new UsersModel(name[i]));

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
