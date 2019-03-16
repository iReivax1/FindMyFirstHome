package com.example.findmyfirsthome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.findmyfirsthome.Boundary.MapAPI;
import com.example.findmyfirsthome.Boundary.MapsTest;



public class SplashScreenUI extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        try {
            synchronized (this) {
                wait(1000);
            }
            } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startMaps();
    }

    protected void startMaps(){
        Intent intent = new Intent(SplashScreenUI.this, MapAPI.class);
        startActivity(intent);
    }
}
