package com.compubase.sportive.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.compubase.sportive.R;
import com.compubase.sportive.adapter.VP_HomeUser;
import com.compubase.sportive.helper.TinyDB;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private VP_HomeUser vp_scheduleAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SharedPreferences preferences;
    private String name,email;
    private ImageView imageView;
    private TextView user_name,user_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = getSharedPreferences("user",MODE_PRIVATE);
        name = preferences.getString("name", "");
        email = preferences.getString("email", "");

//        imageView = findViewById(R.id.imageView);
//        user_name = findViewById(R.id.name_profile_system_user);
//        user_mail = findViewById(R.id.mail_profile_system_user);
//
//        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
//
//        user_name.setText(name);
//        user_mail.setText(email);


        viewPager = findViewById(R.id.viewPager_user);
        tabLayout = findViewById(R.id.tabss);
        tabLayout.setupWithViewPager(viewPager);
        vp_scheduleAdapter = new VP_HomeUser(getSupportFragmentManager());
        viewPager.setAdapter(vp_scheduleAdapter);

        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.white));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            // Handle the camera action
            startActivity(new Intent(HomeActivity.this,UserProfileActivity.class));
        } else if (id == R.id.logout) {

        } else if (id == R.id.contact_us) {

        }else if (id == R.id.myCenters){
            startActivity(new Intent(HomeActivity.this,CentersActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
