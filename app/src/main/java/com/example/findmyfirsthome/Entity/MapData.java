package com.example.findmyfirsthome.Entity;



import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MapData {
    private String amenitiesName;
    private String amenityType;
    //TODO: get coordinates from mapAPI;
    private LatLng Coordinates;

    public static final int AMENITIESNAME = 0;
    public static final int AMENITYTYPE = 1;
    public static final int COORDINATES = 2;

    private ArrayList mapdataDetails;

    public MapData() {}

    public MapData(String amenitiesName, String amenityType, LatLng Coordinates)
    {
        this.amenitiesName = amenitiesName;
        this.amenityType = amenityType;
        this.Coordinates = Coordinates;
    }

    //amenitiesName GET SET
    public String getAmenitiesName()
    {
        return this.amenitiesName;
    }

    public void setAmenitiesName(String amenitiesName)
    {
        this.amenitiesName = amenitiesName;
    }

    //amenityType GET SET
    public String getAmenityType()
    {
        return this.amenityType;
    }

    public void setAmenityType(String amenityType)
    {
        this.amenityType = amenityType;
    }

    //Coordinates GET SET
    public LatLng getCoordinates()
    {
        return this.Coordinates;
    }

    public void setCoordinates(LatLng Coordinates)
    {
        this.Coordinates = Coordinates;
    }

    //mapdataDetails GET SET
    public ArrayList getMapdataDetails()
    {
        //initailize
        this.mapdataDetails = new ArrayList();

        //using ArrayList instead of HashMap as coordinates is ArrayList which cannot be converted into Object
        //add data of attributes into ArrayList in the order of attributes in this class
        this.mapdataDetails.add(this.amenitiesName);
        this.mapdataDetails.add(this.amenityType);
        this.mapdataDetails.add(this.Coordinates);

        return this.mapdataDetails;
    }

    public void addMapdataDetails(ArrayList mapdataDetails)
    {
        this.mapdataDetails = mapdataDetails;
    }

}
