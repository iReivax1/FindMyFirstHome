package com.example.findmyfirsthome.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.findmyfirsthome.Presenter.DataAccessFactory;
import com.example.findmyfirsthome.Interface.DataAccessInterfaceClass;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedHashMap;


public class MapAPI {


    Context context;
    String url;
    RequestQueue requestQueue;
    LatLng coords = new LatLng(0, 0);
    LinkedHashMap<String, Object> amenities;


    public MapAPI(Context context) {
        this.context = context;
    }

    public void getCoordinates(String type, String name) {
        amenities = new LinkedHashMap<String, Object>();
        amenities.put("AmenitiesType", type);
        amenities.put("AmenitiesName", name);
        getJSON(name);
    }

    public boolean getHDBCoord(final String HDBDevelopmentName, final String descriptionText,final String ImgURL, final Boolean affordable) {
        JsonObjectRequest jsonObjReq = null;
        requestQueue = Volley.newRequestQueue(context);
        this.url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + HDBDevelopmentName + "SG&key=AIzaSyC0ENVSN8x7-3OvvSQjLckO_j7Jxn18JYM";
        System.out.print(url);
        jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                coords = parseMapJson(response);
                if(HDBDevelopmentName.contains("Fernvale")){
                    writeHDBToDB(HDBDevelopmentName, descriptionText, ImgURL, affordable, 1.398122, 103.876214);
                }else{
                    writeHDBToDB(HDBDevelopmentName, descriptionText, ImgURL, affordable, coords.latitude, coords.longitude);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", "error");
            }
        });
        requestQueue.add(jsonObjReq);
        return true;
    }

    public boolean getJSON(String name) {
        JsonObjectRequest jsonObjReq = null;
        requestQueue = Volley.newRequestQueue(context);
        this.url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + name + "SG&key=AIzaSyC0ENVSN8x7-3OvvSQjLckO_j7Jxn18JYM";
        System.out.print(url);
        jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                coords = parseMapJson(response);
                amenities.put("AmenitiesLat", coords.latitude);
                amenities.put("AmenitiesLng", coords.longitude);
                writeAmenitiesToDB(amenities);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", "error");
            }
        });
        requestQueue.add(jsonObjReq);
        return true;
    }

    //Parse JSON objects received from GoogleMaps API via get method
    protected LatLng parseMapJson(JSONObject obj) {
        String response;
        try {
            String lat = ((JSONArray) obj.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
            String lng = ((JSONArray) obj.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
            Double latitiude = Double.parseDouble(lat);
            Double longtitude = Double.parseDouble(lng);
            LatLng coord = new LatLng(latitiude, longtitude);
            this.coords = coord;
            return coord;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new LatLng(0, 0);
    }

    //write to the database

    public void writeAmenitiesToDB(LinkedHashMap<String, Object> oneAmenity) {
        DataAccessInterfaceClass db = DataAccessFactory.getDatabaseCtrlInstance(context);
        db.writeAmenitiesData(oneAmenity);
    }

    public void writeHDBToDB(String HDBDevelopmentName, String descriptionText,String ImgURL, Boolean affordable, Double lat, Double lng){
        DataAccessInterfaceClass db = DataAccessFactory.getDatabaseCtrlInstance(context);
        db.writeHDBData(HDBDevelopmentName,descriptionText,ImgURL, affordable, lat , lng);
    }
}

