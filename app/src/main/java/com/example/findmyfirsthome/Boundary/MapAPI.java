package com.example.findmyfirsthome.Boundary;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.findmyfirsthome.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class MapAPI {


    Context context;
    String url;
    RequestQueue requestQueue;
    LatLng coords = new LatLng(0,0);

    public MapAPI(Context context) {
        this.context = context;
    }


    public boolean getHTTP(String name){
        JsonObjectRequest jsonObjReq = null;
        requestQueue = Volley.newRequestQueue(context);
        this.url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + name + "SG&key=AIzaSyDMO5XX-YHL66_9hzc9cF73yfwMrK6lfNE";
        System.out.print(url);
        jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                parseMapJson(response);
                setCoordinates(coords);
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


    public LatLng getCoordinates(String name) {
        boolean done;
        LatLng coord = new LatLng(0,0);
        done = getHTTP(name);
        if(done) {
            coord = this.coords;
        }
        return coord;
    }

    public void setCoordinates(LatLng coord){
        this.coords = coord;
    }


    protected void parseMapJson(JSONObject obj) {
        LatLng coord = new LatLng(0,0);
        String response;
        try {
            String lat = ((JSONArray) obj.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
            String lng = ((JSONArray) obj.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
            Double latitiude = Double.parseDouble(lat);
            Double longtitude = Double.parseDouble(lng);
            this.coords = new LatLng(latitiude, longtitude);
        } catch (Exception ex) {
             ex.printStackTrace();
        }
    }


    public LatLng getHDBCoordinates(String name) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng point = null;

        try {
            //will error if no address given
            address = coder.getFromLocationName(name, 1);
            if (address == null) {
                return null;
            }
            //get only the first address cuz i say only got 1 address
            Address location = address.get(0);
            point = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return point;
    }


}

