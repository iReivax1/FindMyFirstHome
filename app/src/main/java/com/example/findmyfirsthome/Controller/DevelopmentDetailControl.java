package com.example.findmyfirsthome.Controller;

import android.app.Application;
import android.content.Context;

import com.example.findmyfirsthome.Entity.HDBDevelopment;

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

    //get description from entity
    public String getDevelopmentDescription()
    {
        return this.hdbd.getDevelopmentDescription();
    }

    //get image from entity
    /*public String getDevelopmentImage()
    {
        return this.hdbd.getDevelopmentImage();
    }*/

    //return ArrayList of FlatType contents to the boundary
    public ArrayList<HashMap<String, Object>> getTableContent()
    {
        //get from development entity the ArrayList of FlatType details of that estate/development
        flatTypeListDetails = hdbd.getHDBFlatTypeDetailsList();

         return hdbd.getHDBFlatTypeDetailsList();
    }
}
