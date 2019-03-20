package com.example.findmyfirsthome.Boundary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.toolbox.JsonObjectRequest;
import com.example.findmyfirsthome.R;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.Volley;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class DataGovAPI extends AppCompatActivity {

    EditText limit; // This will be a reference to our GitHub username input.
    Button getData;  // This is a reference to the "Get Repos" button.
    TextView dataList;  // This will reference our repo list text box.
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    String baseUrl = "https://data.gov.sg/api/action/datastore_search?resource_id=4fc3fd79-64f2-4027-8d5b-ce0d7c279646&limit=";  // This is the API base URL (GitHub API)
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.
    ArrayList<HashMap<String, String>> infoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_gov_api);


        this.limit = findViewById(R.id.limit);  // Link our github user text box.
        this.getData = findViewById(R.id.get_data);  // Link our click button.
        this.dataList = findViewById(R.id.data_list);  // Link our repository list text output box.
        this.dataList.setMovementMethod(new ScrollingMovementMethod());  // This makes our text box scrollable, for those big GitHub contributors with lots of repos :)
        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
    }

    private void clearRepoList() {
        // This will clear the repo list (set it as a blank string).
        this.dataList.setText("");
    }

    private void addToList(String type, String name, String address) {
        // This will add new data to our list.
        // (\n\n make two new lines).
        String currentText = dataList.getText().toString();
        this.dataList.setText(currentText + "\n\n" + type + name + address);
    }

    private void setRepoListText(String str) {
        // This is used for setting the text of our repo list box to a specific string.
        // We will use this to write a "No repos found" message if the user doens't have any.
        this.dataList.setText(str);
    }

    //limit is the limit number for the number of search queries > 1 please.
    private void getList(String limit) {
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).
        //this is the datagov limit
        this.url = this.baseUrl + limit;

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                JSONParserChildCare(response);
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
        getList(limit.getText().toString());
    }

    public boolean writeAmenitiesCoord(String amenitiesType, String Address) {
        return false;
    }


//    public void query(String condition) {
//      ("https://data.gov.sg/api/action/datastore_search?resource_id=ede26d32-01af-4228-b1ed-f05c45a1d8ee&q=" + condition);
//        ("https://data.gov.sg/api/action/datastore_search?resource_id=4fc3fd79-64f2-4027-8d5b-ce0d7c279646&limit=5");
//
//        ("https://data.gov.sg/api/action/datastore_search?resource_id=bb6f5bf8-7d0b-4526-b020-b812ea7d7d89&q=" + condition);
//        //HDB confirm 4%; For owner-occupied HDB flats, you need not pay tax on the first $8,000 of the AV from 2014. The remaining AV will be taxed at the lowest tier of 4%.
//    }

    public void JSONParserChildCare(JSONObject obj) {
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

                    // tmp hash map for single contact
                    HashMap<String, String> info = new HashMap<>();

                    // adding each child node to HashMap key => value
                    info.put("id", centre_name);
                    info.put("name", centre_address);
                    addToList("Childcare : ", centre_name, centre_address);
                    Log.d("test", centre_name);
                    Log.d("test", centre_address);

                    // adding contact to contact list
                    infoList.add(info);
                }
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
                }
            }
        else {
            Log.e("ERROR", "Couldn't get json from server.");
        }

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