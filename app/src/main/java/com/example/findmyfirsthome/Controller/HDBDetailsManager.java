package com.example.findmyfirsthome.Controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


//Startup Controller just call write data, write grants


public class HDBDetailsManager extends AsyncTask<String, Void, Void> {

    //variables init;
    //url to be given by startUp controller
    private String urlMain1 = "http://esales.hdb.gov.sg/bp25/launch/19feb/bto/19FEBBTOJW_page_6280/$file/about1.html"; //jurong
    private String urlMain2 = "http://esales.hdb.gov.sg/bp25/launch/19feb/bto/19FEBBTOSK_page_6280/$file/about1.html"; //sk
    private String urlMain3 = "http://esales.hdb.gov.sg/bp25/launch/19feb/bto/19FEBBTOKWN_page_6280/$file/about1.html"; //kallng
    private String urlALL = "http://esales.hdb.gov.sg/bp25/launch/19feb/selection/19FEBBTO_page_6280/$file/about0.html";
    private String urlGrants1 = "https://www.hdb.gov.sg/cs/infoweb/residential/buying-a-flat/new/first-timer-applicants"; //both first
    private String urlGrants2 = "https://www.hdb.gov.sg/cs/infoweb/residential/buying-a-flat/new/first-timer-and-second-timer-couple-applicants"; //one first , one second
    //second timer : $15,000 => no need scrap just hardcode make my life easier thanks.
    private ArrayList<String> HDBDevelopmentNames1 = new ArrayList<String>();
    private ArrayList<String> HDBDevelopmentNames2 = new ArrayList<String>();
    private ArrayList<String> HDBDevelopmentNames3 = new ArrayList<String>();
    //String key: Income req, String object's key grant type, double grant amount
    private ArrayList<HashMap<String, Object>> ListFlatTypePrice1 = new ArrayList<HashMap<String, Object>>();
    private ArrayList<HashMap<String, Object>> ListFlatTypePrice2 = new ArrayList<HashMap<String, Object>>();
    private ArrayList<HashMap<String, Object>> ListFlatTypePrice3 = new ArrayList<HashMap<String, Object>>();
    private LinkedHashMap<String, LinkedHashMap<String, Double>> firstTimerGrantList = new LinkedHashMap<String, LinkedHashMap<String, Double>>();
    private LinkedHashMap<String, LinkedHashMap<String, Double>> fsTimerGrantList = new LinkedHashMap<String, LinkedHashMap<String, Double>>();
    private ArrayList<String> descriptionText1 = new ArrayList<>();
    private ArrayList<String> descriptionText2 = new ArrayList<>();
    private ArrayList<String> descriptionText3 = new ArrayList<>();
    private String ImgURLBoonLay;
    private String ImgURLJurong;
    private String ImgFernvale;
    private String ImgKallang;
    private String ImgTowerCrest;
    ArrayList<String> centralImg = new ArrayList<>();
    ArrayList<String> northEastImg = new ArrayList<>();
    ArrayList<String> imageWest = new ArrayList<>();
    private DatabaseController db;

    private Context mContext;


