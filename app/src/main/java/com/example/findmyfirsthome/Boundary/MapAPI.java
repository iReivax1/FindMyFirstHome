package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.findmyfirsthome.Controller.MapsController;
import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapAPI extends FragmentActivity implements OnMapReadyCallback{


    private GoogleMap mMap;
    private MapsController MC = new MapsController();
    private String[] DevelopmentName;
    LatLng point;

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng Singapore = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(Singapore).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Singapore));
//        //     map.setLocationSource(mLocationSource);
////      map.setOnMapLongClickListener(mLocationSource);
////      map.setMyLocationEnabled(true);
    }

    public void setLocation(LatLng point){
        mMap.addMarker(new MarkerOptions().position(point).title("mark"));
    }

    public void drawCircle(LatLng HDBLocation){

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(HDBLocation)
                .radius(150000)
                .visible(false));
    }

    //Controller will call this
    //self call GeoCooding
    public LatLng getHDBCoordinates(String name){
        Context context = getApplicationContext();
        Geocoder gc = new Geocoder(context);
        List<Address> addresses = null;
        point = null;
        try {
            addresses = gc.getFromLocationName(name, 5);
            assert addresses != null; //make sure that addresss is not null
            Address location = addresses.get(0);
            point = new LatLng(location.getLatitude(), location.getLongitude() );
            for ( Address a : addresses )
                mMap.addMarker( new MarkerOptions().position( new LatLng( a.getLatitude(), a.getLongitude() ) ).title( "HDB" ) );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return point;
    }



}

