package com.example.findmyfirsthome.View;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashScreenUI extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //check for internet connection
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        //IF statement true if there is no internet connection
        if(activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
            //display popup to user to connect to the internet
            Toast toast = Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT);
            toast.show();

            //for user experience by closing the app (actually current activity) if there is no internet connection after 1 sec
            new Handler().postDelayed(new Runnable() {
                //run in another thread
                @Override
                public void run() {
                    finish();
                }
            }, 1000);

            //for user experience by closing the whole app (including Toast) 2 sec after closing the current activity
            //this is to make the Toast message last longer
            new Handler().postDelayed(new Runnable() {
                //run in another thread
                @Override
                public void run() {
                    System.exit(0);
                }
            }, 2000);
        }
        
        //Initialize the back-end controller classes, i.e database, hdbmanager, and do all the get data
        new Handler().postDelayed(new Runnable() {
            //run in another thread
            @Override
            public void run() {
                //This method will be executed once the timer is over
                //Start profileUI activity
                Intent i = new Intent(SplashScreenUI.this , ProfileUI.class);
                startActivity(i);
            }
        }, SPLASH_TIME_OUT);
    }
}

