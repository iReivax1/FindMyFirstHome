package com.example.findmyfirsthome.Controller;
import android.os.AsyncTask;
import android.util.Log;

import com.example.findmyfirsthome.Entity.HDBDevelopment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


//Startup Controller just call write data, write grants


public class HDBDetailsManager extends AsyncTask<String, Void, Void> {

    //variables init;
    //url to be given by startUp controller
    private String urlMain1 = "http://esales.hdb.gov.sg/bp25/launch/19feb/bto/19FEBBTOJW_page_6280/$file/about1.html"; //jurong
    private String urlMain2 = "http://esales.hdb.gov.sg/bp25/launch/19feb/bto/19FEBBTOSK_page_6280/$file/about1.html"; //sk
    private String urlMain3 = "http://esales.hdb.gov.sg/bp25/launch/19feb/bto/19FEBBTOKWN_page_6280/$file/about1.html"; //kallng
    private String urlALL = "http://esales.hdb.gov.sg/bp25/launch/19feb/bto/19FEBBTO_page_6280/$file/about0.html";
    private String urlGrants1 = "https://www.hdb.gov.sg/cs/infoweb/residential/buying-a-flat/new/first-timer-applicants"; //both first
    private String urlGrants2 = "https://www.hdb.gov.sg/cs/infoweb/residential/buying-a-flat/new/first-timer-and-second-timer-couple-applicants"; //one first , one second
    //second timer : $15,000 => no need scrap just hardcode make my life easier thanks.
    private ArrayList<String> HDBDevelopmentNames = new ArrayList<String>();
    //String key: Income req, String object's key grant type, double grant amount
    private ArrayList<HashMap<String, Object>> ListFlatTypePrice = new ArrayList<HashMap<String, Object>>();
    private HashMap<String, HashMap<String, Double>> firstTimerGrantList = new HashMap<String, HashMap<String, Double>>();
    private HashMap<String, HashMap<String, Double>> fsTimerGrantList = new HashMap<String, HashMap<String, Double>>();
    private String descriptionText;
    private String ImgURL;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //scrap data happens here
    @Override
    protected Void doInBackground(String... url) {

        ///////////////////////////////////Jurong///////////////////////////////////
        //Scrap development name : BoonLay & Jurong west
        HDBDevelopmentNames = (scrapDevelopmentName(urlALL, 0, 3, 1)); //scrap from table 0, 4th row 2nd data;
        //Scrap List of flat type for Boonlay's HDB
        ListFlatTypePrice = (scrapFlatType(urlALL, 0, 3, 4, 8));
        //Scrap description text for this development
        descriptionText = description(urlMain1, HDBDevelopmentNames.get(0), HDBDevelopmentNames.get(1)); //"jurong west jewel", Boon Lay Glade
        ImgURL = scrapImage(urlMain1);
        adaptHDBD(HDBDevelopmentNames, ListFlatTypePrice, descriptionText, ImgURL);
        ///////////////////////////////////SK///////////////////////////////////
        //Scrap development name : SK
        HDBDevelopmentNames = (scrapDevelopmentName(urlALL, 0, 8, 1));
        //Scrap List of flat type for SK's HDB
        ListFlatTypePrice.add((scrapFlatType(urlALL, 0, 8, 9, 13)));
        //Scrap description text for this development
        descriptionText = description(urlMain2, HDBDevelopmentNames.get(2)); //"SK one"
        ImgURL = scrapImage(urlMain2);
        adaptHDBD(HDBDevelopmentNames, ListFlatTypePrice, descriptionText, ImgURL);
        ///////////////////////////////////Kallang///////////////////////////////////
        //Scrap development name : Kallang
        HDBDevelopmentNames = (scrapDevelopmentName(urlALL, 0, 4, 1));
        //Scrap List of flat type for Kallang's HDB
        ListFlatTypePrice.add((scrapFlatType(urlALL, 0, 8, 9, 13)));
        //Scrap description text for this development
        descriptionText = description(urlMain3, HDBDevelopmentNames.get(3)); // Kallang /whampoa one
        ImgURL = scrapImage(urlMain3);
        adaptHDBD(HDBDevelopmentNames, ListFlatTypePrice, descriptionText, ImgURL);
        //scrap grants
        firstTimerGrantList = scrapGrants(urlGrants1);
        fsTimerGrantList = scrapGrants(urlGrants2);
        adaptGrants(firstTimerGrantList);
        adaptGrants(fsTimerGrantList);

        //HDBDevelopmentName => HDBDevelopmentName
        //Flat type for that hdb name => ListFlatTypePrice
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }


    public ArrayList<HDBDevelopment> getHDBData() {
        return createHDBDevelopment();
    }


