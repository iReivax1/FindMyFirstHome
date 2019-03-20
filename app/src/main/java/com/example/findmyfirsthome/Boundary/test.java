package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.findmyfirsthome.Controller.DatabaseController;
import com.example.findmyfirsthome.Controller.HDBAdapter;
import com.example.findmyfirsthome.R;

import java.util.ArrayList;
import java.util.HashMap;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        HDBAdapter adapter = new HDBAdapter();
        adapter.onCreate(savedInstanceState);
    }

    public Context getContext(){
        return this.getApplicationContext();
    }


}
