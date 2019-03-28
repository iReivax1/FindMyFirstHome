package com.example.findmyfirsthome.Controller;

import android.app.Application;
import android.content.Context;

import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.Entity.MapData;
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
        databaseController = DatabaseController.getInstance(context);

        //get data from Database Controller as entity object
        hdbd = databaseController.readHDBData(estateName);
    }

    //get list of amenities details in arraylist
    public ArrayList<ArrayList> getAmenitiesDetailsList() { return this.hdbd.getAmenititesDetailsList(); }
    //FOR DEBUGGING WITH FAKE INSERTED VALUES
    /*public ArrayList<ArrayList> getAmenitiesDetailsList()
    {
        ArrayList<ArrayList> amenitiesDetailList = new ArrayList<>();
        ArrayList amenitiesDetail = new ArrayList<>();

        amenitiesDetail.add("6th Avenue");
        amenitiesDetail.add("MRT");
        amenitiesDetail.add(new LatLng(1.330556, 103.797233));
        amenitiesDetailList.add(amenitiesDetail);

        amenitiesDetail = new ArrayList<>();
        amenitiesDetail.add("BT Road");
        amenitiesDetail.add("Roat");
        amenitiesDetail.add(new LatLng(1.330915, 103.797271));
        amenitiesDetailList.add(amenitiesDetail);

        return amenitiesDetailList;
    }*/

    //get description from entity
    public String getDevelopmentDescription() { return this.hdbd.getDevelopmentDescription(); }
    //DEBUGGING WITH FAKE INSERTED VALUES
    //public String getDevelopmentDescription() { return "asddf"; }

    public LatLng getDevelopmentLocation() { return this.hdbd.getCoordinates(); }
    //DEBUGGING WITH FAKE INSERTED VALUES
    //public LatLng getDevelopmentLocation() { return new LatLng(1.331183, 103.798311); }

    //get image from entity
    public String getDevelopmentImage() { return this.hdbd.getImgUrl(); }
    //DEBUGGING WITH FAKE INSERTED VALUES
    //public String getDevelopmentImage() { return "http://esales.hdb.gov.sg/bp25/launch/19feb/bto/19FEBBTOJW_images_6280/$file/jw_N2C20_view1.png"; }

    public String getDevelopmentName() { return this.hdbd.getDevelopmentName(); }
    //DEBUGGING WITH FAKE INSERTED VALUES
    //public String getDevelopmentName() { return "Eton Pre-School"; }

    //return ArrayList of FlatType contents to the boundary
    public ArrayList<HashMap<String, Object>> getHDBFlatTypeDetailsList()
    {
        //get from development entity the ArrayList of FlatType details of that estate/development
        flatTypeListDetails = hdbd.getHDBFlatTypeDetailsList();

         return hdbd.getHDBFlatTypeDetailsList();
    }
    //DEBUGGING WITH FAKE INSERTED VALUES
    /*public ArrayList<HashMap<String, Object>> getTableContent()
    {
        ArrayList<HashMap<String, Object>> tableContent = new ArrayList<>();
        HashMap<String, Object> flatTypeDetails = new HashMap<>();

        flatTypeDetails.put("price", 300000.0);
        flatTypeDetails.put("flatType", "3-Room");
        flatTypeDetails.put("affordability", true);
        tableContent.add(flatTypeDetails);

        flatTypeDetails = new HashMap<>();
        flatTypeDetails.put("price", 400000.0);
        flatTypeDetails.put("flatType", "4-Room");
        flatTypeDetails.put("affordability", false);
        tableContent.add(flatTypeDetails);

        return tableContent;
    }*/
}
