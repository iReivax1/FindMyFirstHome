package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.findmyfirsthome.Controller.DatabaseController;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class DataGovAPI {

    // This is our requests queue to process our HTTP requests.
    String typeData;
    String childCareURL = "https://data.gov.sg/api/action/datastore_search?resource_id=4fc3fd79-64f2-4027-8d5b-ce0d7c279646&limit=";
    String marketURL = "https://data.gov.sg/api/action/datastore_search?resource_id=8f6bba57-19fc-4f36-8dcf-c0bda382364d&limit=";
    String schoolURL = "https://data.gov.sg/api/action/datastore_search?resource_id=ede26d32-01af-4228-b1ed-f05c45a1d8ee&limit=";
    String taxURL = "https://data.gov.sg/api/action/datastore_search?resource_id=bb6f5bf8-7d0b-4526-b020-b812ea7d7d89&limit=";
    String url1;
    String url2;
    String url3;
    String url4;
    // Each of these url will hold the full URL to get json object from respective website.
    Context context;
    MapAPI mapAPI;
    ArrayList<LinkedHashMap<String, Object>> childCareList = new ArrayList<>();
    ArrayList<LinkedHashMap<String, Object>> marketList = new ArrayList<>();
    ArrayList<LinkedHashMap<String, Object>> schoolList = new ArrayList<>();
    ArrayList<LinkedHashMap<String, String>> taxList = new ArrayList<>();
    RequestQueue requestQueue;
    LatLng coord = new LatLng(0,0);

    public void setCoord(LatLng coord) {
        this.coord = coord;
    }

    public DataGovAPI(Context cont) {
        this.context = cont;
         this.mapAPI = new MapAPI(cont, this);
    }


    public void execute() {
        // This setups up a new request queue which we will need to make HTTP requests
        getDataFromDataGov("childCare", 1);
        getDataFromDataGov("market", 1);
        getDataFromDataGov("school", 1);
//         getDataFromDataGov("tax",5);
//        parseKML();


    }


    //limit is the limit number for the number of search queries > 1 please.
    private void getDataFromDataGov(String type, int lim) {

        JsonObjectRequest jsonObjReq = null;
        //this is the datagov limit
        this.typeData = type;
        String limit = Integer.toString(lim);
        //Check type to set url
        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        requestQueue = Volley.newRequestQueue(context);
        if (typeData.equals("childCare")) {
            this.url1 = this.childCareURL + limit;
            jsonObjReq = new JsonObjectRequest(Request.Method.GET, url1, (JSONObject) null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    childCareList = JSONParserChildCare(response); //here is fine
                    writeAmenitiesToDB(childCareList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("test", "error");
                }
            });
        } else if (typeData.equals("market")) {
            this.url2 = this.marketURL + limit;
            jsonObjReq = new JsonObjectRequest(Request.Method.GET, url2, (JSONObject) null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    marketList = JSONParserMarket(response);
                    //print(marketList);
                    writeAmenitiesToDB(marketList);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("test", "error");
                }
            });
        } else if (typeData.equals("school")) {
            this.url3 = this.schoolURL + limit;
            jsonObjReq = new JsonObjectRequest(Request.Method.GET, url3, (JSONObject) null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    schoolList = JSONParserSchool(response);
                    writeAmenitiesToDB(schoolList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("test", "error");
                }
            });
        } else if (typeData.equals("tax")) {
            this.url4 = this.taxURL + limit;
            jsonObjReq = new JsonObjectRequest(Request.Method.GET, url4, (JSONObject) null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    taxList = JSONParserTax(response);
                    writeTaxToDB(taxList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("test", "error");
                }
            });
        }

        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(jsonObjReq);
    }

    /////////////////////////////////ChildCare/////////////////////////////////

    public ArrayList<LinkedHashMap<String, Object>> JSONParserChildCare(JSONObject obj) {
        LatLng coordinates = new LatLng(0,0);
        LinkedHashMap<String, Object> info;
        ArrayList<LinkedHashMap<String, Object>> list = new ArrayList<>();
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
                    // adding each child node to HashMap key => value
                    info = new LinkedHashMap<>();
                    info.put("AmenitiesType", "ChildCare");
                    info.put("AmenitiesName", centre_name);
                    Boolean done;
                    done = mapAPI.getCoordinates(centre_name);
                    System.out.println(coord.latitude + ", " + coord.longitude);
                    if(done){
                        info.put("AmenitiesLat", mapAPI.getCoords().latitude);
                        info.put("AmenitiesLng", mapAPI.getCoords().longitude);
                    }else {
                        info.put("AmenitiesLat", 0.0);
                        info.put("AmenitiesLng", 0.0);
                    }

                    list.add(info);
                }
                return list;
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("ERROR", "Couldn't get json from server.");
        }

        return list;
    }

    /////////////////////////////////Market/////////////////////////////////

    public ArrayList<LinkedHashMap<String, Object>> JSONParserMarket(JSONObject obj) {
        LatLng coordinates;
        LinkedHashMap<String, Object> info;
        ArrayList<LinkedHashMap<String, Object>> list = new ArrayList<>();
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
                    info = new LinkedHashMap<>();
                    // adding each child node to HashMap key => value
                    info.put("AmenitiesType", "Market");
                    info.put("AmenitiesName", name_of_centre);
                    info.put("AmenitiesLat", 0.0);
                    info.put("AmenitiesLng", 0.0);
                    ///Using GEOCODING
//                    coordinates = maps.getAmenitiesCoordinates(location_of_centre);
//                    info.put("AmenitiesLat", coordinates.latitude);
//                    info.put("AmenitiesLng", coordinates.longitude);
                    list.add(info);
                }
                // adding contact to contact list
                return list;
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("ERROR", "Couldn't get json from server.");
        }
        return list;
    }

    /////////////////////////////////School/////////////////////////////////

    public ArrayList<LinkedHashMap<String, Object>> JSONParserSchool(JSONObject obj) {
        ArrayList<LinkedHashMap<String, Object>> list = new ArrayList<>();
        LatLng coordinates;
        if (obj != null) {
            LinkedHashMap<String, Object> info;
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
                    String postalCode = c.getString("postal_code");
                    // adding each child node to HashMap key => value
                    info = new LinkedHashMap<>();
                    info.put("AmenitiesType", "School");
                    info.put("AmenitiesName", schoolName);
                    info.put("AmenitiesLat", 0.0);
                    info.put("AmenitiesLng", 0.0);
                    //Using GEOCODING
//                    coordinates = maps.getAmenitiesCoordinates(postalCode);
//                    info.put("AmenitiesLat", coordinates.latitude);
//                    info.put("AmenitiesLng", coordinates.longitude);
                    list.add(info);
                }
                return list;
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("ERROR", "Couldn't get json from server.");
        }
        return list;
    }

    /////////////////////////////////Parse Tax//////////////////////////////////

    public ArrayList<LinkedHashMap<String, String>> JSONParserTax(JSONObject obj) {
        ArrayList<LinkedHashMap<String, String>> list = new ArrayList<>();
        if (obj != null) {
            LinkedHashMap<String, String> info;
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
                    info = new LinkedHashMap<>();
                    info.put("typeOfProperty", typeOfProperty);
                    info.put("taxRate", taxRate);
                    info.put("annualValue", annualValue);
                    list.add(info);

                }
                return list;
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("ERROR", "Couldn't get json from server.");
        }
        return list;
    }

    public void writeAmenitiesToDB(ArrayList<LinkedHashMap<String, Object>> list) {
        DatabaseController db = DatabaseController.getInstance(context);
        for (LinkedHashMap<String, Object> hm : list) {
            db.writeAmenitiesData(hm);
        }

    }

    public void writeTaxToDB(ArrayList<LinkedHashMap<String, String>> infoList) {
        DatabaseController db = DatabaseController.getInstance(context);
        for (LinkedHashMap<String, String> hm : infoList) {
            db.writeTax(hm);
        }
    }


    public ArrayList<ArrayList<LatLng>> parseKML() {
        ArrayList<ArrayList<LatLng>> coOrds = new ArrayList<ArrayList<LatLng>>();

        try {
            StringBuilder buf = new StringBuilder();
            InputStream kml = context.getAssets().open("chas-clinics-kml.kml");
            //read file in buffer from file.
            BufferedReader in = new BufferedReader(new InputStreamReader(kml));
            String str;
            String buffer;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();
            String html = buf.toString();
            Document doc = Jsoup.parse(html, "", Parser.xmlParser());
            ArrayList<String> tracksString = new ArrayList<String>();
            for (Element e : doc.select("coordinates")) {
                tracksString.add(e.toString().replace("<coordinates>", "").replace("</coordinates>", "")); //erase front and end
            }

            for (int i = 0; i < tracksString.size(); i++) {
                ArrayList<LatLng> oneTrack = new ArrayList<LatLng>();
                ArrayList<String> oneTrackString = new ArrayList<String>(Arrays.asList(tracksString.get(i).split("\\s+")));
                for (int k = 1; k < oneTrackString.size(); k++) {
                    LatLng latLng = new LatLng(Double.parseDouble(oneTrackString.get(k).split(",")[0]), Double.parseDouble(oneTrackString.get(k).split(",")[1]));
                    oneTrack.add(latLng);
                }
                coOrds.add(oneTrack);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return coOrds;
    }


    public void print(ArrayList<LinkedHashMap<String, Object>> hashList) {
        System.out.println("-----------------Inside the hashmap-----------------");
        for (LinkedHashMap<String, Object> i : hashList) {
            for (String key : i.keySet()) {
                Log.d("hello", key);
                Log.d("hello", i.get(key).toString());
            }
        }

    }


}


//////////////////////////////////////////////
// TODO:KML  to parse:
// https://data.gov.sg/dataset/chas-clinics?resource_id=21dace06-c4d1-4128-9424-aba7668050dc
// https://geo.data.gov.sg/market-food-centre/2014/12/26/kml/market-food-centre.kml
// https://geo.data.gov.sg/g-mp08-act-mrt-stn-pt/2011/03/29/kml/g-mp08-act-mrt-stn-pt.zip