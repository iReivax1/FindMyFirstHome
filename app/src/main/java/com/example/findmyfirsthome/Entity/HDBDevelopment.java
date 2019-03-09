package com.example.findmyfirsthome.Entity;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HDBDevelopment {
    ArrayList<HDBFlatType> flatType;
    String developmentName;
    String developmentDescription;
    boolean affordable;
    ArrayList<LatLng> coordinates;
    ArrayList<MapData> amenities;

    //constructor
    public HDBDevelopment()
    {
        this.flatType = fT;
        this.developmentName = dN;
        this.developmentDescription = dD;
        this.affordable = affordable;
        this.coordinates = coord;
        this.amenities = amen;
    }

    public HDBDevelopment getHDB(){
        return this;
    }

}


