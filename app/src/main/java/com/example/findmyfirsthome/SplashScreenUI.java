package com.example.findmyfirsthome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.findmyfirsthome.Boundary.DevelopmentDetailUI;

public class SplashScreenUI extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    public void nextWindow(View view)
    {
        Intent intent = new Intent(this, DevelopmentDetailUI.class);
        intent.putExtra("estateName", "Test @ Sembawang");
        startActivity(intent);
    }
}
