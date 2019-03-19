package com.example.findmyfirsthome.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.findmyfirsthome.Boundary.MapAPI;
import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.Entity.MapData;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;
import java.util.HashMap;

//Basically this is our DAO;
//Need to refactor this class to an interface class.
//have all controllers implement this class


//TODO: Redesign database, each enitity = 1 table
//TODO: add writeGrants, and getGrants
public class DatabaseController extends SQLiteOpenHelper implements  DataAccessInterfaceClass {


    //Change version if schema changed;
    public static final int DATABASE_VERSION = 1;

    //----------- TABLE COLUMNS for HDBDevelopment -----------//
    public static final String HDBDevelopmentName = "HDBDevelopmentName";
    public static final String HDBDevelopmentDescription = "HDBdevelopmentDescription";
    public static final String HDBDevelopmentLongitude = "Longitude";
    public static final String HDBDevelopmentLatitude = "Latitude";

    //----------- TABLE COLUMNS for FlatType -----------//
    public static final String HDBFlatType = "FlatType";
    public static final String HDBFlatPrice = "HDBFlatPrice";

    //----------- TABLE COLUMNS for Amenities -----------//
    public static final String AmenitiesName = "AmenitiesName";
    public static final String AmenitiesType = "AmenitiesType";
    public static final String AmenitiesLongitude = "ALongitude";
    public static final String AmenitiesLatitude = "ALatitude";

    //----------- TABLE COLUMNS for Grants -----------//
    public static final String IncomeRequired = "IncomeRequired";
    public static final String GrantType = "GrantType";
    public static final String GrantAmount = "GrantAmount";

    //----------- TABLE NAMES & DATABASE NAME -----------//
    public static final String DATABASE_NAME = "FindMyFirstHome.db";
    private static final String TABLE_NAME = "HDBDevelopment";
    private static final String TABLE_NAME2 = "FlatType";
    private static final String TABLE_NAME3 = "Amenities";
    private static final String TABLE_NAME4 = "Grants";

    //Draw the table
    private static final String SQL_HDBDevelopment = "CREATE TABLE " + TABLE_NAME + " ("  + HDBDevelopmentName + " TEXT PRIMARY KEY, "
            + HDBDevelopmentDescription + " TEXT, " + HDBDevelopmentLongitude + " REAL, " + HDBDevelopmentLatitude
            + " REAL " + ")";

    public static final String SQL_FlatType = "CREATE TABLE " + TABLE_NAME2 + "(" +  HDBDevelopmentName + " TEXT PRIMARY KEY, " + HDBFlatType + " REAL, "
            + HDBFlatPrice + "REAL, " + "FOREIGN KEY (" + HDBDevelopmentName + ") REFERENCES " + TABLE_NAME + "(" + HDBDevelopmentName +  "))";

    public static final String SQL_Amenities = "CREATE TABLE " + TABLE_NAME3 + "(" +  AmenitiesName + " TEXT PRIMARY KEY, " +  AmenitiesType + " TEXT, "
            + AmenitiesLongitude + " REAL, " + AmenitiesLatitude + " REAL, " + "FOREIGN KEY (" + HDBDevelopmentName + ") REFERENCES " + TABLE_NAME + "(" + HDBDevelopmentName +  "))";

    public static final String SQL_Grants = "CREATE TABLE " + TABLE_NAME4 + "(" + IncomeRequired + " TEXT PRIMARY KEY, " + GrantType + " TEXT, " + GrantAmount +
            " REAL)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + TABLE_NAME2;
    private static final String SQL_DELETE_ENTRIES3 = "DROP TABLE IF EXISTS " + TABLE_NAME3;
    private static final String SQL_DELETE_ENTRIES4 = "DROP TABLE IF EXISTS " + TABLE_NAME4;

