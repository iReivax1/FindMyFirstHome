package com.example.findmyfirsthome.Controller;

import android.app.Application;
import android.content.Context;

import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

public class DevelopmentDetailControl {
    private DatabaseController databaseController;
    private HDBDevelopment hdbd;
    private Context context;
    private ArrayList<HashMap<String, Object>> flatTypeListDetails;

    public DevelopmentDetailControl(String estateName, Context context)
    {
        this.context = context;
        databaseController = new DatabaseController(context);

        //get data from Database Controller as entity object
        hdbd = databaseController.readHDBData(estateName);
    }

    //get list of amenities details in arraylist
    public ArrayList<ArrayList> getAmenitiesDetailsList() { return this.hdbd.getAmenititesDetailsList(); }

    //get description from entity
    public String getDevelopmentDescription()
    {
        return this.hdbd.getDevelopmentDescription();
    }

    public LatLng getDevelopmentLocation() { return this.hdbd.getCoordinates(); }

    //get image from entity
    /*public String getDevelopmentImage()
    {
        return this.hdbd.getDevelopmentImage();
    }*/

    public String getDevelopmentName() { return this.hdbd.getDevelopmentName(); }

    //return ArrayList of FlatType contents to the boundary
    public ArrayList<HashMap<String, Object>> getTableContent()
    {
        //get from development entity the ArrayList of FlatType details of that estate/development
        flatTypeListDetails = hdbd.getHDBFlatTypeDetailsList();

         return hdbd.getHDBFlatTypeDetailsList();
    }
}
