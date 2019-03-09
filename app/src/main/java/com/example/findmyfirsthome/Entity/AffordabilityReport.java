package com.example.findmyfirsthome.Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class AffordabilityReport {
    private CalculatedProfile cp;
    private ArrayList<HDBDevelopment> hdbdList;
    private HashMap<String, Object> initalReportDetails;
    private ArrayList<ArrayList> hdbForReportDetails;


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
        this.hdbForReportDetails = new ArrayList<ArrayList>();

        //put all the required data for report from CalculatedProfile entity and  HDBDevelopment entity
        this.initalReportDetails.put("CalculatedProfile", cp.getCalProfileDetails());
        //for each Loop
        for(HDBDevelopment hdbd : hdbdList)
        {
            this.hdbForReportDetails.add(hdbd.getDevelopmentDetails());
        }
        this.initalReportDetails.put("HDBDevelopment", hdbForReportDetails);

        return initalReportDetails;
    }

    public void setHDBDevelopmentList(ArrayList<HDBDevelopment> hdbDevelopmentList)
    {
        this.hdbdList = hdbDevelopmentList;
    }
}
