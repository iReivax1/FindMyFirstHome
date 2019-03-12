package com.example.findmyfirsthome.Controller;
import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.Entity.HDBFlatType;
import com.example.findmyfirsthome.Entity.MapData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class HDBDetailsManager extends AsyncTask<String, Void, Void>  {
    private String urlDetails = "http://esales.hdb.gov.sg/bp25/launch/19feb/bto/19FEBBTO_page_6280/$file/about0.html";
    private String urlMain = "http://esales.hdb.gov.sg/bp25/launch/19feb/bto/19FEBBTO_page_6280/$file/about0.html";
    private ProgressDialog mProgressDialog;

    public HDBDetailsManager(){
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.setTitle("Android Basic JSoup Tutorial");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    //scrap data happens here
    @Override
    protected Void doInBackground(String... url) throws IOException {

        ArrayList<String> HDBEstateName = new ArrayList<String>();
        ArrayList<String> temp;
        //BoonLay & Jurong west
        temp = (scrapDevelopmentName(urlMain, 0, 3, 1)); //scrap from table 0, 4th row 2nd data;
        addToList(temp,HDBEstateName);
        print(scrapFlatType(urlMain,0 ,3,4, 8 ));
        //SK
        temp = (scrapDevelopmentName(urlMain, 0, 8, 1));
        addToList(temp,HDBEstateName);
        print(scrapFlatType(urlMain,0,8,9, 13));
        //Kallang
        temp = (scrapDevelopmentName(urlMain, 0, 4, 1));
        addToList(temp,HDBEstateName);
        print(scrapFlatType(urlMain,0,8,9, 13));
        print(HDBEstateName);

        description(urlDetails, "Jurong West Jewel", "Boon Lay Glade");

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        mProgressDialog.dismiss();
        HDBDevelopment HDBD = new HDBDevelopment(flatTypeList, developmentName, developmentDescription,
        false, LatLng coordinates, ArrayList<MapData > amenities)
    }


    public boolean writesData(ArrayList<HDBDevelopment> dev){
        MapsController mc = new MapsController();
        Context context = mc.getContext();
        DatabaseController db = new DatabaseController(context);
        //get flattype
        //create faltType in the class
        //use writeData to enter flat type
        for(HDBDevelopment hdb : dev){
            db.writeHDBata(hdb);
        }
        return true;
    }

    //method overload
    public boolean writesData(HDBFlatType flat)
    {
        return true;
    }

    public  void description(String url, String developmentName1, String developmentName2) throws IOException {
        Document document = Jsoup.connect(url).get();
        for(int i = 0; i <  document.select("p").size(); i ++) {
            Element paragraphs = document.select("p").get(i);
            if(paragraphs.text().length() > 150) {
                String description = paragraphs.text();
                if(description.contains(developmentName1)) {
                    System.out.println(developmentName1 + ": " + description);
                }else if(description.contains(developmentName2)) {
                    System.out.println('\n');
                    System.out.println(developmentName2 + ": " + description);
                }

            }
        }
    }

    //overloading cuz HDB a bitch
    public  void description(String url, String developmentName1) throws IOException {
        Document document = Jsoup.connect(url).get();
        for (int i = 0; i < document.select("p").size(); i++) {
            Element paragraphs = document.select("p").get(i);
            if (paragraphs.text().length() > 150) {
                String description = paragraphs.text();
                if (description.contains(developmentName1)) {
                    System.out.println(developmentName1 + ": " + description);
                }
            }
        }
    }

    public  HashMap<String, String> scrapFlatType(String url, int tableNumber, int firstRowNumber, int rowStart, int rowEnd){
        HashMap<String, String> flatType = new HashMap<String, String>();
        try {
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
            flatType.put(rooms, price);
            //get(3) for Boonlay => 4 ~ 8
            //get(8) for SK => 9~13
            //get(14) for Kallang => 15~16
            for(int i = rowStart; i < rowEnd; i++) {
                row = rows.get(i);

                cols = row.select("td");

                rooms = cols.get(0).text();
                price = cols.get(1).text();
                flatType.put(rooms,price);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }


        return flatType;
    }

    public ArrayList<String> scrapDevelopmentName(String url, int tableNumber, int rowNumber, int colNumber) {
        try {
            ArrayList<String> textList = new ArrayList<String>();
            Document document = Jsoup.connect(url).get();

            Element table = document.select("table").get(tableNumber); //select the first table.

            Elements rows = table.select("tr");
            Element row = rows.get(rowNumber);

            Elements cols = row.select("td");

            String text = cols.get(colNumber).html();

            if(text.contains("<br>")) {
                String[] textArray= text.split("<br>");
                for(int i =0 ; i < textArray.length ; i++) {
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

    public void addToList(ArrayList<String> temp, ArrayList<String> target) {
        for(int i = 0; i < temp.size() ; i++ ) {
            target.add(temp.get(i));
        }
    }

    public void print(ArrayList<String> stringList) {
        System.out.println("-----------------Inside the arrayList-----------------");
        for (int i = 0; i < stringList.size(); i++) {
            System.out.println("Index: " + i + " :" + stringList.get(i));
        }
    }

    public void print(HashMap<String, String> hashList) {
        System.out.println("-----------------Inside the hashmap-----------------");
        for (String key: hashList.keySet()){
            System.out.println(key);
            System.out.println(hashList.get(key));
        }
    }
    public void print(String[] string, int i) {
        System.out.println("index: " + i + string[i]);
    }

}





