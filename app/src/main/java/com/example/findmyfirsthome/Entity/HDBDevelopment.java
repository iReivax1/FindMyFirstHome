package com.example.findmyfirsthome.Entity;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

public class HDBDevelopment {
    private ArrayList<HDBFlatType> hdbFlatTypeList;
    private ArrayList<HashMap<String, Object>> hdbFlatTypeDetailsList;
    private String developmentName;
    private String developmentDescription;
    private boolean affordable;
    private LatLng coordinates;
    private ArrayList<MapData> amenities;

    private ArrayList developmentDetails ;


    public HDBDevelopment(ArrayList<HashMap<String, Object>> flatTypeList, String developmentName, String developmentDescription,
                          boolean affordable, LatLng coordinates, ArrayList<MapData> amenities)
    {
        //set data
        setHDBFlatTypeList(flatTypeList);
        this.developmentName = developmentName;
        this.developmentDescription = developmentDescription;
        this.affordable = affordable;
        this.coordinates = coordinates;
        this.amenities = amenities;
    }

    //hdbFlatTypeDetailsList GET
    public ArrayList<HashMap<String, Object>> getHDBFlatTypeDetailsList()
    {
        //initialize
        this.hdbFlatTypeDetailsList = new ArrayList<HashMap<String, Object>>();

        //get HashMap of data of each HDBFlatType
        for(HDBFlatType hdbFlatType : hdbFlatTypeList)
        {
            this.hdbFlatTypeDetailsList.add(hdbFlatType.getFlatTypeDetails());
        }

        return this.hdbFlatTypeDetailsList;
    }

    //hdbFlatTypeList CREATE FlatType
    public void setHDBFlatTypeList(ArrayList<HashMap<String, Object>> flatTypeList)
    {
        for(HashMap<String, Object>  hdbFlatTypeDetails :  flatTypeList)
        {
            //create HDBFlatType and add into the ArrayList
            this.hdbFlatTypeList.add(new HDBFlatType(hdbFlatTypeDetails));
        }
    }

    //developmentName GET SET
    public String getDevelopmentName()
    {
        return this.developmentName;
    }

    public void setDevelopmentName(String developmentName)
    {
       this.developmentName = developmentName;
    }

    //developmentDescription GET SET
    public String getDevelopmentDescription()
    {
        return this.developmentDescription;
    }

    public void setDevelopmentDescription(String developmentDescription)
    {
        this.developmentDescription = developmentDescription;
    }

    //affordable GET SET
    public Boolean getAffordable()
    {
        return this.affordable;
    }

    public void setAffordable(Boolean affordable)
    {
        this.affordable = affordable;
    }

    //coordinates GET SET
    public LatLng getCoordinates()
    {
        return this.coordinates;
    }

    public void setCoordinates(LatLng coordinates)
    {
        this.coordinates = coordinates;
    }

    //amenities GET SET
    public ArrayList<MapData> getAmenities()
    {
        return this.amenities;
    }

    public void setAmenities(ArrayList<MapData> amenities)
    {
        this.amenities = amenities;
    }

    public void setHdbFlatTypeList(ArrayList<HDBFlatType> hdbFlatTypeList) {
        this.hdbFlatTypeList = hdbFlatTypeList;
    }

    public ArrayList<HDBFlatType> getHdbFlatTypeList() {
        return hdbFlatTypeList;
    }

    public ArrayList getDevelopmentDetails()
    {
        //initialize
        this.developmentDetails = new ArrayList();

        //put them in ArrayList as ArrayList object does not inherit from Object class so cannot use HashMap
        //added into ArrayList in order like the order of the attributes in this class
        this.developmentDetails.add(getHDBFlatTypeDetailsList());
        this.developmentDetails.add(this.developmentName);
        this.developmentDetails.add(developmentDescription);
        this.developmentDetails.add(this.affordable);
        this.developmentDetails.add(this.coordinates);
        this.developmentDetails.add(this.amenities);

        return developmentDetails;
    }

}


