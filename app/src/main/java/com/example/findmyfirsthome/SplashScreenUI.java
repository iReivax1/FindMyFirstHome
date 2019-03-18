package com.example.findmyfirsthome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.findmyfirsthome.Boundary.DevelopmentDetailUI;

import com.example.findmyfirsthome.Boundary.DataGovAPI;
import com.example.findmyfirsthome.Boundary.MapAPI;


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
        startDataGovAPI();
    }

    private void startMaps(){
        Intent intent = new Intent(SplashScreenUI.this, MapAPI.class);
        startActivity(intent);
    }

    private void startDataGovAPI(){
        Intent intent = new Intent(SplashScreenUI.this, DataGovAPI.class);
        startActivity(intent);
    }

    public void nextWindow(View view)
    {
        Intent intent = new Intent(this, DevelopmentDetailUI.class);
        intent.putExtra("estateName", "Test @ Sembawang");
        startActivity(intent);
    }
}