    protected ArrayList<HDBDevelopment> createHDBDevelopment() {
        int index = 0;
        HDBDevelopment HDBD = null;
        ArrayList<HDBDevelopment> HDBDList = new ArrayList<HDBDevelopment>();
        if (HDBDevelopmentNames.isEmpty()) {
            return null;
        } else {
            while (HDBDevelopmentNames.get(index) != null) {
                HDBD = new HDBDevelopment(ListFlatTypePrice, HDBDevelopmentNames.get(index), descriptionText, false, null, null, ImgURL);
                HDBDList.add(HDBD);
                index++;
            }
        }
        return HDBDList;
    }

    public boolean adaptHDBD(ArrayList<String> HDBDevelopmentNames, ArrayList<HashMap<String, Object>> ListFlatType, String descriptionText, String ImgURL) {

        HDBSplashscreenController adapter = new HDBSplashscreenController();


        //For each of the HDBDevelopmenet
        for (String name : HDBDevelopmentNames) {
            //Write each of the hashmap into each development name
            for(HashMap<String, Object> ft : ListFlatType){
                adapter.writeHDBD(name, ft, descriptionText, ImgURL);
            }
        }

        return true;
    }

    public void adaptGrants(HashMap<String, HashMap<String, Double>> list) {
        HDBSplashscreenController adapter = new HDBSplashscreenController();

        for (String key : list.keySet()) {
            String incomeReq = key;
            HashMap<String, Double> grant = list.get(key);
            adapter.writeHDBGrantData(incomeReq, grant);
        }
    }


    protected String description(String url, String developmentName1, String developmentName2) {

        try {
            Document document = Jsoup.connect(url).get();
            for (int i = 0; i < document.select("p").size(); i++) {
                Element paragraphs = document.select("p").get(i);
                if (paragraphs.text().length() > 150) {
                    String description = paragraphs.text();
                    if (description.contains(developmentName1)) {
                        return (developmentName1 + ": " + description);
                    } else if (description.contains(developmentName2)) {
                        return ('\n' + developmentName2 + ": " + description);
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //overloading cuz HDB a bitch
    protected String description(String url, String developmentName1) {
        try {
            Document document = Jsoup.connect(url).get();
            for (int i = 0; i < document.select("p").size(); i++) {
                Element paragraphs = document.select("p").get(i);
                if (paragraphs.text().length() > 150) {
                    String description = paragraphs.text();
                    if (description.contains(developmentName1)) {
                        return (developmentName1 + ": " + description);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
    //each development wil have 1 arraylist each arraylist will have mulitple hashmaps
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
                }
            }
            if (text.contains("<sup>")) {
                text = cols.get(colNumber).text();
                textList.add(text);
            }
            return textList;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Controller to do tell this guy which web to scrap
    //first timer : https://www.hdb.gov.sg/cs/infoweb/residential/buying-a-flat/new/first-timer-applicants
    //First and Second : https://www.hdb.gov.sg/cs/infoweb/residential/buying-a-flat/new/first-timer-and-second-timer-couple-applicants
    //second timer : $15,000
    public HashMap<String, HashMap<String, Double>> scrapGrants(String url) {
        String grant = "0";
        Elements cols = null;
        Element col = null;
        Element row = null;
        HashMap<String, HashMap<String, Double>> grantList = new HashMap<String, HashMap<String, Double>>();  //HM<IncomeRequired, HM<GrantType, GrantAmt>>
        HashMap<String, Double> tempHM = new HashMap<String, Double>();
        ArrayList<String> incomeReq = new ArrayList<String>();
        ArrayList<Double> AHG = new ArrayList<Double>();
        ArrayList<Double> SHG = new ArrayList<Double>();
        String[] temp = null;
        try {
            Document document = Jsoup.connect("url").get();

            Element table = document.select("table").get(1); //select the second table.
            Elements body = table.select("tbody");
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
                            //System.out.println(income);
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

            for (int i = 0; i < 15; i++) {
                tempHM.put("SHG", SHG.get(i));
                tempHM.put("AHG", AHG.get(i));
                grantList.put(incomeReq.get(i), tempHM);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return grantList;
    }

    public String scrapImage(String url) {
        String imgUrl = "" ;
        try {
            Document document = Jsoup.connect(url).get();
            Element image = document.select("img").get(1);
            imgUrl = image.absUrl("src");
            System.out.println(imgUrl);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return imgUrl;
    }



//    ----------------------------DEBUG PRINT METHODS----------------------------

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

    public void print(HashMap<String, String> hashList) {
        System.out.println("-----------------Inside the hashmap-----------------");
        for (String key: hashList.keySet()){
            Log.d("hello",key);
            Log.d("hello", hashList.get(key));
        }
    }
    public void print(String[] string, int i) {
        System.out.println("index: " + i + string[i]);
    }

}


//TODO:
///Need to scrap this and put in HashMap<String(for area i.e Ang Mo Kio), HashMap<String (For flat type), Double(Price)>>;
//https://www.hdb.gov.sg/cs/infoweb/residential/renting-a-flat/renting-from-the-open-market/rental-statistics
//This is to find the Annual value, rental of latest quarter * 12;
