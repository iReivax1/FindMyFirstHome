package com.example.findmyfirsthome.Boundary;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

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


    public MapAPI() {

    }

    //Method to connect to html
    public String getHTTPData(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL); //create a URL object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //open the connection
            conn.setRequestMethod("GET"); //getting data so is Get method
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //binary (non-alphanumeric) data (or a significantly sized payload) to transmit, use multipart/form-data.
            // Otherwise, use application/x-www-form-urlencoded
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); //read the file
                while ((line = br.readLine()) != null) response += line;
            } else response = ""; //if http response code != 200 then have an empty response

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public LatLng getCoordinates(String name) {
        LatLng coord = new LatLng(0,0);
        coord = execute(name);
        return coord;
    }


    protected LatLng execute(String name) {
        LatLng coord = new LatLng(0,0);
        String response;
        try {
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + name + "SG&key=AIzaSyDMO5XX-YHL66_9hzc9cF73yfwMrK6lfNE";
            System.out.print(url);
            response = getHTTPData(url);
            Log.d("response", response);
            JSONObject jsonObject = new JSONObject(response); //ToDo Problem is here unable to get, change to use volley connection
            String lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
            String lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
            Double latitiude = Double.parseDouble(lat);
            Double longtitude = Double.parseDouble(lng);
            coord = new LatLng(latitiude, longtitude);
            return coord;
        } catch (Exception ex) {
             ex.printStackTrace();
        }
        return coord;
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

