package com.example.findmyfirsthome.Presenter;

import com.example.findmyfirsthome.Model.CalculatedProfile;
import com.example.findmyfirsthome.Model.HDBDevelopment;
import com.example.findmyfirsthome.Model.MapData;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AffordabilityReportControllerTest {
    CalculatedProfile cp = new CalculatedProfile(40000,40000,225825,630,20,225825,20000);
    ArrayList<HashMap<String, Object>> flatTypeList = new ArrayList<>();
    HashMap<String, Object> flatType = new HashMap<>();
    HashMap<String, Object> flatType2 = new HashMap<>();
    LatLng coords = new LatLng(0.0,0.0);
    ArrayList<MapData> amenities;
    ArrayList<String> ImgURL = new ArrayList<>();
    MapData amenitiy = new MapData("Market", "ABC", coords);
    HDBDevelopment hdbd = new HDBDevelopment(flatTypeList, "Kallang Breeze", "Some Desc", false, coords, amenities, "URL" );
    ArrayList<HashMap<String, Object>> HDBFlatTypeDetailsList = new ArrayList<>();

    @Test
    public void getFixedInfo() {
        flatType.put("flatType", "3-room");
        flatType.put("price", 374000);
        flatType.put("affordability", false);
        flatType2.put("flatType", "4-room");
        flatType2.put("price", 352300);
        flatType2.put("affordability", false);
        flatTypeList.add(flatType);
        flatTypeList.add(flatType2);
        ImgURL.add("URL");
        HDBFlatTypeDetailsList.add(flatType);
        HDBFlatTypeDetailsList.add(flatType2);
        ArrayList<String> temp = new ArrayList<>();
        if (cp == null)
            print(temp);

        //add calculated profile into list and pass to view
        String maxMortgage = String.format ("%,.2f", cp.getMaxMortgage());
        temp.add(maxMortgage);
        String maxTenure = String.valueOf(cp.getMaxMortgagePeriod());
        temp.add(maxTenure);
        String maxPropertyPrice = String.format ("%,.2f", cp.getMaxPropertyPrice());
        temp.add(maxPropertyPrice);
        String ahg = String.format ("%,.2f", cp.getAHG());
        temp.add(ahg);
        String shg = String.format ("%,.2f", cp.getSHG());
        temp.add(shg);

        print(temp);
    }

    @Test
    public void getHDBDependentInfo() {
        final double monthIR = 0.026/12;
        HDBFlatTypeDetailsList = hdbd.getHDBFlatTypeDetailsList();
        ArrayList<String> temp = new ArrayList<>();

        if (HDBFlatTypeDetailsList.size() == 0 || cp == null)
            print(temp);

        //loop through all the list of HDB Flats Types
        for(HashMap<String, Object> HDBFlatTypeDetails : HDBFlatTypeDetailsList) {
            //check if data is null and flat type must be the same
            if(HDBFlatTypeDetails.get("flatType") != null && ((String) HDBFlatTypeDetails.get("flatType")).equals(this.flatType)) {
                double term = cp.getMaxMortgagePeriod();
                //prior calculations of required values in double
                double price = (Double) HDBFlatTypeDetails.get("price");
                double downpay = 0.1 * price;
                double loan = 0.9 * price;
                double repay = loan*(monthIR * (Math.pow((1+monthIR),(term*12))))/(Math.pow((1+monthIR),(term*12)) -1);


                //add values in Strings to list and pass to view
                String propertyPrice = String.format ("%,.2f", price);
                temp.add(propertyPrice);
                String downpaymentReq = String.format ("%,.2f", downpay);
                temp.add(downpaymentReq);
                String loanReq = String.format ("%,.2f", loan);
                temp.add(loanReq);
                String repaymentSum = String.format ("%,.2f", repay);
                temp.add(repaymentSum);

                //get out of FOR LOOP since got the data we needed
                break;
            }
        }
        print(temp);
    }

    public void print(ArrayList<String> printList){
        for(String i : printList){
            System.out.print(i);
        }
    }
}