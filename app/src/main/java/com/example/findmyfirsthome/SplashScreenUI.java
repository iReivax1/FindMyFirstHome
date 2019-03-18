package com.example.findmyfirsthome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.findmyfirsthome.Boundary.DevelopmentDetailUI;

import com.example.findmyfirsthome.Boundary.DataGovAPI;
import com.example.findmyfirsthome.Boundary.MapAPI;
import com.example.findmyfirsthome.Controller.HDBDetailsManager;


public class SplashScreenUI extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        startDetailsManager();
        try {
            synchronized (this) {
                wait(10000);
            }
            } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void startMaps(){
        Intent intent = new Intent(SplashScreenUI.this, MapAPI.class);
        startActivity(intent);
    }

    private void startDataGovAPI(){
        Intent intent = new Intent(SplashScreenUI.this, DataGovAPI.class);
        startActivity(intent);
    }
    private void startHDBDevelopmentUI(){
        Intent intent = new Intent(SplashScreenUI.this, DevelopmentDetailUI.class);
        startActivity(intent);
    }

    private void startDetailsManager(){
        HDBDetailsManager manager = new HDBDetailsManager();
    }

    public void nextWindow(View view)
    {

        Intent intent = new Intent(this, DevelopmentDetailUI.class);
        intent.putExtra("estateName", "Test @ Sembawang");
        startActivity(intent);
    }
}
