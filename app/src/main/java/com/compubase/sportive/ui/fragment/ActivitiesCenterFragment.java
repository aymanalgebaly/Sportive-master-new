package com.compubase.sportive.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.compubase.sportive.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivitiesCenterFragment extends Fragment {


    public ActivitiesCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities_center, container, false);
    }

}
