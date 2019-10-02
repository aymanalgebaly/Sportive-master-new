package com.compubase.sportive.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.compubase.sportive.R;
import com.compubase.sportive.adapter.VP_HomeCenter;
import com.compubase.sportive.adapter.VP_HomeUser;

public class CenterHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private VP_HomeCenter vp_scheduleAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView imageView;
    private TextView user_name,user_mail;
    private SharedPreferences preferences;
    private String name,email;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_home);

        preferences = getSharedPreferences("user",MODE_PRIVATE);
        name = preferences.getString("name", "");
        email = preferences.getString("email", "");
        image = preferences.getString("image", "");

        viewPager = findViewById(R.id.viewPager_center);
        tabLayout = findViewById(R.id.tabss_center);
        tabLayout.setupWithViewPager(viewPager);
        vp_scheduleAdapter = new VP_HomeCenter(getSupportFragmentManager());
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


        Toolbar toolbar = findViewById(R.id.toolbar_center);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView name_txt = header.findViewById(R.id.name_profile_system_user);
        ImageView imageView = header.findViewById(R.id.imageView_user);
        TextView email_txt = header.findViewById(R.id.mail_profile_system_user);

        name_txt.setText(name);
        email_txt.setText(email);
        Glide.with(this).load(image).placeholder(R.drawable.bg_sportive).into(imageView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//        imageView = findViewById(R.id.imageView_center);
//        user_name = findViewById(R.id.center_name_system);
//        user_mail = findViewById(R.id.center_mail_system);
//
//        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
//
//        user_name.setText(name);
//        user_mail.setText(email);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            alartExit();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.center_profile) {

            startActivity(new Intent(CenterHomeActivity.this,EditProfileCenterActivity.class));
            // Handle the camera action
        } else if (id == R.id.center_logout) {

//            alartExit();
            startActivity(new Intent(CenterHomeActivity.this,LoginActivity.class));
            finish();

        } else if (id == R.id.center_contact_us) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void alartExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);

                //Main2Activity.this.finish();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}
