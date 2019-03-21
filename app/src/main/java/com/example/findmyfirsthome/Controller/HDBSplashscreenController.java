package com.example.findmyfirsthome.Controller;

import android.app.Application;

public class HDBSplashscreenController extends Application {

    HDBDetailsManager manager = new HDBDetailsManager(this);
    @Override
    public void onCreate(){
        super.onCreate();
        manager.execute();
    }
/*
    public void writeHDBD(String HDBDevelopmentNames, HashMap<String, Object> ListFlatType, String descriptionText, String ImgURL){
        if(manager.getStatus() == AsyncTask.Status.FINISHED){
            System.out.println("Reached database");
            DatabaseController db = new DatabaseController(this);
            db.writeHDBData(HDBDevelopmentNames,ListFlatType,descriptionText,ImgURL);
            System.out.println("SplashScreenController write HDB, Success in writing "+HDBDevelopmentNames);
        }
        else{
            System.out.println("SplashScreenController write HDB, Fail to write "+HDBDevelopmentNames);
        }

    }

    public void writeHDBGrantData(String incomeReq, HashMap<String, Double> grant){
        DatabaseController db = new DatabaseController(this);
        db.writeHDBGrantData(incomeReq, grant);
        System.out.println("SplashScreenController write HDB Grant, Success in writing "+incomeReq);
    }*/
}
