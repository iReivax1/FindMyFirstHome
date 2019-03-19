package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.findmyfirsthome.Controller.DevelopmentDetailControl;
import com.example.findmyfirsthome.Entity.AffordabilityReport;
import com.example.findmyfirsthome.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class DevelopmentDetailUI extends FragmentActivity implements OnMapReadyCallback{

    private static Context context;
    private DevelopmentDetailControl ddc;
    private GoogleMap mMap;
    private ArrayList<HashMap<String, Object>> HDBFlatTypeDetailsList;
    private ArrayList<ArrayList> amenitiesDetailsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.development_detail_ui);
    }

    //runs after onCreate()
    @Override
    protected void onStart()
    {
        super.onStart();

        //to get data passed from previous intent/activity/boundary
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        //to be used when combine work with the rest
        final String estateName = extras.getString("estateName");
        //String estateName = "Test @ Sembawang"; //temporary used for now before combining with others

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        //initialize controller
        //get specific estate/development entity object
        ddc = new DevelopmentDetailControl(estateName, this);


        ////////////////////////////////////////////////////////////////////////////////////////////////////
        //set content to show in the UI
        //set what development/estate name to display in the UI
        final TextView estateNameView = findViewById(R.id.text_estateName);
        estateNameView.setText(estateName);

        //set image of development/estate
        //final ImageView estateImage = findViewById(R.id.image_estatePic);
        //estateImage.setImageResource();

        //set description of development/estate
        //get from controller which get from database controller which get from database
        final TextView estateDescription = findViewById(R.id.text_estateDescription);
        estateDescription.setText(ddc.getDevelopmentDescription());


        //for Table of FlatType info
        final TableLayout tableLayOut = findViewById(R.id.table_developmentTable);

        HDBFlatTypeDetailsList = ddc.getTableContent();

        Object temp;

        for(HashMap<String, Object> HDBFlatTypeDetails : HDBFlatTypeDetailsList)
        {
            TextView tv = new TextView(this);
            //set all fields from HashMap
            temp = HDBFlatTypeDetails.get("flatType");
            if(temp == null)
                break;
            tv.setText((int)temp);

            TextView tv1 = new TextView(this);
            //set all fields from HashMap
            temp = HDBFlatTypeDetails.get("price");

            if(temp == null)
                break;
            //setText cannot set Double
            tv1.setText(String.valueOf((Double)temp));


            //create generation button in table last column
            Button generateReportButton = new Button(this);
            generateReportButton.setId((int)HDBFlatTypeDetails.get("flatType"));
            generateReportButton.setText("Generate");
            generateReportButton.setTextColor(Color.parseColor("#FFFFFF")); //set text to white color
            generateReportButton.setBackgroundColor(Color.parseColor("#0000FF"));   //set button to dark blue color
            generateReportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent generateAfReportIntent = new Intent(getApplicationContext(), AffordabilityReportUI.class);
                    generateAfReportIntent.putExtra("estateName", estateName);  //send the estate/development name
                    generateAfReportIntent.putExtra("FlatType", v.getId()); //send the FlatType
                    startActivity(generateAfReportIntent);
                }
            });


            //set all fields from HashMap
            temp = HDBFlatTypeDetails.get("affordability");

            //set FlatType and price content of that row to dimgrey color
            if((Boolean)temp == false)
            {
                tv.setTextColor(Color.parseColor("#696969"));
                tv1.setTextColor(Color.parseColor("#696969"));
            }

            //set each cell of the table into a row
            TableRow tr = new TableRow(this);
            tr.addView(tv);
            tr.addView(tv1);
            tr.addView(generateReportButton);

            //set row into table
            TableLayout tableLayout = findViewById(R.id.table_developmentTable);
            tableLayOut.addView(tr);
        }


        //for Map display
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_developmentDetails);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        amenitiesDetailsList = ddc.getAmenitiesDetailsList();

        //only plot if there is data received
        if(HDBFlatTypeDetailsList.size() != 0) {
            // get location of development location and add a marker to it
            LatLng developmentLoc = ddc.getDevelopmentLocation();
            mMap.addMarker(new MarkerOptions().position(developmentLoc).title(ddc.getDevelopmentName()));

            for(ArrayList amemity : amenitiesDetailsList) {
                LatLng nearBy = new LatLng(1.345734, 103.681283);
                mMap.addMarker(new MarkerOptions().position((LatLng) amemity.get(2))
                        .title((String) amemity.get(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }

            //move map focus to the location of the main marker
            mMap.moveCamera(CameraUpdateFactory.newLatLng(developmentLoc));
        }
    }

}
