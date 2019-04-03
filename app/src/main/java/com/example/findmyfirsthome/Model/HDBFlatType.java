package com.example.findmyfirsthome.Model;

import java.util.HashMap;

public class HDBFlatType {
    private double price;
    private String flatType; //number of rooms
    private boolean affordability;

    //
    private HashMap<String, Object> flatTypeDetails;

    //constructor
    public HDBFlatType(HashMap<String, Object> hdbFlatTypeDetails)
    {
        Object temp;

        //set all fields from HashMap
        temp = hdbFlatTypeDetails.get("price");
        this.price = temp == null ? 0 : (Double)temp;

        temp = hdbFlatTypeDetails.get("flatType");
        this.flatType = temp == null ? "" : temp.toString();

        temp = hdbFlatTypeDetails.get("affordability");
        this.affordability = temp == null ? false : (Boolean)temp;
    }

    //price GET SET
    public double getPrice()
    {
        return this.price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    //flatType GET SET
    public String getFlatType()
    {
        return this.flatType;
    }

    public void setFlatType(String flatType)
    {
        this.flatType = flatType;
    }

    //affordability GET SET
    public boolean getAffordability()
    {
        return this.affordability;
    }

    public void setAffordability(boolean affordability)
    {
        this.affordability = affordability;
    }

    public HashMap<String, Object> getFlatTypeDetails()
    {
        //initialize
        this.flatTypeDetails = new HashMap<>();

        //set data of this object into HashMap
        //key is the same as attribute name
        this.flatTypeDetails.put("price", this.price);
        this.flatTypeDetails.put("flatType", this.flatType);
        this.flatTypeDetails.put("affordability", this.affordability);

        return this.flatTypeDetails;
    }




}
