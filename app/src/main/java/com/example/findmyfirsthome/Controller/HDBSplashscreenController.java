package com.example.findmyfirsthome.Controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

public class HDBSplashscreenController extends AppCompatActivity {
    HDBDetailsManager manager = new HDBDetailsManager();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager.execute();
    }

    public void writeHDBD(String HDBDevelopmentNames, HashMap<String, Object> ListFlatType, String descriptionText, String ImgURL){
        if(manager.getStatus() == AsyncTask.Status.FINISHED){
            DatabaseController db = new DatabaseController(this.getApplicationContext());
            db.writeHDBData(HDBDevelopmentNames,ListFlatType,descriptionText,ImgURL);
            System.out.println("SplashScreenController write HDB, Success in writing "+HDBDevelopmentNames);
        }
        else{
            System.out.println("SplashScreenController write HDB, Fail to write "+HDBDevelopmentNames);
        }

    }

    public void writeHDBGrantData(String incomeReq, HashMap<String, Double> grant){
        DatabaseController db = new DatabaseController(this.getApplicationContext());
        db.writeHDBGrantData(incomeReq, grant);
        System.out.println("SplashScreenController write HDB Grant, Success in writing "+incomeReq);
    }
}
