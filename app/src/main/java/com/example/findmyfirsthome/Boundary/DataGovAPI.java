package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.findmyfirsthome.Controller.DatabaseController;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class DataGovAPI {

    // This is our requests queue to process our HTTP requests.
    String typeData;
    String childCareURL = "https://data.gov.sg/api/action/datastore_search?resource_id=4fc3fd79-64f2-4027-8d5b-ce0d7c279646&limit=";
    String marketURL = "https://data.gov.sg/api/action/datastore_search?resource_id=8f6bba57-19fc-4f36-8dcf-c0bda382364d&limit=";
    String schoolURL = "https://data.gov.sg/api/action/datastore_search?resource_id=ede26d32-01af-4228-b1ed-f05c45a1d8ee&limit=";
    String taxURL = "https://data.gov.sg/api/action/datastore_search?resource_id=bb6f5bf8-7d0b-4526-b020-b812ea7d7d89&limit=";
    String url1;
    String url2;
    String url3;
    String url4;
    // Each of these url will hold the full URL to get json object from respective website.
    Context context;
    ArrayList<LinkedHashMap<String, String>> taxList = new ArrayList<>();
    RequestQueue requestQueue;

    public DataGovAPI(Context cont) {
        this.context = cont;
    }


    public void execute() {
        // This setups up a new request queue which we will need to make HTTP requests
        getDataFromDataGov("childCare", 1);
        getDataFromDataGov("market", 1);
        getDataFromDataGov("school", 1);
//         getDataFromDataGov("tax",5);
        parseKML();


    }


    //limit is the limit number for the number of search queries > 1 please.
    private void getDataFromDataGov(String type, int lim) {

        JsonObjectRequest jsonObjReq = null;
        //this is the datagov limit
        this.typeData = type;
        String limit = Integer.toString(lim);
        //Check type to set url
        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        requestQueue = Volley.newRequestQueue(context);
        if (typeData.equals("childCare")) {
            this.url1 = this.childCareURL + limit;
            jsonObjReq = new JsonObjectRequest(Request.Method.GET, url1, (JSONObject) null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    JSONParserChildCare(response); //here is fine
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("test", "error");
                }
            });
        } else if (typeData.equals("market")) {
            this.url2 = this.marketURL + limit;
            jsonObjReq = new JsonObjectRequest(Request.Method.GET, url2, (JSONObject) null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    JSONParserMarket(response);
                    //print(marketList);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("test", "error");
                }
            });
        } else if (typeData.equals("school")) {
            this.url3 = this.schoolURL + limit;
            jsonObjReq = new JsonObjectRequest(Request.Method.GET, url3, (JSONObject) null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    JSONParserSchool(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("test", "error");
                }
            });
        } else if (typeData.equals("tax")) {
            this.url4 = this.taxURL + limit;
            jsonObjReq = new JsonObjectRequest(Request.Method.GET, url4, (JSONObject) null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    taxList = JSONParserTax(response);
                    writeTaxToDB(taxList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("test", "error");
                }
            });
        }

        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(jsonObjReq);
    }

    /////////////////////////////////ChildCare/////////////////////////////////

    public void JSONParserChildCare(JSONObject obj) {
        MapAPI maps = new MapAPI(context);
        if (obj != null) {
            try {
                JSONObject jsonObj = obj;
                //Log.d("test", obj.toString());
                // Getting JSON Array node
                JSONObject result = jsonObj.getJSONObject("result");
                JSONArray records = result.getJSONArray("records");
                Log.d("Error", Integer.toString(records.length()));
                // looping through all records
                //each jobj represents one amenity
                for (int i = 0; i < records.length(); i++) {
                    JSONObject jobj = records.getJSONObject(i);
                    String centre_name = jobj.getString("centre_name");
                    //get the centre name //coordinates will be geocode using center_name
                    //pass type and name to get coordinates
                    //writing into db done by maps api
                    maps.getCoordinates("ChildCare", centre_name);
                }
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("ERROR", "Couldn't get json from server.");
        }

    }

    /////////////////////////////////Market///////////////////////////////// Method same as childcare just different name

    public void JSONParserMarket(JSONObject obj) {
        MapAPI maps = new MapAPI(context);
        if (obj != null) {
            try {
                JSONObject jsonObj = obj;
                JSONObject result = jsonObj.getJSONObject("result");
                JSONArray records = result.getJSONArray("records");
                for (int i = 0; i < records.length(); i++) {
                    JSONObject jobj = records.getJSONObject(i);
                    String name_of_centre = jobj.getString("name_of_centre");
                    maps.getCoordinates("Market", name_of_centre);
                }
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("ERROR", "Couldn't get json from server.");
        }
    }

    /////////////////////////////////School/////////////////////////////////Method same as childcare just different name

    public void JSONParserSchool(JSONObject obj) {
        MapAPI maps = new MapAPI(context);
        if (obj != null) {
            try {
                JSONObject jsonObj = obj;
                JSONObject result = jsonObj.getJSONObject("result");
                JSONArray records = result.getJSONArray("records");
                for (int i = 0; i < records.length(); i++) {
                    JSONObject jobj = records.getJSONObject(i);
                    String schoolName = jobj.getString("school_name");
                    maps.getCoordinates("School", schoolName);
                }
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("ERROR", "Couldn't get json from server.");
        }
    }

    /////////////////////////////////Parse Tax//////////////////////////////////

    public ArrayList<LinkedHashMap<String, String>> JSONParserTax(JSONObject obj) {
        ArrayList<LinkedHashMap<String, String>> list = new ArrayList<>();
        if (obj != null) {
            LinkedHashMap<String, String> info;
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
                    info = new LinkedHashMap<>();
                    info.put("typeOfProperty", typeOfProperty);
                    info.put("taxRate", taxRate);
                    info.put("annualValue", annualValue);
                    list.add(info);

                }
                return list;
            } catch (final JSONException e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("ERROR", "Couldn't get json from server.");
        }
        return list;
    }


    public void writeTaxToDB(ArrayList<LinkedHashMap<String, String>> infoList) {
        DatabaseController db = DatabaseController.getInstance(context);
        for (LinkedHashMap<String, String> hm : infoList) {
            db.writeTax(hm);
        }
    }


    public void parseKML() {
        ArrayList<ArrayList<LatLng>> coOrds = new ArrayList<ArrayList<LatLng>>();

        try {
            StringBuilder buf = new StringBuilder();
            InputStream kml = context.getAssets().open("chas-clinics-kml.kml");
            //read file in buffer from file.
            BufferedReader in = new BufferedReader(new InputStreamReader(kml));
            String str = in.readLine();

            while (str != null) {
                buf.append(str);
                str = in.readLine();
            }
            in.close();
            String html = buf.toString();
            Document doc = Jsoup.parse(html, "", Parser.xmlParser());
            ArrayList<String> trackName = new ArrayList<>();
            ArrayList<String> trackCoord = new ArrayList<String>();
            for(Element element : doc.select("SimpleData[name='HCI_NAME']")){
                trackName.add(element.toString().replace("<SimpleData name=\"HCI_NAME\">","").replace("</SimpleData>",""));
            }
            for (Element e : doc.select("coordinates")) {
                trackCoord.add(e.toString().replace("<coordinates>", "").replace("</coordinates>", "")); //erase front and end
            }

            for (int i = 0; i < trackCoord.size(); i++) {
                LinkedHashMap<String,Object> hashed = new LinkedHashMap<>();
                String[] temp = trackCoord.get(i).split(",");
                Double[] coord = new Double[]{Double.parseDouble(temp[0]), Double.parseDouble(temp[1])};
                hashed.put("AmenitiesType","CLINIC");
                hashed.put("AmenitiesName",trackName.get(i));
                hashed.put("AmenitiesLng",coord[0]);
                hashed.put("AmenitiesLat",coord[1]);
                DatabaseController dbc = DatabaseController.getInstance(context);
                dbc.writeAmenitiesData(hashed);
                dbc.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void print(ArrayList<LinkedHashMap<String, Object>> hashList) {
        System.out.println("-----------------Inside the hashmap-----------------");
        for (LinkedHashMap<String, Object> i : hashList) {
            for (String key : i.keySet()) {
                Log.d("hello", key);
                Log.d("hello", i.get(key).toString());
            }
        }

    }


}


//////////////////////////////////////////////
// TODO:KML  to parse:
// https://data.gov.sg/dataset/chas-clinics?resource_id=21dace06-c4d1-4128-9424-aba7668050dc
// https://geo.data.gov.sg/market-food-centre/2014/12/26/kml/market-food-centre.kml
// https://geo.data.gov.sg/g-mp08-act-mrt-stn-pt/2011/03/29/kml/g-mp08-act-mrt-stn-pt.zip