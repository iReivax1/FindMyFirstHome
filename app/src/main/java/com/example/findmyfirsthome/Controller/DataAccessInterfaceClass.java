package com.example.findmyfirsthome.Controller;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.Entity.MapData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

public interface DataAccessInterfaceClass {

    /////////////////////////////////////////////////////write/////////////////////////////////////////////////////////////////
    boolean writeHDBData(String HDBDevelopmentName, ArrayList<HashMap<String, Object>>ListFlatTypePrice, String descriptionText);

    boolean writeHDBFlatTypeData(String name, HashMap<String, Object> HMFlatType);

    boolean writeHDBGrantData(String incomeReq, HashMap<String, Double> grantList);

    //boolean writeAmenitiesData(String name);
    /////////////////////////////////////////////////////read/////////////////////////////////////////////////////////////////
    ArrayList<HDBDevelopment> readHDBData();

    HDBDevelopment readHDBData(String developmentName);

    ArrayList<MapData> readMapData(String name);

    ArrayList<HashMap<String, Object>> readHDBFlatType(String name);

    LatLng readHDBDevelopmentCoordinates(String name);

}
