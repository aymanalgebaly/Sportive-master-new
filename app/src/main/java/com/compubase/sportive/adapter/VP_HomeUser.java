package com.compubase.sportive.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.compubase.sportive.ui.fragment.ActivitiesCenterFragment;
import com.compubase.sportive.ui.fragment.ActivitiesFragment;
import com.compubase.sportive.ui.fragment.FamousFragment;
import com.compubase.sportive.ui.fragment.HomeFragment;
import com.compubase.sportive.ui.fragment.TrainersFragment;

public class VP_HomeUser extends FragmentPagerAdapter {

    Fragment[] fragments ={new HomeFragment(),new FamousFragment(),new ActivitiesFragment(),new TrainersFragment()};

    String title;

    public VP_HomeUser(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                title = "Home";
                break;
            case 1:
                title =  "Famous";
                break;
            case 2:
                title = "Activities";
                break;
            case 3:
                title = "Trainers";
                break;

        }
        return title;
    }
}
