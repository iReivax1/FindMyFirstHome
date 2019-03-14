package com.example.findmyfirsthome.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.Entity.HDBFlatType;
import com.example.findmyfirsthome.Entity.MapData;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;
import java.util.HashMap;

//Basically this is our DAO;
//Need to refactor this class to an interface class.
//have all controllers implement this class
public class DatabaseController extends SQLiteOpenHelper {


    //Change version if schema changed;
    public static final int DATABASE_VERSION = 1;

    //----------- TABLE COLUMNS -----------//
    public static final String ID = "ID";
    public static final String HDBDevelopmentName = "HDBDevelopmentName";
    public static final String HDBDevelopmentDescription = "HDBdevelopmentDescription";
    public static final String HDBDevelopmentLongitude = "Longitude";
    public static final String HDBDevelopmentLatitude = "Latitude";
    public static final String HDBFlatType = "FlatType";
    public static final String HDBFlatPrice = "HDBFlatPrice";
    public static final String AmenitiesName = "AmenitiesName";
    public static final String AmenitiesLongitude = "ALongitude";
    public static final String AmenitiesLatitude = "ALatitude";


    //----------- TABLE COLUMNS -----------//
    public static final String DATABASE_NAME = "FindMyFirstHome.db";
    private static final String TABLE_NAME = "HDB";

    //Draw the table
    private static final String SQL_HDB = "CREATE TABLE " + TABLE_NAME + " (" + ID+ "INTEGER PRIMARY KEY," + HDBDevelopmentName + " TEXT, "
            + HDBDevelopmentDescription + " TEXT, " + HDBDevelopmentLongitude + " REAL, " + HDBDevelopmentLatitude
            + " REAL, " + HDBFlatType + " INTEGER, " + HDBFlatPrice + "REAL, " + AmenitiesName
            + "TEXT, " + AmenitiesLongitude + "REAL, " + AmenitiesLatitude + "REAL" + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

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
        sqLiteDatabase.execSQL(SQL_HDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*Our Methods Below*/
    /*Because they can be long-running, be sure that you call getWritableDatabase() or
     getReadableDatabase()
     in a background thread,
     such as with AsyncTask or IntentService. */


    public void setHDB() {

    }


    public boolean writeHDBata(HDBDevelopment HDBD) {
        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HDBDevelopmentName, HDBD.getDevelopmentName());
        values.put(HDBDevelopmentDescription, HDBD.getDevelopmentDescription());
        LatLng HDBCoord = HDBD.getCoordinates();
        String HDBlat = Double.toString(HDBCoord.latitude);
        String HDBlon = Double.toString(HDBCoord.longitude);
        values.put(AmenitiesLatitude, HDBlat);
        values.put(AmenitiesLongitude, HDBlon);
        //Potential BUGGY AREA TODO: UPGRADE TO USE FLATDETAIL HASHMAP
        ArrayList<HDBFlatType> HDBFT = HDBD.getHdbFlatTypeList();
        for (HDBFlatType i : HDBFT) {
            String HDBFlatTypeStr = Integer.toString(i.getFlatType());
            String HDBFP = Double.toString(i.getPrice());
            values.put(HDBFlatType, HDBFlatTypeStr);
            values.put(HDBFlatPrice, HDBFP);
        }

        ArrayList<MapData> HDBAmenities = HDBD.getAmenities();
        for (MapData j : HDBAmenities) {
            String name = j.getAmenitiesName();
            values.put(AmenitiesName, name);
            LatLng ACoord = j.getCoordinates();
            String Alat = Double.toString(ACoord.latitude);
            String Alon = Double.toString(ACoord.longitude);
            values.put(AmenitiesLatitude, Alat);
            values.put(AmenitiesLongitude, Alon);
        }

        //The first argument is the table name.
        //The second argument tells the framework what to do if ContentValues is empty
        //Third argument is  content;
        // Insert the new row, returning the primary key value of the new row
        values.put(ID, numID);
        numID++;
        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }

     // TODO: GetData;

    public ArrayList<HDBDevelopment> getHDBData(){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                ID,
                HDBDevelopmentName,
                HDBDevelopmentDescription,
                HDBDevelopmentLongitude,
                HDBDevelopmentLatitude,
                HDBFlatType,
                HDBFlatPrice,
                AmenitiesName,
                AmenitiesLongitude,
                AmenitiesLatitude
        };



        // How you want the results sorted in the resulting Cursor
        //SELECT HDBFlatType, HDBFlatPrice FROM TABLE_NAME AS D, HDBFlatType as FT WHERE D.DBDEVELOPMENTName == FT.HDBDEvelopmentName

        String rawQuery = "SELECT * FROM "+ TABLE_NAME + "as D, " + HDBFlatType + "as FT WHERE D.HDBDevelopmentName = FT.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        HashMap<String, Object> flatTypeDetails = null;
        ArrayList<HDBFlatType> HDBFTList = new ArrayList<HDBFlatType>();

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
            HDBFTList.add(new HDBFlatType(flatTypeDetails));
        }
        cursor.close();

        //TODO: Return created objects by calling the creation method
        return null;

    }

    private HDBDevelopment createHDBDevelopmentObject(ArrayList<HashMap<String, Object>> HDBFTList, String HDBDevelopmentName, String HDBDevelopmentDescription,
                                                     boolean affordable, LatLng coordinates, ArrayList<MapData> amenities){
        HDBDevelopment HDBD =  new HDBDevelopment(HDBFTList, HDBDevelopmentName,  HDBDevelopmentDescription,
                false, coordinates, amenities);
        return HDBD;
    }

    public LatLng getHDBDevelopmentCoordinates(HDBDevelopment HDBD){
        return null;
    }



}


    /*Note: to destroy the data base use write this in the onDestroy in the activity class
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

*/