package com.example.findmyfirsthome.Controller;

import android.content.Context;

import com.example.findmyfirsthome.Entity.HDBDevelopment;

import java.util.ArrayList;
import java.util.List;

public class HDBDevelopmentController {

    private DatabaseController db;
    ArrayList<String> listHDBName;
    ArrayList<String> listHDBurl;
    public HDBDevelopmentController(Context currentContext){
        db = new DatabaseController(currentContext);
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
}
