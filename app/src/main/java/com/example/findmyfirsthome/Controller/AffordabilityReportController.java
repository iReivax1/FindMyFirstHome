package com.example.findmyfirsthome.Controller;

import android.content.Context;

import com.example.findmyfirsthome.Entity.CalculatedProfile;
import com.example.findmyfirsthome.Entity.HDBDevelopment;

import java.util.ArrayList;
import java.util.HashMap;

public class AffordabilityReportController {
    private DatabaseController dbc;
    private CalculatedProfile cp;
    private HDBDevelopment hdbd;
    private String hdbName, flatType;
    private ArrayList<HashMap<String, Object>> HDBFlatTypeDetailsList;

    public AffordabilityReportController(Context context, String hdbName, String flatType){
        dbc = DatabaseController.getInstance(context);

        this.cp = dbc.readCalculatedProfile();
        this.hdbd = dbc.readHDBData(hdbName);

        this.hdbName = hdbName;
        this.flatType = flatType;

    }

    public ArrayList<String> getFixedInfo(){
        ArrayList<String> temp = new ArrayList<>();
        if (cp == null)
            return temp;

        //add calculated profile into list and pass to view
        String maxMortgage = String.valueOf(cp.getMaxMortgage());
        temp.add(maxMortgage);
        String maxTenure = String.valueOf(cp.getMaxMortgagePeriod());
        temp.add(maxTenure);
        String maxPropertyPrice = String.valueOf(cp.getMaxPropertyPrice());
        temp.add(maxPropertyPrice);
        String ahg = String.valueOf(cp.getAHG());
        temp.add(ahg);
        String shg = String.valueOf(cp.getSHG());
        temp.add(shg);
        return temp;
    }

    public ArrayList<String> getHDBDependentInfo(){
        final double monthIR = 0.026/12;
        HDBFlatTypeDetailsList = hdbd.getHDBFlatTypeDetailsList();
        ArrayList<String> temp = new ArrayList<>();

        if (HDBFlatTypeDetailsList.size() == 0 || cp == null)
            return temp;

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
                String propertyPrice = String.valueOf(price);
                temp.add(propertyPrice);
                String downpaymentReq = String.valueOf(downpay);
                temp.add(downpaymentReq);
                String loanReq = String.valueOf(loan);
                temp.add(loanReq);
                String repaymentSum = String.valueOf(repay);
                temp.add(repaymentSum);

                //get out of FOR LOOP since got the data we needed
                break;
            }
        }
        return temp;
    }

}
