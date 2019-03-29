package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.findmyfirsthome.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;


public class MapAPI{


    private GoogleMap mMap;
    Context context;
    public  MapAPI(){

    }
    //Controller will call this
    //self call GeoCooding
    //To write in database
    public LatLng getHDBCoordinates(String name){
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
            point = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return point;
    }


    //name can be anything, an address or a place name, not sure about postal code
    public LatLng getAmenitiesCoordinates(String name) {

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
            point = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return point;
    }
}
