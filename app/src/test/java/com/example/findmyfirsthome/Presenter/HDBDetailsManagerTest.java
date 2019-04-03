package com.example.findmyfirsthome.Presenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class HDBDetailsManagerTest {

    @Test
    public void adaptHDBD(ArrayList<String> HDBDevelopmentNames, ArrayList<HashMap<String, Object>> ListFlatType, ArrayList<String> descriptionText, ArrayList<String> ImgURL) {
        int index = 0;
        for (String name : HDBDevelopmentNames) {
            writeHDBDataPlusGetCoord(name, descriptionText.get(index), ImgURL.get(index), false);
            for(int i=0;i<ListFlatType.size();i++) {
                HashMap<String, Object> ftNew = new HashMap<String, Object>();
                for(String ft : ListFlatType.get(i).keySet()) {
                    ftNew.put(ft,ListFlatType.get(i).get(ft));
                }
                writeHDBFlatData(name, ftNew);
            }
            index++;
        }

        return true;
    }
}