package com.example.findmyfirsthome.Controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class HDBAdapter extends AppCompatActivity {
    HDBDetailsManager manager = new HDBDetailsManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager.execute();
    }

    public void writeHDBD(String HDBDevelopmentNames, ArrayList<HashMap<String, Object>>ListFlatTypePrice, String descriptionText, String ImgURL){
       if(manager.getStatus() == AsyncTask.Status.FINISHED){
           DatabaseController db = new DatabaseController(this.getApplicationContext());
           db.writeHDBData(HDBDevelopmentNames, ListFlatTypePrice,descriptionText,ImgURL);
       }

    }

    public void writeHDBGrantData(String incomeReq, HashMap<String, Double> grant){
        DatabaseController db = new DatabaseController(this.getApplicationContext());
        db.writeHDBGrantData(incomeReq, grant);
    }
}
