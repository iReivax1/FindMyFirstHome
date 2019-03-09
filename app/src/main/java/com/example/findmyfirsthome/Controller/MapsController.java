package com.example.findmyfirsthome.Controller;

import com.example.findmyfirsthome.Boundary.MapAPI;
import com.example.findmyfirsthome.Entity.HDBDevelopment;


import android.app.Application;
import android.content.Context;
import java.util.ArrayList;



public class MapsController extends Application {

    private static Application Application;

    public static Application getApplication() {
        return Application;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Application = this;
    }

    //create controller
    DatabaseController db = new DatabaseController(getContext());



    ArrayList<HDBDevelopment> HDBDevelopmentlist;

    public void getHDBList(){
        this.HDBDevelopmentlist = db.getHDBList();
    }

    public void writeHDBCoordinates(){


    }


}
