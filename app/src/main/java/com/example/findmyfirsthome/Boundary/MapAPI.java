package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.CountDownTimer;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MapAPI {


    Context context;
    String url;
    RequestQueue requestQueue;
    LatLng coords = new LatLng(0, 0);
    DataGovAPI d;

    public MapAPI(Context context,  DataGovAPI d) {
        this.context = context;
        this.d = d;
    }


    public LatLng getCoords(){
        return this.coords;
    }

    public boolean getCoordinates(String name) {
        getJSON(name);
        while(coords.longitude == 0 && coords.latitude == 0)
            return false;

        return true;
    }

    public boolean getJSON(String name) {
        JsonObjectRequest jsonObjReq = null;
        requestQueue = Volley.newRequestQueue(context);
        this.url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + name + "SG&key=AIzaSyDMO5XX-YHL66_9hzc9cF73yfwMrK6lfNE123";
        System.out.print(url);
        jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                coords = parseMapJson(response);
                d.setCoord(coords);
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

