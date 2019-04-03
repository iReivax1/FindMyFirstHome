package com.example.findmyfirsthome.Presenter;

import android.app.Application;

import com.example.findmyfirsthome.Model.DataGovAPI;
import com.example.findmyfirsthome.View.SplashScreenUI;

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
