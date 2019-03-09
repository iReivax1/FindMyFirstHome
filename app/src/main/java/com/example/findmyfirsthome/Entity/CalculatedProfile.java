/*
    1. Grants not completed.
    2. getCalProfileDetails() not completed.
*/
package com.example.findmyfirsthome.Entity;

import java.util.HashMap;

public class CalculatedProfile {
    //Get from database the exact grant amount
    private double AHG;
    private double SHG;

    private double maxMortgageAmt;
    private double maxPurchasePrice;
    private int maxMortgageTerm;

    private HashMap<String, Object> calProfileDetails;

    //AHG GET SET
    public double getAHG()
    {
        return this.AHG;
    }

    public void setAHG(double AHG)
    {
        this.AHG = AHG;
    }

    //SHG GET SET
    public double getSHG()
    {
        return this.SHG;
    }

    public void setSHG(double SHG)
    {
        this.SHG = SHG;
    }

    //maxMortgageAmt GET SET
    public double getMaxMortgageAmt()
    {
        return this.maxMortgageAmt;
    }

    public void setMaxMortgageAmt(double maxMortgageAmt)
    {
        this.maxMortgageAmt = maxMortgageAmt;
    }

    //maxPurchasePrice GET SET
    public double getMaxPurchasePrice()
    {
        return this.maxPurchasePrice;
    }

    public void setMaxPurchasePricet(double maxPurchasePrice)
    {
        this.maxPurchasePrice = maxPurchasePrice;
    }

    //maxPurchasePrice GET SET
    public int getMaxMortgageTerm()
    {
        return this.maxMortgageTerm;
    }

    public void setMaxMortgageTerm(int maxMortgageTerm)
    {
        this.maxMortgageTerm = maxMortgageTerm;
    }

    //Calculated Profile content in list GET SET
    public HashMap<String, Object> getCalProfileDetails()
    {
        this.calProfileDetails = new HashMap<String, Object>();

        //get all fields and return as HashMap
        //key of HashMap is the same as attribute
        this.calProfileDetails.put("AHG", this.AHG);
        this.calProfileDetails.put("SHG", this.SHG);
        this.calProfileDetails.put("maxMortgageAmt", this.maxMortgageAmt);
        this.calProfileDetails.put("maxPurchasePrice", this.maxPurchasePrice);
        this.calProfileDetails.put("maxMortgageTerm", this.maxMortgageTerm);

        return this.calProfileDetails;
    }

    public void setCalProfileDetails(HashMap<String, Object> calProfileDetails)
    {
        Object temp;

        //set all fields from HashMap
        temp = calProfileDetails.get("AHG");
        this.AHG = temp == null ? 0 : (Double)temp;
        temp = calProfileDetails.get("SHG");
        this.SHG = temp == null ? 0 : (Double)temp;
        temp = calProfileDetails.get("maxMortgageAmt");
        this.maxMortgageAmt = temp == null ? 0 : (Double)temp;
        temp = calProfileDetails.get("maxPurchasePrice");
        this.maxPurchasePrice = temp == null ? 0 : (Double)temp;
        temp = calProfileDetails.get("maxMortgageTerm");
        this.maxMortgageTerm = temp == null ? 0 : (Integer) temp;
    }
}
