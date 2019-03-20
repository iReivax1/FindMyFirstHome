package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.example.findmyfirsthome.Controller.HDBSplashscreenController;
import com.example.findmyfirsthome.R;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        HDBSplashscreenController SScontrol = new HDBSplashscreenController();
        SScontrol.executeScrapper();
    }

    public Context getContext(){
        return this.getApplicationContext();
    }


}
