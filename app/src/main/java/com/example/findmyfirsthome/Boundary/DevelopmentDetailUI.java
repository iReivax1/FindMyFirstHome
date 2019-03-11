package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.findmyfirsthome.R;

public class DevelopmentDetailUI extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.development_detail_ui);
    }

    //runs after onCreate()
    @Override
    protected void onStart()
    {
        super.onStart();

        //to get data passed from previous intent/activity/boundary
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        //to be used when combine work with the rest
        //String estateName = extras.getString("estateName");
        String estateName = "Test @ Sembawang"; //temporary used for now before combining with others


        //set content to show in the UI
        //set what estate name to display in the UI
        final TextView estateNameView = findViewById(R.id.text_estateName);
        estateNameView.setText(estateName);
    }
}
