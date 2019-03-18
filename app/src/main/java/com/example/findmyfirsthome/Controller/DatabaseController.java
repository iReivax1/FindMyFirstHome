package com.example.findmyfirsthome.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.findmyfirsthome.Boundary.MapAPI;
import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.Entity.HDBFlatType;
import com.example.findmyfirsthome.Entity.MapData;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;
import java.util.HashMap;

//Basically this is our DAO;
//Need to refactor this class to an interface class.
//have all controllers implement this class


//TODO: Redesign database, each enitity = 1 table
//TODO: add writeGrants, and getGrants
public class DatabaseController extends SQLiteOpenHelper {


    //Change version if schema changed;
    public static final int DATABASE_VERSION = 1;

    //----------- TABLE COLUMNS for HDBDevelopment -----------//
    public static final String ID = "ID";
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
    private static final String SQL_HDBDevelopment = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY," + HDBDevelopmentName + " TEXT, "
            + HDBDevelopmentDescription + " TEXT, " + HDBDevelopmentLongitude + " REAL, " + HDBDevelopmentLatitude
            + " REAL " + ")";
    
    public static final String SQL_FlatType = "CREATE TABLE " + TABLE_NAME2 + "(" +  HDBDevelopmentName + " TEXT PRIMARY KEY, " + HDBFlatType + " INTEGER, "
            + HDBFlatPrice + "REAL, " + "FOREIGN KEY (" + HDBDevelopmentName + ") REFERENCES " + TABLE_NAME + "(" + HDBDevelopmentName +  "))";

