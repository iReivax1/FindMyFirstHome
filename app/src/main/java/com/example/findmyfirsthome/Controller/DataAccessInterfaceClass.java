package com.example.findmyfirsthome.Controller;

import com.example.findmyfirsthome.Entity.CalculatedProfile;
import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.Entity.MapData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

public interface DataAccessInterfaceClass {

    /////////////////////////////////////////////////////write/////////////////////////////////////////////////////////////////
    void writeHDBFlatTypeData(String name, HashMap<String, Object> ListFlatType);

    boolean writeHDBData(String name, String descriptionText, String ImgUrl);

    boolean writeHDBGrantData(String incomeReq, HashMap<String, Double> grantList);

    boolean writeAmenitiesData( HashMap<String, Object> infoList);

    boolean writeCalculatedProfile(CalculatedProfile cp);

    //boolean writeAmenitiesData(String name);
    /////////////////////////////////////////////////////read/////////////////////////////////////////////////////////////////
    CalculatedProfile readCalculatedProfile();

    ArrayList<HDBDevelopment> readHDBData();

    HDBDevelopment readHDBData(String developmentName);

    ArrayList<MapData> readMapData(String name);

    ArrayList<HashMap<String, Object>> readHDBFlatType(String name);

    LatLng readHDBDevelopmentCoordinates(String name);


}
