package com.compubase.sportive.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.compubase.sportive.R;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private boolean login;
    private boolean login_user;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        login_user = preferences.getBoolean("login", true);
        type = preferences.getString("type", "");

        //good

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if (login_user){
                    if (type.equals("center")){
                        Intent mainIntent = new Intent(SplashActivity.this,CenterHomeActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();
                    }else if (type.equals("user")){
                        Intent mainIntent = new Intent(SplashActivity.this,HomeActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();
                    }else if (type.equals("trainer")){
                        Intent mainIntent = new Intent(SplashActivity.this,TrainerHomeActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();
                    }else {
                        Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();
                    }

                }else {
                    Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }

            }
        }, 2000);

        ImageView imageView = findViewById(R.id.img_splash);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        imageView.setAnimation(animation);


    }
}
