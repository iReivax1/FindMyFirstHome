package com.example.findmyfirsthome.Boundary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.findmyfirsthome.Controller.DownloadFileManager;
import com.example.findmyfirsthome.Controller.StartupControl;
import com.example.findmyfirsthome.R;

public class SplashScreenUI extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //Initialize the back-end controller classes, i.e database, hdbmanager, and do all the get data
        StartupControl sc1 = new StartupControl();
        sc1.runIt();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer
            */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start profileUI activity
                Intent i = new Intent( SplashScreenUI.this, DataGovAPI.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);;
    }
}
