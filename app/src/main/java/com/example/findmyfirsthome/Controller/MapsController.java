package com.example.findmyfirsthome.Controller;

import com.example.findmyfirsthome.Boundary.MapAPI;
import com.example.findmyfirsthome.Entity.HDBDevelopment;


import android.app.Application;
import android.content.Context;
import java.util.ArrayList;



public class MapsController extends Application {



    DatabaseController db = new DatabaseController(getContext());
    ArrayList<HDBDevelopment> HDBDevelopmentlist;
    public Context getContext() {
        return this.getBaseContext();
    }

    @Override
    public void onCreate() {

        super.onCreate();
        //create controller
         HDBDevelopmentlist = getHDBList();
    }



    public void getHDBList(){
        this.HDBDevelopmentlist = db.getHDBData();
    }

    public void writeHDBCoordinates(){


    }


}
