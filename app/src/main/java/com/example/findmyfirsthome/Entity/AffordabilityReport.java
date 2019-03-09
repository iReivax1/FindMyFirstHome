package com.example.findmyfirsthome.Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class AffordabilityReport {
    private CalculatedProfile cp;
    private ArrayList<HDBDevelopment> hdbdList;
    private HashMap initalReportDetails;
    private ArrayList<HashMap<String, Object>> hdbdForReportDetails;


    //CalculatedProfile association object Get Set
    public void setCalculatedProfile(CalculatedProfile calculatedProfile)
    {
        this.cp = calculatedProfile;
    }

    //HDBDevelopment association object Get Set
    //returns HashMap of data in List of Strings and Values
    public HashMap getInitalReportDetails()
    {
        //initialize
        this.initalReportDetails = new HashMap<String, Object>();
        this.hdbdForReportDetails = new ArrayList<HashMap<String, Object>>();

        //put all the required data for report from CalculatedProfile entity and  HDBDevelopment entity
        this.initalReportDetails.put("CalculatedProfile", cp.getCalProfileDetails());
        //for each Loop
        this.initalReportDetails.forEach((hdbd) -> this.hdbdForReportDetails.add(hdbd.getDevelopmentDetails()));
        this.initalReportDetails.put("HDBDevelopment", hdbdForReportDetails);

        return initalReportDetails;
    }

    public void setHDBDevelopmentList(ArrayList<HDBDevelopment> hdbDevelopmentList)
    {
        this.hdbdList = hdbDevelopmentList;
    }
}
