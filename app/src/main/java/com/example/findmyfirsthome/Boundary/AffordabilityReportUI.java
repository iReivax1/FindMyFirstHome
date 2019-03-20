package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.findmyfirsthome.R;

public class AffordabilityReportUI extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //to get data passed from previous intent/activity/boundary
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        //to be used when combine work with the rest
        final String flatType = extras.getString("FlatType");
    }
}
