package com.example.findmyfirsthome.Interface;

import com.example.findmyfirsthome.Model.CalculatedProfile;
import com.example.findmyfirsthome.Model.HDBDevelopment;
import com.example.findmyfirsthome.Model.MapData;
import com.example.findmyfirsthome.Model.UserData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public interface DataAccessInterfaceClass {

    /////////////////////////////////////////////////////write methods/////////////////////////////////////////////
    boolean writeHDBData(String name, String descriptionText, String ImgUrl, Boolean affordable, Double lat, Double lng);
    void writeHDBFlatTypeData(String name, HashMap<String, Object> ListFlatType);
    boolean writeAmenitiesData(LinkedHashMap<String, Object> infoList);
    boolean writeHDBGrantData(String incomeReq, HashMap<String, Double> grantList);
    boolean writeUserData(UserData ud);
    boolean writeTax(LinkedHashMap<String, String> info);
    boolean writeCalculatedProfile(CalculatedProfile cp);

    ///////////////////////////////////////Read methods/////////////////////////////////////////////////////
    CalculatedProfile readCalculatedProfile();
    ArrayList<HDBDevelopment> readHDBData();
    HDBDevelopment readHDBData(String developmentName);
    ArrayList<MapData> readMapData(String name);
    ArrayList<HashMap<String, Object>> readHDBFlatType(String name);
    LatLng readHDBDevelopmentCoordinates(String name);
    HashMap<String, Double> readHDBGrantData(String incomeReq);
    UserData readUserData();
    ArrayList<Double> readMembersSalaryList();

    //////////////////////////////////////Delete methods////////////////////////////////////////////////////
    void deleteHDBData();

    void close();
}
