package com.example.findmyfirsthome.Controller;

import android.app.Application;

public class HDBSplashscreenController extends Application {

    HDBDetailsManager manager = new HDBDetailsManager(this);
    @Override
    public void onCreate(){
        super.onCreate();
        manager.execute();
    }
}
