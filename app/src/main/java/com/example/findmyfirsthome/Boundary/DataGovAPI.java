package com.example.findmyfirsthome.Boundary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.findmyfirsthome.R;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class DataGovAPI extends AppCompatActivity {

    EditText etGitHubUser; // This will be a reference to our GitHub username input.
    Button btnGetRepos;  // This is a reference to the "Get Repos" button.
    TextView tvRepoList;  // This will reference our repo list text box.
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    String baseUrl = "https://data.gov.sg/api/action/datastore_search?resource_id=4fc3fd79-64f2-4027-8d5b-ce0d7c279646&limit=";  // This is the API base URL (GitHub API)
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_gov_api);

        this.etGitHubUser = findViewById(R.id.et_github_user);  // Link our github user text box.
        this.btnGetRepos = findViewById(R.id.btn_get_repos);  // Link our click button.
        this.tvRepoList = findViewById(R.id.tv_repo_list);  // Link our repository list text output box.
        this.tvRepoList.setMovementMethod(new ScrollingMovementMethod());  // This makes our text box scrollable, for those big GitHub contributors with lots of repos :)

        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
    }

    private void clearRepoList() {
        // This will clear the repo list (set it as a blank string).
        this.tvRepoList.setText("");
    }

    private void addToRepoList(String address, String lastUpdated) {
        // This will add a new repo to our list.
        // It combines the repoName and lastUpdated strings together.
        // And then adds them followed by a new line (\n\n make two new lines).
        String strRow = address + " / " + lastUpdated;
        String currentText = tvRepoList.getText().toString();
        this.tvRepoList.setText(currentText + "\n\n" + strRow);
    }

    private void setRepoListText(String str) {
        // This is used for setting the text of our repo list box to a specific string.
        // We will use this to write a "No repos found" message if the user doens't have any.
        this.tvRepoList.setText(str);
    }

    private void getList(String limit) {
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).
        //this is the datagov limit
        this.url = this.baseUrl + limit;

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Check the length of our response to see if anything exist
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            // For each repo, add a new line to our repo list.
                            JSONObject jsonObj = response.getJSONObject(i);
                            System.out.println(jsonObj);
                            String repoName = jsonObj.get("name").toString();
                            String lastUpdated = jsonObj.get("updated_at").toString();
                            addToRepoList(repoName, lastUpdated);

                        } catch (JSONException e) {
                            // If there is an error then output this to the logs.
                            Log.e("Volley", "Invalid JSON Object.");
                        }

                    }
                } else {
                    // The user didn't have any repos.
                    setRepoListText("No repos found.");
                }

            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
                        setRepoListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                });
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);
    }

    public void getReposClicked(View v) {
        // Clear the repo list (so we have a fresh screen to add to)
        clearRepoList();
        // Call our getList() function that is defined above and pass in the
        // text which has been entered into the etGitHubUser text input field.
        getList(etGitHubUser.getText().toString());
    }

    public boolean writeAmenitiesCoord() {
        return false;
    }

    public boolean writeTaxInfo() {
        return false;
    }

    public boolean writeGrantsInfo() {
        return false;
    }

    public void query(String condition) {
        InputStream school = JSONconnector("https://data.gov.sg/api/action/datastore_search?resource_id=ede26d32-01af-4228-b1ed-f05c45a1d8ee&q=" + condition);
        InputStream pre_school = JSONconnector("https://data.gov.sg/api/action/datastore_search?resource_id=4fc3fd79-64f2-4027-8d5b-ce0d7c279646&limit=5");

        InputStream tax_rate = JSONconnector("https://data.gov.sg/api/action/datastore_search?resource_id=bb6f5bf8-7d0b-4526-b020-b812ea7d7d89&q=" + condition);
        //HDB confirm 4%; For owner-occupied HDB flats, you need not pay tax on the first $8,000 of the AV from 2014. The remaining AV will be taxed at the lowest tier of 4%.
    }

    public void JSONParser() {

    }

    public InputStream JSONconnector(String url) {
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection conn = null;
        try {
            conn = u.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = null;
        try {
            is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return is;
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