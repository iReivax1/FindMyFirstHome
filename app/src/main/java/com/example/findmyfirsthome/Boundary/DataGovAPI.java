package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.findmyfirsthome.Controller.DatabaseController;
import com.example.findmyfirsthome.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class DataGovAPI extends AppCompatActivity {

    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    String typeData;
    String childCareURL = "https://data.gov.sg/api/action/datastore_search?resource_id=4fc3fd79-64f2-4027-8d5b-ce0d7c279646&limit=";  // This is the API base URL (GitHub API)
    String marketURL = "https://data.gov.sg/api/action/datastore_search?resource_id=8f6bba57-19fc-4f36-8dcf-c0bda382364d&limit=";
    String schoolURL = "https://data.gov.sg/api/action/datastore_search?resource_id=ede26d32-01af-4228-b1ed-f05c45a1d8ee&limit=";
    String taxURL = "https://data.gov.sg/api/action/datastore_search?resource_id=bb6f5bf8-7d0b-4526-b020-b812ea7d7d89&limit=10";
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.
    Context context = this;

    MapAPI maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_gov_api);
        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
        getDataFromDataGov("childCare", 10);
        getDataFromDataGov("market", 10);
        getDataFromDataGov("school", 10);
        maps = new MapAPI(context);
    }


    public void getDataFromDataGov(String type, int limit) { //type is childcare market all these, limit iss how many toquery from 5 6 7?
        getList(type,Integer.toString(limit));
    }

    //limit is the limit number for the number of search queries > 1 please.
    private void getList(String type, String limit) {
        //this is the datagov limit
        this.typeData = type;

        //Check type to set url
        if(typeData.equals("childCare")){
            this.url = this.childCareURL + limit;
        }
        else if(typeData.equals("market")){
            this.url = this.marketURL + limit;
        }
        else if(typeData.equals("school")){
            this.url = this.schoolURL + limit;
        }
        else if(typeData.equals("tax")){
            this.url = this.taxURL;
        }
        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if(typeData.equals("childCare")) {
                    JSONParserChildCare(response);
                }
                else if(typeData.equals("market")){
                    JSONParserMarket(response);
                }
                else if(typeData.equals("school")){
                    JSONParserSchool(response);
                }
                else if(typeData.equals("tax")){
                    JSONParserTax(response);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", "error");
            }
        });

        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(jsonObjReq);
    }




