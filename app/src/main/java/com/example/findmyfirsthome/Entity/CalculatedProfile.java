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

    private HashMap calProfileDetails;

    //AHG get set
    public double getAHG()
    {
        return this.AHG;
    }

    public void setAHG(double AHG)
    {
        this.AHG = AHG;
    }

    //SHG get set
    public double getSHG()
    {
        return this.SHG;
    }

    public void setSHG(double SHG)
    {
        this.SHG = SHG;
    }

    //maxMortgageAmt get set
    public double getMaxMortgageAmt()
    {
        return this.maxMortgageAmt;
    }

    public void setMaxMortgageAmt(double maxMortgageAmt)
    {
        this.maxMortgageAmt = maxMortgageAmt;
    }

    //maxPurchasePrice get set
    public double getMaxPurchasePrice()
    {
        return this.maxPurchasePrice;
    }

    public void setMaxPurchasePricet(double maxPurchasePrice)
    {
        this.maxPurchasePrice = maxPurchasePrice;
    }

    //maxPurchasePrice get set
    public int getMaxMortgageTerm()
    {
        return this.maxMortgageTerm;
    }

    public void setMaxMortgageTerm(int maxMortgageTerm)
    {
        this.maxMortgageTerm = maxMortgageTerm;
    }

    public HashMap getCalProfileDetails()
    {
        calProfileDetails = new HashMap();

        return calProfileDetails;
    }
}
