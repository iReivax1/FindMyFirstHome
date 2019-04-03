package com.example.findmyfirsthome.Presenter;

import com.example.findmyfirsthome.Interface.DataAccessInterfaceClass;
import com.example.findmyfirsthome.Model.CalculatedProfile;
import com.example.findmyfirsthome.Model.HDBDevelopment;
import com.example.findmyfirsthome.Model.HDBFlatType;
import com.example.findmyfirsthome.Model.MapData;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CalculateProfileControlTest {

    ArrayList<HashMap<String, Object>> flatTypeList = new ArrayList<>();
    HashMap<String, Object> flatType = new HashMap<>();
    HashMap<String, Object> flatType2 = new HashMap<>();
    ArrayList<HDBDevelopment> hdbDevelopments = new ArrayList<>();
    ArrayList<MapData> amenities;
    LatLng coords = new LatLng(0.0,0.0);
    MapData amenitiy = new MapData("Market", "ABC", coords);
    CalculatedProfile cp = new CalculatedProfile(40000,40000,225825,630,20,225825,20000);
    HDBDevelopment hdb = new HDBDevelopment(flatTypeList, "Kallang Breeze", "Some Desc", false, coords, amenities, "URL" );

    @Test
    public void setAffordability() {
        flatType.put("flatType", "3-room");
        flatType.put("price", 374000);
        flatType.put("affordability", false);
        flatType2.put("flatType", "4-room");
        flatType2.put("price", 352300);
        flatType2.put("affordability", false);
        flatTypeList.add(flatType);
        flatTypeList.add(flatType2);
        hdbDevelopments.add(hdb);
        for(int i=0;i< hdbDevelopments.size();i++){
            ArrayList<HashMap<String, Object>> readHDBFlatType = hdbDevelopments.get(i).getHDBFlatTypeDetailsList(); //get flat types of each development
            int numOfTrue = 0;
            for(HashMap<String, Object> j : readHDBFlatType){
                for(String k : j.keySet())
                {
                    if(k.contains("price")){
                        double price = (double) j.get(k);
                        if(price<cp.getMaxPropertyPrice()){ //set affordability to true if the property is < maximum property price allowed with the loan
                            j.put("affordability", true);
                            numOfTrue++;
                        }
                        else {
                            j.put("affordability", false);
                        }
                    }
                }

            }
            //true if at least one affordable flat type then set hdb development to true too
            if(numOfTrue>0) System.out.print("False");
            else System.out.print("True");
            for(int m=0;m<readHDBFlatType.size();m++) {
                System.out.print("HDBFlat");
            }
        }
    }
    public void execute()
    {
        setAffordability();
    }

}