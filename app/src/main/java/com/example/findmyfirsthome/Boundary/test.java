package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.findmyfirsthome.Controller.DatabaseController;
import com.example.findmyfirsthome.R;

import java.util.ArrayList;
import java.util.HashMap;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        DatabaseController db = new DatabaseController(getApplicationContext());

        HashMap<String, Object> flatList = new HashMap<String, Object>();
        flatList.put("4room", 30000.00);
        flatList.put("5room", 40000.00);
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(flatList);
        db.writeHDBData("NTUCC", list, "hello");
    }

    public Context getContext(){
        return this.getApplicationContext();
    }




}
