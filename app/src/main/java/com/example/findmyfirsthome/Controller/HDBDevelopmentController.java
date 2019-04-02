package com.example.findmyfirsthome.Controller;

import android.content.Context;

import com.example.findmyfirsthome.Entity.CalculatedProfile;
import com.example.findmyfirsthome.Entity.HDBDevelopment;

import java.util.ArrayList;
import java.util.List;

public class HDBDevelopmentController {

    private DataAccessInterfaceClass db;
    ArrayList<String> listHDBName;
    ArrayList<String> listHDBurl;
    public HDBDevelopmentController(Context currentContext){
        db = DataAccessFactory.getDatabaseCtrlInstance(currentContext);
    }

    public List<String> getAllHDBnames(){
        ArrayList<HDBDevelopment> temp = db.readHDBData();
        listHDBName = new ArrayList<>();
        for(HDBDevelopment i : temp){
            listHDBName.add(i.getDevelopmentName());
        }
        return listHDBName;
    }

    public List<String> getAllHDBurl(){
        ArrayList<HDBDevelopment> temp =db.readHDBData();
        listHDBurl = new ArrayList<>();
        for(HDBDevelopment i : temp){
            listHDBurl.add(i.getImgUrl());
        }
        return listHDBurl;
    }

    public List<String> getRecHDBnames(){
        ArrayList<HDBDevelopment> temp = db.readHDBData();
        listHDBName = new ArrayList<>();
        for(HDBDevelopment i : temp){
            if(i.getAffordable())
                listHDBName.add(i.getDevelopmentName());
        }
        return listHDBName;
    }

    public List<String> getRecHDBurl(){
        ArrayList<HDBDevelopment> temp =db.readHDBData();
        listHDBurl = new ArrayList<>();
        for(HDBDevelopment i : temp){
            if(i.getAffordable())
                listHDBurl.add(i.getImgUrl());
        }
        return listHDBurl;
    }

    public List<String> getFooterDetails(){
        CalculatedProfile cp = db.readCalculatedProfile();
        List<String> footerDetails = new ArrayList<>();
        if (cp == null)
            return footerDetails;
        footerDetails.add(Integer.toString((int)Math.ceil(cp.getMaxPropertyPrice())));
        footerDetails.add(Integer.toString((int)Math.ceil(cp.getMaxMortgage())));
        footerDetails.add(Integer.toString((int)Math.ceil(cp.getMaxMortgagePeriod())));
        return footerDetails;
    }
}
