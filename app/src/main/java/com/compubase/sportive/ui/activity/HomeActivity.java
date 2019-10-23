package com.compubase.sportive.ui.activity;

import android.content.Context;
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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

    private View mView;
    private DrawerLayout drawer;
    private String type;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        preferences = getSharedPreferences("user",MODE_PRIVATE);
        name = preferences.getString("name", "");
        email = preferences.getString("email", "");
        type = preferences.getString("type", "");
        image = preferences.getString("image", "");

//        if (type.equals("trainer")){
//            drawer.setVisibility(View.GONE);
//        }


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


        Toolbar toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout_user);
        NavigationView navigationView = findViewById(R.id.nav_view_user);
        View header = navigationView.getHeaderView(0);
        TextView name_txt = header.findViewById(R.id.name_profile_system_user);
        ImageView imageView = header.findViewById(R.id.imageView_user);
        TextView email_txt = header.findViewById(R.id.mail_profile_system_user);
        ImageView imageView1 = header.findViewById(R.id.img_share);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.compubase.sportive");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }
        });

        name_txt.setText(name);
        email_txt.setText(email);
        Glide.with(this).load(image).placeholder(R.drawable.bg_sportive).into(imageView);

        mView = navigationView.getHeaderView(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                HomeActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        imageView =  drawer.findViewById(R.id.imageView_user);
//        user_name =(TextView) drawer.findViewById(R.id.name_profile_system_user);
//        user_mail =(TextView) drawer.findViewById(R.id.mail_profile_system_user);
//
//        user_name.setText(name);
//        user_mail.setText(email);

        navigationView.setNavigationItemSelectedListener(HomeActivity.this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_user);
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

        if (id == R.id.profile) {
            // Handle the camera action

            startActivity(new Intent(HomeActivity.this,UserProfileActivity.class));

        } else if (id == R.id.logout) {

            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

            preferences = getSharedPreferences("user", MODE_PRIVATE);

            editor.putBoolean("login", false);

            editor.apply();
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
//            alartExit();

        } else if (id == R.id.contact_us) {

        }else if (id == R.id.myCenters){
            startActivity(new Intent(HomeActivity.this,CentersActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout_user);
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
