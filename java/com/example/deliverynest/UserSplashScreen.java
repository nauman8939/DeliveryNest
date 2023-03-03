package com.example.deliverynest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class UserSplashScreen extends AppCompatActivity {
    private SessionManager sessionManager;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sessionManager = new SessionManager(this);
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        sessionManager.isLoggedIn();

        // Wait for 3 seconds before launching the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (sessionManager.isLoggedIn()) {
                    // User is logged in
                    intent = new Intent(UserSplashScreen.this, UserDashboard.class);
                } else if (prefs.getBoolean("first_time", true)) {
                    // First time user
                    intent = new Intent(UserSplashScreen.this, OnBoardingScreen.class);
                    prefs.edit().putBoolean("first_time", false).apply();
                } else {
                    // User is not logged in
                    intent = new Intent(UserSplashScreen.this, SecondSplashScreen.class);
                }


                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
