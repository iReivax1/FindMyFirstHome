package com.example.findmyfirsthome.Boundary;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.findmyfirsthome.Controller.DataAccessInterfaceClass;
import com.example.findmyfirsthome.Controller.DatabaseController;
import com.example.findmyfirsthome.Controller.DownloadFileManager;
import com.example.findmyfirsthome.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataGovAPI extends AppCompatActivity {

    EditText limit; // This will be a reference to our GitHub username input.
    EditText dataType;
    Button getData;  // This is a reference to the "Get Repos" button.
    TextView dataList;  // This will reference our repo list text box.
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    String typeData;
    String childCareURL = "https://data.gov.sg/api/action/datastore_search?resource_id=4fc3fd79-64f2-4027-8d5b-ce0d7c279646&limit=";  // This is the API base URL (GitHub API)
    String marketURL = "https://data.gov.sg/api/action/datastore_search?resource_id=8f6bba57-19fc-4f36-8dcf-c0bda382364d&limit=";
    String schoolURL = "https://data.gov.sg/api/action/datastore_search?resource_id=ede26d32-01af-4228-b1ed-f05c45a1d8ee&limit=";
    String taxURL = "https://data.gov.sg/api/action/datastore_search?resource_id=bb6f5bf8-7d0b-4526-b020-b812ea7d7d89&limit=10";
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.
    MapAPI maps = new MapAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_gov_api);

        this.dataType = findViewById(R.id.data_type);
        this.limit = findViewById(R.id.limit);  // Link our github user text box.
        this.getData = findViewById(R.id.get_data);  // Link our click button.
        this.dataList = findViewById(R.id.data_list);  // Link our repository list text output box.
        this.dataList.setMovementMethod(new ScrollingMovementMethod());  // This makes our text box scrollable, for those big GitHub contributors with lots of repos :)

        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.

        DownloadFileManager dfm = new DownloadFileManager();
        dfm.download("https://geo.data.gov.sg/market-food-centre/2014/12/26/kml/market-food-centre.kml");

    }

    private void clearRepoList() {
        // This will clear the list (set it as a blank string).
        this.dataList.setText("");
    }

    private void addToList(String type, String name, String address) {
        // This will add new data to our list.
        // (\n\n make two new lines).
        String currentText = dataList.getText().toString();
        this.dataList.setText(currentText + "\n\n" +type + " " + name + " " + address);
    }

    private void setRepoListText(String str) {
        // This is used for setting the text of our repo list box to a specific string.
        // We will use this to write a "No repos found" message if the user doens't have any.
        this.dataList.setText(str);
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
                setRepoListText("error");
            }
        });

        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(jsonObjReq);
    }

    public void getReposClicked(View v) {
        // Clear the repo list (so we have a fresh screen to add to)
        clearRepoList();
        // Call our getList() function that is defined above and pass in the
        // text which has been entered into the etGitHubUser text input field.
        getList(dataType.getText().toString(),limit.getText().toString());
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
                    info.put("AmenitiesLng", coordinates.longitude);

                    //For UI purpose, just for testing
                    addToList("childCare", centre_name, centre_address);
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
                    coordinates = maps.getAmenitiesCoordinates(location_of_centre);
                    info.put("AmenitiesLat", coordinates.latitude);
                    info.put("AmenitiesLng", coordinates.longitude);

                    //For UI purpose, just for testing
                    addToList("market", name_of_centre, location_of_centre);
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
                    String postalCode = c.getString("postal_code");

                    // adding each child node to HashMap key => value
                    info.put("AmenitiesType", "School");
                    info.put("AmenitiesName", schoolName);

                    //Using GEOCODING
                    coordinates = maps.getAmenitiesCoordinates(postalCode);
                    info.put("AmenitiesLat", coordinates.latitude);
                    info.put("AmenitiesLng", coordinates.longitude);
                    // adding contact to contact list
                    //infoList.add(info);

                    //For UI purpose, just for testing
                    addToList("school", schoolName, postalCode);
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
                    addToList(typeOfProperty, taxRate, annualValue);
                    // adding contact to contact list
                }
                writeTaxToDB(info);
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        }
        else {
            Log.e("ERROR", "Couldn't get json from server.");
        }

    }

    public void writeAmenitiesToDB( HashMap<String, Object> list){
        DataAccessInterfaceClass db = new DatabaseController(this.getApplicationContext());
        db.writeAmenitiesData(list);
    }

    public void writeTaxToDB(HashMap<String, String> info){
        // TODO: write tax list to DB
    }


    public void KMLConnector() {

    }

}


//////////////////////////////////////////////
// KML  to parse:
// https://data.gov.sg/dataset/pre-schools-location
// https://data.gov.sg/dataset/chas-clinics?resource_id=21dace06-c4d1-4128-9424-aba7668050dc
// https://geo.data.gov.sg/market-food-centre/2014/12/26/kml/market-food-centre.kml
// https://geo.data.gov.sg/g-mp08-act-mrt-stn-pt/2011/03/29/kml/g-mp08-act-mrt-stn-pt.zip