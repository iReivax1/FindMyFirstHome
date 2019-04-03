package com.example.findmyfirsthome.Presenter;

import com.example.findmyfirsthome.Model.CalculatedProfile;
import com.example.findmyfirsthome.Model.HDBDevelopment;
import com.example.findmyfirsthome.Model.MapAPI;
import com.example.findmyfirsthome.Model.MapData;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;



public class HDBDetailsManagerTest {
    ArrayList<HashMap<String, Object>> flatTypeList = new ArrayList<>();
    HashMap<String, Object> flatType = new HashMap<>();
    HashMap<String, Object> flatType2 = new HashMap<>();
    ArrayList<HDBDevelopment> hdbDevelopments = new ArrayList<>();
    ArrayList<String> HDBDevelopmentNames = new ArrayList<>();
    ArrayList<MapData> amenities;
    LatLng coords = new LatLng(0.0,0.0);
    MapData amenitiy = new MapData("Market", "ABC", coords);
    CalculatedProfile cp = new CalculatedProfile(40000,40000,225825,630,20,225825,20000);
    HDBDevelopment hdb = new HDBDevelopment(flatTypeList, "Kallang Breeze", "Some Desc", false, coords, amenities, "URL" );
    ArrayList<String> descriptionText = new ArrayList<>();
    ArrayList<String> ImgURL = new ArrayList<>();


    public void putInput(){
        flatType.put("flatType", "3-room");
        flatType.put("price", 374000);
        flatType.put("affordability", false);
        flatType2.put("flatType", "4-room");
        flatType2.put("price", 352300);
        flatType2.put("affordability", false);
        amenities.add(amenitiy);
        flatTypeList.add(flatType);
        flatTypeList.add(flatType2);
        hdbDevelopments.add(hdb);
        HDBDevelopmentNames.add("Kallang Breeze");
        descriptionText.add("Desc");
        ImgURL.add("URL");
    }

    @Test
    public void adaptHDBD(ArrayList<String> HDBDevelopmentNames, ArrayList<HashMap<String, Object>> flatTypeList, ArrayList<String> descriptionText, ArrayList<String> ImgURL) {
        int index = 0;
        for (String name : HDBDevelopmentNames) {
            writeHDBDataPlusGetCoord(name, descriptionText.get(index), ImgURL.get(index), false);
            for(int i=0;i<flatTypeList.size();i++) {
                HashMap<String, Object> ftNew = new HashMap<String, Object>();
                for(String ft : flatTypeList.get(i).keySet()) {
                    ftNew.put(ft,flatTypeList.get(i).get(ft));
                }
                writeHDBFlatData(name, ftNew);
            }
            index++;
        }
    }

    public void execute(){
        adaptHDBD(HDBDevelopmentNames, flatTypeList, descriptionText, ImgURL);
    }

    public void writeHDBDataPlusGetCoord (String HDBDevelopmentNames, String descriptionText, String ImgURL, Boolean affordable){
       System.out.print("Geting data " + HDBDevelopmentNames + descriptionText + ImgURL + affordable);
    }
    public void writeHDBFlatData(String HDBDevelopmentNames, HashMap<String, Object> ListFlatType){
        System.out.print("writing in HDB Flat Data");
    }
}