    public static final String SQL_Amenities = "CREATE TABLE " + TABLE_NAME3 + "(" +  AmenitiesName + " TEXT PRIMARY KEY, " + HDBDevelopmentName + " TEXT," + AmenitiesType + " TEXT, "
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


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //On creation of DBC the table SQL_HDB will be created
        sqLiteDatabase.execSQL(SQL_HDBDevelopment);
        sqLiteDatabase.execSQL(SQL_FlatType);
        sqLiteDatabase.execSQL(SQL_Amenities);
        sqLiteDatabase.execSQL(SQL_Grants);
    }

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


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*Our Methods Below*/
    /*Because they can be long-running, be sure that you call getWritableDatabase() or
     getReadableDatabase()
     in a background thread,
     such as with AsyncTask or IntentService. */




    public boolean writeHDBata(HDBDevelopment HDBD) {
        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HDBDevelopmentName, HDBD.getDevelopmentName());
        values.put(HDBDevelopmentDescription, HDBD.getDevelopmentDescription());
        String HDBlat = Double.toString(getHDBDevelopmentCoordinates(HDBDevelopmentName).latitude);
        String HDBlon = Double.toString(getHDBDevelopmentCoordinates(HDBDevelopmentName).longitude);
        values.put(HDBDevelopmentLatitude, HDBlat);
        values.put(HDBDevelopmentLongitude, HDBlon);

        writeHDBFlatTypeData(HDBD);
        writeAmenitiesData(HDBD);

        //The first argument is the table name.
        //The second argument tells the framework what to do if ContentValues is empty
        //Third argument is  content;
        // Insert the new row, returning the primary key value of the new row
        //put id number;
        values.put(ID, numID);
        numID++;
        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public void writeHDBFlatTypeData(HDBDevelopment HDBD){

        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        ArrayList<HDBFlatType> HDBFT = HDBD.getHdbFlatTypeList();
        for (HDBFlatType i : HDBFT) {
            String HDBFlatTypeStr = Integer.toString(i.getFlatType());
            String HDBFP = Double.toString(i.getPrice());
            values.put(HDBDevelopmentName, HDBD.getDevelopmentName());
            values.put(HDBFlatType, HDBFlatTypeStr);
            values.put(HDBFlatPrice, HDBFP);
        }

        long newRowId = db.insert(TABLE_NAME2, null, values);
        db.close();
    }

    public void writeAmenitiesData(HDBDevelopment HDBD){

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
        db.close();

    }



    public ArrayList<HDBDevelopment> readHDBData(){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                ID,
                HDBDevelopmentName,
                HDBDevelopmentDescription,
                HDBDevelopmentLongitude,
                HDBDevelopmentLatitude

        };

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
            String StringDevelopmentLongitude = cursor.getString(index);
            Double DevelopmentLongitude = Double.parseDouble(StringDevelopmentLongitude);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentLatitude");
            String StringDevelopmentLatitude = cursor.getString(index);
            Double DevelopmentLatitude = Double.parseDouble(StringDevelopmentLatitude);

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

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                ID,
                HDBDevelopmentName,
                HDBDevelopmentDescription,
                HDBDevelopmentLongitude,
                HDBDevelopmentLatitude

        };

        // How you want the results sorted in the resulting Cursor
        //Potential problem, potential solution query by ID.

        String rawQuery = "SELECT * FROM "+ TABLE_NAME + " as D, " + TABLE_NAME2 + "as FT WHERE D.HDBDevelopmentName = developmentName AND D.HDBDevelopmentName = FT.HDBDevelopmentName";

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
            String StringDevelopmentLongitude = cursor.getString(index);
            Double DevelopmentLongitude = Double.parseDouble(StringDevelopmentLongitude);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentLatitude");
            String StringDevelopmentLatitude = cursor.getString(index);
            Double DevelopmentLatitude = Double.parseDouble(StringDevelopmentLatitude);

            coord = new LatLng(DevelopmentLatitude, DevelopmentLongitude);
            mdList = readMapData(DevelopmentName);
            HDBFTList = readHDBFlatType(DevelopmentName);

        }

        cursor.close();

        //TODO: Return created objects by calling the creation method
        HDBD = createHDBDevelopmentObject(HDBFTList, DevelopmentName, DevelopmentDescription, false, coord, mdList);

        return HDBD;

    }

    private ArrayList<MapData> readMapData(String name){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                ID,
                AmenitiesName,
                AmenitiesType,
                AmenitiesLongitude,
                AmenitiesLatitude
        };


        String rawQuery = "SELECT AmenitiesName, AmenitiesLongitude, AmenitiesLatitude FROM "+ TABLE_NAME + " as D, " + HDBFlatType + " as FT WHERE name = FT.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        ArrayList<MapData> md = new ArrayList<MapData>();

        while(cursor.moveToNext() && cursor != null) {

            int index;


            index = cursor.getColumnIndexOrThrow("AmenitiesName");
            String AName = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("AmenitiesLongitude");
            String StringAmenitiesLongitude = cursor.getString(index);
            Double ALongitude = Double.parseDouble(StringAmenitiesLongitude);

            index = cursor.getColumnIndexOrThrow("AmenitiesLatitude");
            String StringAmenitiesLatitude = cursor.getString(index);
            Double ALatitude = Double.parseDouble(StringAmenitiesLatitude);

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

    private ArrayList<HashMap<String, Object>> readHDBFlatType(String name){
        assert getReadableDatabase() != null;
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                HDBDevelopmentName,
                HDBFlatType,
                HDBFlatPrice
        };

        HashMap<String, Object> flatTypeDetails = null;
        ArrayList<HashMap<String, Object>> HDBFlatTypedetailsList = new ArrayList<HashMap<String, Object>>();

        String rawQuery = "SELECT HDBFlatType, HDBFlatPrice FROM "+ TABLE_NAME2 + " as D" + " WHERE name = FT.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        while(cursor.moveToNext() && cursor != null) {

            int index;

            index = cursor.getColumnIndexOrThrow("HDBFlatPrice");
            String HDBFlatPrice = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("HDBFlatType");
            String HDBFlatType = cursor.getString(index);


            //add data to flatType
            flatTypeDetails = new HashMap<String, Object>();
            flatTypeDetails.put("price", Double.parseDouble(HDBFlatPrice));
            flatTypeDetails.put("flatType", Double.parseDouble(HDBFlatType));
            flatTypeDetails.put("affordability", false);
            HDBFlatTypedetailsList.add(flatTypeDetails);
        }

        assert HDBFlatTypedetailsList != null;
        cursor.close();

        return HDBFlatTypedetailsList;
    }

    private HDBDevelopment createHDBDevelopmentObject(ArrayList<HashMap<String, Object>> HDBFTList, String HDBDevelopmentName, String HDBDevelopmentDescription,
                                                     boolean affordable, LatLng coordinates, ArrayList<MapData> amenities){
        HDBDevelopment HDBD =  new HDBDevelopment(HDBFTList, HDBDevelopmentName,  HDBDevelopmentDescription,
                false, coordinates, amenities);
        return HDBD;
        }

    public LatLng getHDBDevelopmentCoordinates(String HDBDevelopmentName){
            MapAPI mapi = new MapAPI();
            LatLng coord = mapi.getHDBCoordinates(HDBDevelopmentName);

            return coord;
        }


}


    /*Note: to destroy the data base use write this in the onDestroy in the activity class
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
*/