package com.compubase.sportive.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.compubase.sportive.ui.fragment.ActivitiesCenterFragment;
import com.compubase.sportive.ui.fragment.ActivitiesFragment;
import com.compubase.sportive.ui.fragment.FamousFragment;
import com.compubase.sportive.ui.fragment.HomeCenterFragment;
import com.compubase.sportive.ui.fragment.HomeFragment;
import com.compubase.sportive.ui.fragment.UsersFragment;

public class VP_HomeCenter extends FragmentPagerAdapter {

    Fragment[] fragments ={new HomeCenterFragment(),new UsersFragment(),new ActivitiesCenterFragment()};

    String title;


    public VP_HomeCenter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
