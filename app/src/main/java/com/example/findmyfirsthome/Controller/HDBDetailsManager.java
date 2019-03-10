package com.example.findmyfirsthome.Controller;
import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.Entity.HDBFlatType;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class HDBDetailsManager extends AsyncTask<String, Void, Void>  {
    private String url = "https://www.hdb.gov.sg/cs/infoweb/residential/buying-a-flat/new/first-timer-and-second-timer-couple-applicants";
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
    protected Void doInBackground(String... url){
        try {
            Document document = Jsoup.connect(String.valueOf(url)).get();
//			String title = document.title(); //Get title
//			print("  Title: " + title); //Print title.
            for (Element div : document.select("table")) {
                for(Element row : div.select("tr")) {
                    Elements tds = row.select("td");
                    if(tds.size() >= 3) {
                        Log.d( "Creation", tds.text());
                        String text = tds.text();
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        mProgressDialog.dismiss();

    }


    public boolean writesData(ArrayList<HDBDevelopment> dev){

        //get flattype
        //create faltType in the class
        //use writeData to enter flat type
        for(HDBDevelopment hdb : dev){
            HDBFlatType flatType = new HDBFlatType();
            writesData(flatType);
        }
        return true;
    }

    //method overload
    public boolean writesData(HDBFlatType flat)
    {
        return true;
    }

}





