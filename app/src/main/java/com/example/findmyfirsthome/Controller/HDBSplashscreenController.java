package com.example.findmyfirsthome.Controller;

import android.app.Application;

import com.example.findmyfirsthome.Boundary.DataGovAPI;

public class HDBSplashscreenController extends Application {

    HDBDetailsManager manager = new HDBDetailsManager(this);
    DataGovAPI dg = new DataGovAPI(this);
    @Override
    public void onCreate(){
        super.onCreate();
        manager.execute();
        dg.execute();
    }
}