    public HDBDetailsManager (Context mContext){
        this.mContext=mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //scrap data happens here
    @Override
    protected Void doInBackground(String... url) {

        ///////////////////////////////////Jurong///////////////////////////////////
        //Scrap development name : BoonLay & Jurong west
        HDBDevelopmentNames1 = (scrapDevelopmentName(urlALL, 0, 3, 1)); //scrap from table 0, 4th row 2nd data;
        //System.out.println(HDBDevelopmentNames1);
        //Scrap List of flat type for Boonlay's HDB
        ListFlatTypePrice1 = (scrapFlatType(urlALL, 0, 3, 4, 8));
        //System.out.println(ListFlatTypePrice1);
        //Scrap description text for this development
        for(int index = 0; index < HDBDevelopmentNames1.size(); index++){
            //"jurong west jewel", Boon Lay Glade
            descriptionText1.add(scrapDescription(urlMain1, HDBDevelopmentNames1.get(0)));
        }
       // System.out.println(descriptionText1);
        ImgURLBoonLay = scrapImage(urlALL , 1);
        ImgURLJurong = scrapImage(urlALL , 2);

        imageWest.add(ImgURLBoonLay);
        imageWest.add(ImgURLJurong);
        ///////////////////////////////////SK///////////////////////////////////

        //Scrap development name : SK
        HDBDevelopmentNames2 = (scrapDevelopmentName(urlALL, 0, 8, 1));
        //System.out.println(HDBDevelopmentNames2);
        //Scrap List of flat type for SK's HDB
        ListFlatTypePrice2 = (scrapFlatType(urlALL, 0, 8, 9, 13));
        //System.out.println(ListFlatTypePrice2);
        //Scrap description text for this development
        for(int index = 0; index < HDBDevelopmentNames2.size(); index++){
            descriptionText2.add(scrapDescription(urlMain2, HDBDevelopmentNames2.get(index)));//"Fernvale"
        }
        //System.out.println(descriptionText2);
        ImgFernvale = scrapImage(urlALL,3);
        northEastImg.add(ImgFernvale);

        ///////////////////////////////////Kallang///////////////////////////////////
        //Scrap development name : Kallang
        HDBDevelopmentNames3 = (scrapDevelopmentName(urlALL, 0, 14, 1));
        System.out.println(HDBDevelopmentNames3);
        //Scrap List of flat type for Kallang's HDB
        ListFlatTypePrice3 = (scrapFlatType(urlALL, 0, 14, 15, 16));
        //System.out.println(ListFlatTypePrice3);
        //Scrap description text for this development
        for(int index = 0; index < HDBDevelopmentNames3.size(); index++){
            descriptionText3.add(scrapDescription(urlMain3, HDBDevelopmentNames3.get(index))); //"Kallang" , "Tower crest"
        }

        //System.out.println(descriptionText3);
        ImgKallang = scrapImage(urlALL,4);
        ImgTowerCrest = scrapImage(urlALL,5);
        centralImg.add(ImgKallang);
        centralImg.add(ImgTowerCrest);
        //scrap grants
        firstTimerGrantList = scrapGrants(urlGrants1);
        //printGrants(firstTimerGrantList);
        fsTimerGrantList = scrapGrants(urlGrants2);
        //printGrants(fsTimerGrantList);
        //HDBDevelopmentName => HDBDevelopmentName
        //Flat type for that hdb name => ListFlatTypePrice
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        adaptHDBD(HDBDevelopmentNames1, ListFlatTypePrice1, descriptionText1, imageWest);
        adaptHDBD(HDBDevelopmentNames2, ListFlatTypePrice2, descriptionText2, northEastImg);
        adaptHDBD(HDBDevelopmentNames3, ListFlatTypePrice3, descriptionText3, centralImg);
        adaptGrants(firstTimerGrantList);
        adaptGrants(fsTimerGrantList);
    }

//    ----------------------------------------Open up all the convoluted data structure and store it in simple form--------------------------------------------

    public boolean adaptHDBD(ArrayList<String> HDBDevelopmentNames, ArrayList<HashMap<String, Object>> ListFlatType, ArrayList<String> descriptionText, ArrayList<String> ImgURL) {

        //For each of the HDBDevelopmenet
        int index = 0;
        for (String name : HDBDevelopmentNames) {
            writeHDBData(name, descriptionText.get(index), ImgURL.get(index), false);
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

    public void adaptGrants(LinkedHashMap<String, LinkedHashMap<String, Double>> list) {

        for (String key : list.keySet()) {
            HashMap<String, Double> sub = list.get(key);
            for(String subKey: sub.keySet()){
                String tempKey = subKey;
                Double tempValue = sub.get(subKey);
                LinkedHashMap<String, Double> temp = new LinkedHashMap<String, Double>();
                temp.put(tempKey,tempValue);
                writeHDBGrantData(key,temp);
            }
        }
    }

//  ----------------------------------------Write functions to database object--------------------------------------------

    public void writeHDBData(String HDBDevelopmentNames, String descriptionText, String ImgURL, Boolean affordable){
        db = DatabaseController.getInstance(mContext);
        db.writeHDBData(HDBDevelopmentNames,descriptionText,ImgURL, affordable);

    }

    public void writeHDBFlatData(String HDBDevelopmentNames, HashMap<String, Object> ListFlatType){
        db = DatabaseController.getInstance(mContext);
        db.writeHDBFlatTypeData(HDBDevelopmentNames,ListFlatType);
    }

    public void writeHDBGrantData(String incomeReq, HashMap<String, Double> grant){
        db = DatabaseController.getInstance(mContext);
        db.writeHDBGrantData(incomeReq, grant);
//        String temp = "from writeHDBData " + incomeReq;
//        Log.d("manager",temp);
    }

//  ----------------------------------------Scrap Development names of a single BTO launch--------------------------------------------

    protected ArrayList<String> scrapDevelopmentName(String url, int tableNumber, int rowNumber, int colNumber) {
        try {
            ArrayList<String> textList = new ArrayList<String>();
            Document document = Jsoup.connect(url).get();

            Element table = document.select("table").get(tableNumber); //select the first table.

            Elements rows = table.select("tr");
            Element row = rows.get(rowNumber);

            Elements cols = row.select("td");

            String text = cols.get(colNumber).html();

            if (text.contains("<br>")) {
                String[] textArray = text.split("<br>");
                for (int i = 0; i < textArray.length; i++) {
                    textList.add(textArray[i]);
                    System.out.println(textArray);
                }
            }
            if (text.contains("<sup>")) {
                text = cols.get(colNumber).text();
                if(text.contains("#")) {
                    text = text.replace("#", "");
                }
                textList.add(text);
            }
            return textList;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//  ----------------------------------------Scrap Description for each development--------------------------------------------

    protected String scrapDescription(String url, String developmentName) {
        String allDesc = "";
        try {
            Document document = Jsoup.connect(url).get();
            for(int i = 0; i <  document.select("p").size(); i ++) {
                Element paragraphs = document.select("p").get(i);
                String description = paragraphs.text();
                allDesc +=description;
            }
            allDesc = developmentName +": "+ allDesc;
            //System.out.println(allDesc);
            return allDesc;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

//    ----------------------------------------Scrap FlatType for each development--------------------------------------------
//    Each development wil have 1 arraylist each arraylist will have mulitple hashmaps

    protected ArrayList<HashMap<String, Object>> scrapFlatType(String url, int tableNumber, int firstRowNumber, int rowStart, int rowEnd) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> flatType = null;
        try {
            //Connect to the page
            Document document = Jsoup.connect(url).get();
            Element table = document.select("table").get(tableNumber); //select the first table.

            Elements rows = table.select("tr");
            //For 2-room because HDB wannabe a special snowflake
            //get(3) for Boonlay
            //get(8) for SK
            //get(14) for  kallang
            Element row = rows.get(firstRowNumber);
            Elements cols = row.select("td");

            String rooms = cols.get(2).text();
            String price = cols.get(3).text();
            price = price.replace(",", "").substring(6);
            ///////////1 hashMap/////////////////////
            flatType = new HashMap<String, Object>();
            flatType.put("price", price);
            flatType.put("flatType", rooms);
            flatType.put("affordability", false);
            ///////////////////////////////////////
            list.add(flatType);

            //get(3) for Boonlay => 4 ~ 8
            //get(8) for SK => 9~13
            //get(14) for Kallang => 15~16
            for (int i = rowStart; i < rowEnd; i++) {
                row = rows.get(i);
                cols = row.select("td");
                rooms = cols.get(0).text();
                price = cols.get(1).text();
                price = price.replace(",", "").substring(6);
                ///////////1 hashMap/////////////////////
                flatType = new HashMap<String, Object>();
                flatType.put("price", price);
                flatType.put("flatType", rooms);
                flatType.put("affordability", false);
                ///////////////////////////////////////
                list.add(flatType);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

//    ----------------------------------------Scrap Grants--------------------------------------------
//    FYI if both second timer : $15,000 straight
    public LinkedHashMap<String, LinkedHashMap<String, Double>> scrapGrants(String url) {
        String grant = "0";
        Elements cols = null;
        Element col = null;
        Element row = null;
        LinkedHashMap<String, LinkedHashMap<String, Double>> grantList = new LinkedHashMap<String, LinkedHashMap<String, Double>>();  //HM<IncomeRequired, HM<GrantType, GrantAmt>>
        LinkedHashMap<String, Double> tempHM;
        ArrayList<String> incomeReq = new ArrayList<String>();
        List<Double> AHG = new ArrayList<Double>();
        List<Double> SHG = new ArrayList<Double>();
        String[] temp = null;
        try {
            Document document = Jsoup.connect(url).get();

            Element table = document.select("table").get(1); //select the second table.
            Elements body = table.select("tbody"); //select body of second table
            //select all rows
            Elements rows = body.select("tr");
            //select all row in table body
            for (int i = 0; i < rows.size(); i++) {
                row = rows.get(i);
                cols = row.select("td");
                for (int j = 0; j < cols.size(); j++) {
                    // j == 0 is for income required.
                    switch (j) {
                        case (0):
                            col = cols.get(j);
                            String income = col.text();
                            incomeReq.add(income);
                            break;
                        //j == 1 AHG grant
                        case (1):
                            col = cols.get(j);
                            grant = col.text();
                            if (grant.contains("$")) {
                                grant = grant.substring(1);
                                if (grant.contains(",")) {
                                    temp = grant.split(",");
                                    grant = temp[0] + temp[1];
                                }
                                AHG.add(Double.parseDouble(grant));
                            } else AHG.add(0.0);
                            break;
                        //j ==3 is for SHG
                        case (3):
                            col = cols.get(j);
                            grant = col.text();
                            if (grant.contains("$")) {
                                grant = grant.substring(1);
                                if (grant.contains(",")) {
                                    temp = grant.split(",");
                                    grant = temp[0] + temp[1];
                                }
                                SHG.add(Double.parseDouble(grant));
                            } else SHG.add(0.0);
                            break;
                        default:
                            break;
                    }
                }

            }

            for (int i = 0; i < rows.size(); i++) {
                tempHM = new LinkedHashMap<String, Double>();
                //Log.i("SHG grant: ",  SHG.get(i).toString());
                tempHM.put("SHG", SHG.get(i));
                //Log.i("AHG grant: ",  AHG.get(i).toString());
                tempHM.put("AHG", AHG.get(i));
               // print(tempHM); //HM got no problem
                //Log.i("scrapGrans", incomeReq.get(i)); //scraping is fine
                grantList.put(incomeReq.get(i), tempHM);
            }
            return grantList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return grantList;
    }

//    ----------------------------------------Scrap Image --------------------------------------------

    public String scrapImage(String url, int index) {
        String imgUrl = "" ;
        try {
            Document document = Jsoup.connect(url).get();
                Element image = document.select("img").get(index);
                imgUrl = image.absUrl("src");
                System.out.println(imgUrl);
                //FIRST IS boon  lay glade
                //SECOND IS Jurong west jewel
                //third fernvale
                //4th kallang
                //5th tower crest
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return imgUrl;
    }



//    ----------------------------------------DEBUG PRINT METHODS--------------------------------------------

    private static void printList(ArrayList<Double> shg) {
        System.out.println("-----------------Inside the SHG/AHG-----------------");
        for (int i = 0; i < shg.size(); i++) {
            Log.d("bye","Index: " + i + " :" + shg.get(i));
        }
    }


    public void print(ArrayList<String> stringList) {
        System.out.println("-----------------Inside the arrayList-----------------");
        for (int i = 0; i < stringList.size(); i++) {
            Log.d("hello","Index: " + i + " :" + stringList.get(i));
        }
    }

    public void print(HashMap<String, Double> hashList) {
        System.out.println("-----------------Inside the hashmap-----------------");
        for (String key: hashList.keySet()){
            Log.d("hello",key);
            Log.d("hello", hashList.get(key).toString());
        }
    }
    public void print(String[] string, int i) {
        System.out.println("index: " + i + string[i]);
    }

    public static void printGrants(LinkedHashMap<String, LinkedHashMap<String, Double>> list) {
        for (String key : list.keySet()) {
            String incomeReq = key;
            System.out.println("-----------------Inside the printGrants-----------------");
            System.out.println("Income: " + incomeReq);
            HashMap<String, Double> grant = list.get(key);
            for(String key2 : grant.keySet()) {
                String grantType = key2;
                Double grantAmt = grant.get(key2);
                System.out.println("Type: " + grantType + " " +  "Amount: " + grantAmt);
            }
        }

    }

}


//https://www.hdb.gov.sg/cs/infoweb/residential/renting-a-flat/renting-from-the-open-market/rental-statistics
//This is to find the Annual value, rental of latest quarter * 12;
