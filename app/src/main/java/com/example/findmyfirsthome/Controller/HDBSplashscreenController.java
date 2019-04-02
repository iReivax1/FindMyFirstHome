package com.example.findmyfirsthome.Controller;

import android.app.Application;

import com.example.findmyfirsthome.Boundary.DataGovAPI;
import com.example.findmyfirsthome.Boundary.SplashScreenUI;

public class HDBSplashscreenController extends Application {

    private HDBDetailsManager manager = new HDBDetailsManager(this);
    private DataGovAPI dg = new DataGovAPI(this);
    @Override
    public void onCreate() {
        SplashScreenUI next = new SplashScreenUI();
        super.onCreate();
        manager.execute();
        dg.execute();
    }

}