//    public void query(String condition) {
//      ("https://data.gov.sg/api/action/datastore_search?resource_id=ede26d32-01af-4228-b1ed-f05c45a1d8ee&q=" + condition);
//        ("https://data.gov.sg/api/action/datastore_search?resource_id=4fc3fd79-64f2-4027-8d5b-ce0d7c279646&limit=5");
//
//        ("https://data.gov.sg/api/action/datastore_search?resource_id=bb6f5bf8-7d0b-4526-b020-b812ea7d7d89&q=" + condition);
//        //HDB confirm 4%; For owner-occupied HDB flats, you need not pay tax on the first $8,000 of the AV from 2014. The remaining AV will be taxed at the lowest tier of 4%.
//    }

    /////////////////////////////////ChildCare/////////////////////////////////

    public void JSONParserChildCare(JSONObject obj) {
        LatLng coordinates;
        HashMap<String, Object> info = new HashMap<>();
        if (obj != null) {
            try {
                JSONObject jsonObj = obj;
                //Log.d("test", obj.toString());
                // Getting JSON Array node
                JSONObject result = jsonObj.getJSONObject("result");
                JSONArray records = result.getJSONArray("records");
                Log.d("Error", Integer.toString(records.length()));
                // looping through All Contacts
                for (int i = 0; i < records.length(); i++) {
                    JSONObject c = records.getJSONObject(i);
                    String centre_name = c.getString("centre_name");
                    String centre_address = c.getString("centre_address");

                    // adding each child node to HashMap key => value
                    info.put("AmenitiesType", "ChildCare");
                    info.put("AmenitiesName", centre_name);

                    //Using GEOCODING
                    coordinates = maps.getAmenitiesCoordinates(centre_address);
                    info.put("AmenitiesLat", coordinates.latitude);
                    System.out.println(Double.toString(coordinates.latitude));
                    info.put("AmenitiesLng", coordinates.longitude);
                    System.out.println(Double.toString(coordinates.longitude));

                }
                writeAmenitiesToDB(info);
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("ERROR", "Couldn't get json from server.");
        }
    }

    /////////////////////////////////Market/////////////////////////////////

    public void JSONParserMarket(JSONObject obj) {
        LatLng coordinates;
        HashMap<String, Object> info = new HashMap<>();
        if (obj != null) {
            try {
                JSONObject jsonObj = obj;
                //Log.d("test", obj.toString());
                // Getting JSON Array node
                JSONObject result = jsonObj.getJSONObject("result");
                JSONArray records = result.getJSONArray("records");
                // looping through All Contacts
                for (int i = 0; i < records.length(); i++) {
                    JSONObject c = records.getJSONObject(i);

                    String name_of_centre = c.getString("name_of_centre");
                    String location_of_centre = c.getString("location_of_centre");
                    // tmp hash map for single contact

                    // adding each child node to HashMap key => value
                    info.put("AmenitiesType", "Market");
                    info.put("AmenitiesName", name_of_centre);

                    //Using GEOCODING
//                    coordinates = maps.getAmenitiesCoordinates(location_of_centre);
//                    info.put("AmenitiesLat", coordinates.latitude);
//                    info.put("AmenitiesLng", coordinates.longitude);

                }
                // adding contact to contact list
                writeAmenitiesToDB(info);
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        }
        else {
            Log.e("ERROR", "Couldn't get json from server.");
        }
    }

    /////////////////////////////////School/////////////////////////////////

    public void JSONParserSchool(JSONObject obj) {

        LatLng coordinates;
        if (obj != null) {
            HashMap<String, Object> info = new HashMap<>();
            try {
                JSONObject jsonObj = obj;
                //Log.d("test", obj.toString());
                // Getting JSON Array node
                JSONObject result = jsonObj.getJSONObject("result");
                JSONArray records = result.getJSONArray("records");
                Log.d("Error", Integer.toString(records.length()));
                // looping through All Contacts
                for (int i = 0; i < records.length(); i++) {
                    JSONObject c = records.getJSONObject(i);

                    String schoolName = c.getString("school_name");
                    System.out.println(schoolName);
                    String postalCode = c.getString("postal_code");
                    System.out.println(postalCode);
                    // adding each child node to HashMap key => value
                    info.put("AmenitiesType", "School");
                    info.put("AmenitiesName", schoolName);

                    //Using GEOCODING
//                    coordinates = maps.getAmenitiesCoordinates(postalCode);
//                    info.put("AmenitiesLat", coordinates.latitude);
//                    info.put("AmenitiesLng", coordinates.longitude);
                    // adding contact to contact list
                    //infoList.add(info);
                }
                writeAmenitiesToDB(info);
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        }
        else {
            Log.e("ERROR", "Couldn't get json from server.");
        }

    }

    /////////////////////////////////Parse Tax//////////////////////////////////

    public void JSONParserTax(JSONObject obj) {
        if (obj != null) {
            HashMap<String, String> info = new HashMap<>();
            ArrayList<HashMap<String, String>> infoList = new ArrayList<>();
            try {
                JSONObject jsonObj = obj;
                //Log.d("test", obj.toString());
                // Getting JSON Array node
                JSONObject result = jsonObj.getJSONObject("result");
                JSONArray records = result.getJSONArray("records");
                Log.d("Error", Integer.toString(records.length()));
                // looping through All Contacts
                for (int i = 0; i < records.length(); i++) {
                    JSONObject c = records.getJSONObject(i);

                    String typeOfProperty = c.getString("type_of_property");
                    String taxRate = c.getString("tax_rate");
                    String annualValue = c.getString("annual_value");
                    // tmp hash map for single contact


                    // adding each child node to HashMap key => value
                    info.put("typeOfProperty", typeOfProperty);
                    info.put("taxRate", taxRate);
                    info.put("annualValue", annualValue);
                    infoList.add(info);

                }
                writeTaxToDB(infoList);
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        }
        else {
            Log.e("ERROR", "Couldn't get json from server.");
        }

    }

    public void writeAmenitiesToDB( HashMap<String, Object> list){
        DatabaseController db = DatabaseController.getInstance(context);
        db.writeAmenitiesData(list);
    }

    public void writeTaxToDB(ArrayList<HashMap<String, String>> infoList){
        DatabaseController db = DatabaseController.getInstance(context);
        db.writeTax(infoList);
    }

    private static void readKML(InputStream fileKML, String nameCoordinates) {
        String column = null;
        Boolean folder = Boolean.TRUE;
        Boolean placemark = Boolean.FALSE;
        Boolean placeCorrect = Boolean.FALSE;
        BufferedReader br = new BufferedReader(new InputStreamReader(fileKML));
        try {
            while ((column = br.readLine()) != null) {
                if (folder) {
                    int ifolder = column.indexOf("");
                    if (ifolder != -1) {
                        folder = Boolean.FALSE;
                        placemark = Boolean.TRUE;
                        continue;
                    }
                }
                if (placemark) {
                    String tmpLine = nameCoordinates;
                    tmpLine = tmpLine.replaceAll("\t", "");
                    tmpLine = tmpLine.replaceAll(" ", "");
                    String tmpColumn = column;
                    tmpColumn = tmpColumn.replaceAll("\t", "");
                    tmpColumn = tmpColumn.replaceAll(" ", "");
                    int name = tmpColumn.indexOf(tmpLine);
                    if (name != -1) {
                        placemark = Boolean.FALSE;
                        placeCorrect = Boolean.TRUE;
                        continue;
                    }
                }
                if (placeCorrect) {
                    int coordin = column.indexOf("<coordinates>");
                    if (coordin != -1) {
                        String tmpCoordin = column;
                        tmpCoordin = tmpCoordin.replaceAll(" ", "");
                        tmpCoordin = tmpCoordin.replaceAll("\t", "");
                        tmpCoordin = tmpCoordin.replaceAll("<coordinates>", "");
                        tmpCoordin = tmpCoordin
                                .replaceAll("</coordinates>", "");
                        String[] coo = tmpCoordin.split(",");
                        System.out.println("LONG: "+coo[0]);
                        System.out.println("LATI: "+coo[1]);
                        break;
                    }
                }

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


//////////////////////////////////////////////
// TODO:KML  to parse:
// https://data.gov.sg/dataset/chas-clinics?resource_id=21dace06-c4d1-4128-9424-aba7668050dc
// https://geo.data.gov.sg/market-food-centre/2014/12/26/kml/market-food-centre.kml
// https://geo.data.gov.sg/g-mp08-act-mrt-stn-pt/2011/03/29/kml/g-mp08-act-mrt-stn-pt.zip