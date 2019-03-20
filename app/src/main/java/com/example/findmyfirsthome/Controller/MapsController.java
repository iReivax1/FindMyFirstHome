package com.example.findmyfirsthome.Controller;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;


public class MapsController extends AppCompatActivity {
    //object and var init;
    //create DAO;
    DatabaseController db = new DatabaseController(getContext());
    HashMap<String, LatLng> HDBDevelopmentlist;
    public Context getContext() {
        return this.getBaseContext();
    }

//    On create this controller will
//    1.  Tell MapsAPI to load the coordinates of the HDBDevelopments that have been scraped from HDBDetailsManager;
//    2.  MapsAPI Put all data to DB;
//    3.  DBC will then use this data from 2 to create the object;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    //this list of hdB development is not completed, lacking all the map details. Need to iterate the list and add them. FUCK LOAD WORK TO DO

    public HashMap<String, LatLng> getHDBListCoord(){

        ArrayList<HDBDevelopment> HDBDevelopmentlist = db.readHDBData();
        HashMap<String, LatLng> HDBCoord = new HashMap<String, LatLng>();
        LatLng coordinates;
        String HDBDevelopmentName;
        for(HDBDevelopment HDBD : HDBDevelopmentlist){
            coordinates = db.readHDBDevelopmentCoordinates(HDBD.getDevelopmentName());
            HDBDevelopmentName = HDBD.getDevelopmentName();
            HDBCoord.put(HDBDevelopmentName, coordinates);
        }
        return HDBCoord;
    }


    //MapController to parse url and give to mapAPI



}