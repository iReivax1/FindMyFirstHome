package com.example.findmyfirsthome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.findmyfirsthome.Boundary.MapAPI;
import com.example.findmyfirsthome.Boundary.MapsTest;

import java.util.Map;

public class SplashScreenUI extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        startMaps();
    }

    protected void startMaps(){
        Intent intent = new Intent(this, MapsTest.class);
        startService(intent);
    }
}