    private static int numID = 0;
    //use this to create the databaseContoller to write and get data; Like so;
    //DatabaseController dbC = new DatabaseController(getContext()); check out mapsControlelr
    //getContext() - Returns the context view only current running activity.
    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create all the tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //On creation of DBC the table SQL_HDB will be created
        sqLiteDatabase.execSQL(SQL_HDBDevelopment);
        sqLiteDatabase.execSQL(SQL_FlatType);
        sqLiteDatabase.execSQL(SQL_Amenities);
        sqLiteDatabase.execSQL(SQL_Grants);
    }

    //If Database version is difference, delete all current entries and re-create new DBs
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES2);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES3);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES4);
        onCreate(sqLiteDatabase);
    }


    /////////////////////////////////////////////////////////////////////Write Functions/////////////////////////////////////////////////////////////////////
    /*Our Methods Below*/
    /*Because they can be long-running, be sure that you call getWritableDatabase() or
     getReadableDatabase()
     in a background thread,
     such as with AsyncTask or IntentService. */




    public boolean writeHDBData(String name, ArrayList<HashMap<String, Object>>ListFlatTypePrice, String descriptionText) {
        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        boolean checkWriteFlatData = false;
        values.put(HDBDevelopmentName, name);
        values.put(HDBDevelopmentDescription, descriptionText);
        //String HDBlat = Double.toString(getHDBDevelopmentCoordinates(HDBDevelopmentName).latitude);
        //String HDBlon = Double.toString(getHDBDevelopmentCoordinates(HDBDevelopmentName).longitude);
        values.put(HDBDevelopmentLatitude, 0.0);
        values.put(HDBDevelopmentLongitude, 0.0);

        for(HashMap<String, Object> i : ListFlatTypePrice){
            writeHDBFlatTypeData(HDBDevelopmentName, i);
        }

        //writeAmenitiesData(HDBDevelopmentName);

        //The first argument is the table name.
        //The second argument tells the framework what to do if ContentValues is empty
        //Third argument is  content;
        // Insert the new row, returning the primary key value of the new row
        //put id number;
        //assert checkWriteFlatData == true;
        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public boolean writeHDBFlatTypeData(String name, HashMap<String, Object> HMFlatType){

        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();


        for (String key : HMFlatType.keySet()) {
            String HDBFlatTypeStr = key;
            Double HDBFP = (Double)HMFlatType.get(key);
            values.put(HDBDevelopmentName, name);
            values.put(HDBFlatType, HDBFlatTypeStr);
            values.put(HDBFlatPrice, HDBFP);
        }

        long newRowId = db.insert(TABLE_NAME2, null, values);
        return true;
    }


    /*
    //where to get amaneities
    public void writeAmenitiesData(String name){

        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        ArrayList<MapData> HDBAmenities = HDBD.getAmenities();
        for (MapData j : HDBAmenities) {
            values.put(AmenitiesName, j.getAmenitiesName());
            values.put(AmenitiesType, j.getAmenityType());
            LatLng ACoord = j.getCoordinates();
            String Alat = Double.toString(ACoord.latitude);
            String Alon = Double.toString(ACoord.longitude);
            values.put(AmenitiesLatitude, Alat);
            values.put(AmenitiesLongitude, Alon);
        }

        long newRowId = db.insert(TABLE_NAME3, null, values);


    }
    */


    public boolean writeHDBGrantData(String incomeReq, HashMap<String, Double> grantList){

        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(IncomeRequired, incomeReq);
        for (String key : grantList.keySet()) {
            String grantType = key;
            Double grantAmount = grantList.get(key);
            values.put(GrantType, grantType);
            values.put(GrantAmount, grantAmount);
        }
        long newRowId = db.insert(TABLE_NAME4, null, values);
        db.close();
        return true;
    }

//////////////////////////////////////Read functions///////////////////////////////////////////////////////////////////////

    public ArrayList<HDBDevelopment> readHDBData(){
        SQLiteDatabase db = getReadableDatabase();

        // How you want the results sorted in the resulting Cursor
        //Potential problem, potential solution query by ID.

        String rawQuery = "SELECT * FROM "+ TABLE_NAME + "as D, " + TABLE_NAME2 + "as FT WHERE D.HDBDevelopmentName = FT.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        HashMap<String, Object> flatTypeDetails = null;

        ArrayList<HDBDevelopment> HDBDList = null;
        ArrayList<MapData> mdList = null;
        ArrayList<HashMap<String, Object>> HDBFTList = null;
        LatLng coord = null;
        String DevelopmentName = "";
        String DevelopmentDescription = "";
        while(cursor.moveToNext() && cursor != null) {

            int index;

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentName");
            DevelopmentName = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentDescription");
            DevelopmentDescription = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentLongitude");
            Double DevelopmentLongitude = cursor.getDouble(index);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentLatitude");
            Double DevelopmentLatitude = cursor.getDouble(index);

            coord = new LatLng(DevelopmentLatitude, DevelopmentLongitude);
            mdList = readMapData(DevelopmentName);
            HDBFTList = readHDBFlatType(DevelopmentName);

        }

        cursor.close();

        //TODO: Return created objects by calling the creation method
        createHDBDevelopmentObject(HDBFTList, DevelopmentName, DevelopmentDescription, false, coord, mdList);

        return HDBDList;

    }

    public HDBDevelopment readHDBData(String developmentName){
        SQLiteDatabase db = getReadableDatabase();

        // How you want the results sorted in the resulting Cursor
        //Potential problem, potential solution query by ID.

        String rawQuery = "SELECT * FROM " + TABLE_NAME + " as D, " + TABLE_NAME2 + " as FT WHERE D.HDBDevelopmentName = '" + developmentName
                + "' AND D.HDBDevelopmentName = FT.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        HashMap<String, Object> flatTypeDetails = null;

        HDBDevelopment HDBD = null;
        ArrayList<MapData> mdList = null;
        ArrayList<HashMap<String, Object>> HDBFTList = null;
        LatLng coord = null;
        String DevelopmentName = developmentName;
        String DevelopmentDescription = "";
        while(cursor.moveToNext() && cursor != null) {

            int index;

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentName");
            DevelopmentName = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentDescription");
            DevelopmentDescription = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentLongitude");
            Double DevelopmentLongitude = cursor.getDouble(index);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentLatitude");
            Double DevelopmentLatitude = cursor.getDouble(index);

            coord = new LatLng(DevelopmentLatitude, DevelopmentLongitude);
            mdList = readMapData(DevelopmentName);
            HDBFTList = readHDBFlatType(DevelopmentName);

        }

        cursor.close();

        //if cursor is empty
        if(HDBFTList == null)
            HDBFTList = new ArrayList<>();
        if(mdList == null)
            mdList = new ArrayList<>();

        //TODO: Return created objects by calling the creation method
        HDBD = createHDBDevelopmentObject(HDBFTList, DevelopmentName, DevelopmentDescription, false, coord, mdList);

        return HDBD;

    }

    public ArrayList<MapData> readMapData(String name){
        SQLiteDatabase db = getReadableDatabase();

        String rawQuery = "SELECT AmenitiesName, AmenitiesType, AmenitiesLongitude, AmenitiesLatitude FROM "+ TABLE_NAME + " as D, " + HDBFlatType + " as FT WHERE name = FT.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        ArrayList<MapData> md = new ArrayList<MapData>();

        while(cursor.moveToNext() && cursor != null) {

            int index;


            index = cursor.getColumnIndexOrThrow("AmenitiesName");
            String AName = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("AmenitiesLongitude");
            Double ALongitude = cursor.getDouble(index);

            index = cursor.getColumnIndexOrThrow("AmenitiesLatitude");

            Double ALatitude = cursor.getDouble(index);

            index = cursor.getColumnIndexOrThrow("AmenitiesType");
            String AType = cursor.getString(index);

            LatLng Acoord = new LatLng(ALatitude, ALongitude);


            //add data to mapData arrayList
            md.add(new MapData(AName,AType,Acoord));

        }

        assert md != null;
        cursor.close();
        return md;
    }

    public ArrayList<HashMap<String, Object>> readHDBFlatType(String name){
        assert getReadableDatabase() != null;
        SQLiteDatabase db = getReadableDatabase();

        HashMap<String, Object> flatTypeDetails = null;
        ArrayList<HashMap<String, Object>> HDBFlatTypedetailsList = new ArrayList<HashMap<String, Object>>();

        String rawQuery = "SELECT HDBFlatType, HDBFlatPrice FROM "+ TABLE_NAME2 + " as D" + " WHERE name = FT.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        while(cursor.moveToNext() && cursor != null) {

            int index;

            index = cursor.getColumnIndexOrThrow("HDBFlatPrice");
            Double HDBFlatPrice = cursor.getDouble(index);

            index = cursor.getColumnIndexOrThrow("HDBFlatType");
            Integer HDBFlatType = cursor.getInt(index);


            //add data to flatType
            flatTypeDetails = new HashMap<String, Object>();
            flatTypeDetails.put("price", HDBFlatPrice);
            flatTypeDetails.put("flatType", HDBFlatType);
            flatTypeDetails.put("affordability", false);
            HDBFlatTypedetailsList.add(flatTypeDetails);
        }

        assert HDBFlatTypedetailsList != null;
        cursor.close();

        return HDBFlatTypedetailsList;
    }


    public LatLng readHDBDevelopmentCoordinates(String name){
        assert getReadableDatabase() != null;
        SQLiteDatabase db = getReadableDatabase();

        LatLng coord = new LatLng(0,0);

        String rawQuery = "SELECT HDBDevelopmentLatitude, HDBDevelopmentLongitude FROM "+ TABLE_NAME + " as D" + " WHERE name = D.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        while(cursor.moveToNext() && cursor != null) {

            int index;

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentLatitude");
            Double lat = cursor.getDouble(index);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentLongitude");
            Double lng = cursor.getDouble(index);
            coord = new LatLng(lat, lng);
        }

        assert coord != null;
        cursor.close();

        return coord;

    }

    public HashMap<String, Double> readHDBGrantData(String incomeReq){

        assert getReadableDatabase() != null;
        SQLiteDatabase db = getReadableDatabase();
        HashMap<String, Double> grants = new HashMap<String, Double>();


        String rawQuery = "SELECT GrantType, GrantAmount FROM "+ TABLE_NAME4 + " as D" + " WHERE incomeReq = D.incomeReq";

        Cursor cursor = db.rawQuery(rawQuery, null);

        while(cursor.moveToNext() && cursor != null) {

            int index;

            index = cursor.getColumnIndexOrThrow("GrantType");
            String type =cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("GrantAmount");
            Double amount = cursor.getDouble(index);

            grants.put(type, amount);
        }

        return grants;
    }


    private HDBDevelopment createHDBDevelopmentObject(ArrayList<HashMap<String, Object>> HDBFTList, String HDBDevelopmentName, String HDBDevelopmentDescription,
                                                     boolean affordable, LatLng coordinates, ArrayList<MapData> amenities){
        HDBDevelopment HDBD =  new HDBDevelopment(HDBFTList, HDBDevelopmentName,  HDBDevelopmentDescription,
                false, coordinates, amenities);
        return HDBD;
        }

    private LatLng getHDBDevelopmentCoordinates(String HDBDevelopmentName){
            MapAPI mapi = new MapAPI();
            LatLng coord = mapi.getHDBCoordinates(HDBDevelopmentName);
            return coord;
    }




}