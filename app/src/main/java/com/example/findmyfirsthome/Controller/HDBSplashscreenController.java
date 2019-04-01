package com.example.findmyfirsthome.Controller;

import android.app.Application;
import android.os.AsyncTask;

import com.example.findmyfirsthome.Boundary.DataGovAPI;

public class HDBSplashscreenController extends Application {

    private HDBDetailsManager manager = new HDBDetailsManager(this);
    private DataGovAPI dg = new DataGovAPI(this);
    @Override
    public void onCreate() {
        super.onCreate();
        manager.execute();
        dg.execute();
    }

}
