package com.example.findmyfirsthome.Controller;

import com.example.findmyfirsthome.Entity.AffordabilityReport;
import com.example.findmyfirsthome.Entity.CalculatedProfile;

import java.util.ArrayList;
import java.util.HashMap;
import static java.lang.Math.*;

public class AffordabilityReportController {
    DatabaseController dbc = new DatabaseController();
    AffordabilityReport report;
    ArrayList<String> hdbFlatInfo;
    String hdbName, flatType;

    public AffordabilityReportController(String hdbName, String flatType){
        this.report = dbc.getAffordabilityReport();
        this.hdbFlatInfo = dbc.getHdbFlatInfo(hdbName, flatType);
    }

    public ArrayList<String> getFixedInfo(){
        CalculatedProfile cp = report.getCalcProfile();
        ArrayList<String> temp = new ArrayList<>();
        if (cp == null)
            return temp;

        //add calculated profile into list and pass to view
        String maxMortgage = String.valueOf(cp.getMaxMortgageAmt());
        temp.add(maxMortgage);
        String maxTenure = String.valueOf(cp.getMaxMortgageTerm());
        temp.add(maxTenure);
        String maxPropertyPrice = String.valueOf(cp.getMaxPurchasePrice());
        temp.add(maxPropertyPrice);
        String ahg = String.valueOf(cp.getAHG());
        temp.add(ahg);
        String shg = String.valueOf(cp.getSHG());
        temp.add(shg);
        return temp;
    }

    public ArrayList<String> getHDBDependentInfo(String hdbName, String flatType){
        final double monthIR = 0.026/12;
        CalculatedProfile cp = report.getCalcProfile();
        ArrayList<String> temp = new ArrayList<>();
        if (hdbFlatInfo.size() == 0 || cp == null)
            return temp;

        double term = cp.getMaxMortgageTerm();
        //prior calculations of required values in double
        double price = hdbFlatInfo.get(0);

        double downpay = 0.1*price;
        double loan = 0.9*price;
        double repay = price*0.9/(pow((1+monthIR),(term*12)) - 1)/monthIR*(pow((1+monthIR),(term*12)));

        //add values in Strings to list and pass to view
        String propertyPrice = String.valueOf(price);
        temp.add(propertyPrice);
        String downpaymentReq = String.valueOf(downpay);
        temp.add(downpaymentReq);
        String loanReq = String.valueOf(loan);
        temp.add(loanReq);
        String repaymentSum = String.valueOf(repay);
        temp.add(repaymentSum);
        return temp;
    }
}
