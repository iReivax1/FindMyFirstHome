package com.example.findmyfirsthome.Presenter;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.findmyfirsthome.Model.DataGovAPI;
import com.example.findmyfirsthome.View.SplashScreenUI;

public class HDBSplashscreenController extends Application {

    private HDBDetailsManager manager;
    private DataGovAPI dg;
    @Override
    public void onCreate() {
        SplashScreenUI next = new SplashScreenUI();
        super.onCreate();

        //check have network connectivity before initializing and allowing scrapping of data and getting coordinates
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting())
        {
            manager = new HDBDetailsManager(this);
            dg = new DataGovAPI(this);

            manager.execute();
            dg.execute();
        }
    }
}
