package com.compubase.sportive.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.compubase.sportive.R;
import com.compubase.sportive.adapter.CentersAdapter;
import com.compubase.sportive.data.API;
import com.compubase.sportive.helper.RetrofitClient;
import com.compubase.sportive.model.Center;
import com.compubase.sportive.ui.fragment.HomeFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CentersTrainersActivity extends AppCompatActivity {

    @BindView(R.id.center_trainer_toolbar)
    Toolbar centerTrainerToolbar;
    @BindView(R.id.abb_bar_trainer_profile)
    AppBarLayout abbBarTrainerProfile;
    @BindView(R.id.rcv_centers_trainers)
    FrameLayout rcvCentersTrainers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centers_trainers);
        ButterKnife.bind(this);


        setSupportActionBar(centerTrainerToolbar);

        HomeFragment homeFragment = new HomeFragment();
        displaySelectedFragment(homeFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trainer_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_profile_trainer:
                startActivity(new Intent(CentersTrainersActivity.this, TrainerActivity.class));
            case R.id.log_out_trainer:
                finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    public void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.rcv_centers_trainers, fragment);
        fragmentTransaction.commit();
    }
}
