package com.example.findmyfirsthome;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenUI extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }
}
