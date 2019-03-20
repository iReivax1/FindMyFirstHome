package com.example.findmyfirsthome.Controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class HDBSplashscreenController extends AppCompatActivity {
    HDBDetailsManager manager = new HDBDetailsManager();
    @Override
    public void onCreate(Bundle savedInstanceS
        super.onCreate(savedInstanceState);
        manager.execute();
    }

    public void writeHDBD(String HDBDevelopmentNames, HashMap<String, Object> ListFlatType, String descriptionText, String ImgURL){
        if(manager.getStatus() == AsyncTask.Status.FINISHED){
            DatabaseController db = new DatabaseController(this.getApplicationContext());
            db.writeHDBData(HDBDevelopmentNames,ListFlatType,descriptionText,ImgURL);
        }

    }

    public void writeHDBGrantData(String incomeReq, HashMap<String, Double> grant){
        DatabaseController db = new DatabaseController(this.getApplicationContext());
        db.writeHDBGrantData(incomeReq, grant);
    }
}
