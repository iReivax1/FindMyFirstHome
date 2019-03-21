package com.example.findmyfirsthome.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.findmyfirsthome.Boundary.MapAPI;
import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.Entity.MapData;
import com.example.findmyfirsthome.Entity.UserData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.*;

//Basically this is our DAO;
//Need to refactor this class to an interface class.
//have all controllers implement this class


//TODO: Redesign database, each enitity = 1 table
//TODO: add writeGrants, and getGrants
public class DatabaseController extends SQLiteOpenHelper implements DataAccessInterfaceClass, BaseColumns {


    //Change version if schema changed;
    public static final int DATABASE_VERSION = 1;

    //----------- TABLE COLUMNS for ALL -----------//
    public static final String ID = "ID";
    //----------- TABLE COLUMNS for HDBDevelopment -----------//
    public static final String HDBDevelopmentName = "HDBDevelopmentName";
    public static final String HDBDevelopmentDescription = "HDBdevelopmentDescription";
    public static final String HDBDevelopmentLongitude = "Longitude";
    public static final String HDBDevelopmentLatitude = "Latitude";
    public static final String HDBDevelopmentImgURL = "ImgURL";

    //----------- TABLE COLUMNS for FlatType -----------//
    public static final String HDBFlatType = "HDBFlatType";
    public static final String HDBFlatPrice = "HDBFlatPrice";
    public static final String HDBFlatAffordability = "HDBAffordability";

    //----------- TABLE COLUMNS for Amenities -----------//
    public static final String AmenitiesName = "AmenitiesName";
    public static final String AmenitiesType = "AmenitiesType";
    public static final String AmenitiesAddress = "AmenitiesAddress";

    //----------- TABLE COLUMNS for Grants -----------//
    public static final String IncomeRequired = "IncomeRequired";
    public static final String GrantType = "GrantType";
    public static final String GrantAmount = "GrantAmount";


    //----------- TABLE COLUMNS for UserData -----------//
    //ID
    public static final String isMarried = "isMarried";
    public static final String isFirstTimeBuyer = "isFirstTimeBuyer";
    public static final String isSingaporean = "isSingapore"; //if true is singaporean
    public static final String age = "age";
    public static final String grossSalary = "grossSalary";

    public static final String isFirstTimeBuyerPartner = " isFirstTimeBuyerPartner";
    public static final String isSingaporeanPartner = "isSingaporeanPartner"; //if true is singaporean
    public static final String agePartner = "agePartner";
    public static final String grossSalaryPartner = "grossSalaryPartner";

    public static final String carLoan = "carLoan";
    public static final String creditLoan = "creditLoan";
    public static final String studyLoan = "studyLoan";
    public static final String otherCommitments = "otherCommitments";

    public static final String buyer1CPF = "buyer1CPF";
    public static final String buyer2CPF = "buyer2CPF";

    public static final String numberOfAdditionalHouseholdMembers = "numberOfAdditionalHouseholdMembers";

    //----------- TABLE COLUMNS for membersSalaryList -----------//
    //ID is foreign key and primary key
    public static final String membersSalaryList = "membersSalaryList";

    //----------- TABLE NAMES & DATABASE NAME -----------//
    public static final String DATABASE_NAME = "FindMyFirstHome.db";
    private static final String TABLE_NAME = "HDBDevelopment";
    private static final String TABLE_NAME2 = "FlatType";
    private static final String TABLE_NAME3 = "Amenities";
    private static final String TABLE_NAME4 = "Grants";
    private static final String TABLE_NAME5 = "UserData";
    private static final String TABLE_NAME6 = "membersSalaryList";

    //Draw the table
    private static final String SQL_HDBDevelopment = "CREATE TABLE " + TABLE_NAME + " (" + HDBDevelopmentName + " TEXT PRIMARY KEY, " + HDBDevelopmentDescription + " TEXT, " + HDBDevelopmentLongitude + " REAL, " + HDBDevelopmentLatitude + " REAL, " + HDBDevelopmentImgURL + " TEXT " + ");";


    public static final String SQL_FlatType = "CREATE TABLE " + TABLE_NAME2 + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+HDBDevelopmentName + " TEXT, " + HDBFlatType + " TEXT, " + HDBFlatPrice + " REAL, " + HDBFlatAffordability + " BOOLEAN, " + " FOREIGN KEY (" + HDBDevelopmentName + ") REFERENCES " + TABLE_NAME + "(" + HDBDevelopmentName +  "));";

    //public static final String SQL_Amenities = "CREATE TABLE " + TABLE_NAME3 + " (" +  AmenitiesName + " TEXT PRIMARY KEY, " +  AmenitiesType + " TEXT, " + AmenitiesLongitude + " REAL, " + AmenitiesLatitude + " REAL, " + "FOREIGN KEY (" + HDBDevelopmentName + ") REFERENCES " + TABLE_NAME + "(" + HDBDevelopmentName +  "));";

    public static final String SQL_Grants = "CREATE TABLE " + TABLE_NAME4 + " (" + IncomeRequired + " TEXT PRIMARY KEY, " + GrantType + " TEXT, " + GrantAmount +
            " REAL);";

    public static final String SQL_UserData = "CREATE TABLE " + TABLE_NAME5 + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + isMarried + " BOOLEAN, "
            + isFirstTimeBuyer + " BOOLEAN, " + isSingaporean + " BOOLEAN, " + age + " REAL, " + grossSalary + " REAL, " + isFirstTimeBuyerPartner + "BOOLEAN, " + isSingaporeanPartner + " BOOLEAN, " + agePartner + " REAL, " + grossSalaryPartner + " REAL, "
            + carLoan + " REAL, " + creditLoan + " REAL, " + studyLoan + " REAL, " + otherCommitments + " REAL, " + buyer1CPF + " REAL, " + buyer2CPF + " REAL, "
            + numberOfAdditionalHouseholdMembers + " REAL " + ")";


    public static final String SQL_membersSalaryList_ = "CREATE TABLE " + TABLE_NAME6 + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + membersSalaryList + " REAL, " + " FOREIGN KEY (" + ID + ") REFERENCES " + TABLE_NAME5 + "(" + ID + "));";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + TABLE_NAME2;
    private static final String SQL_DELETE_ENTRIES3 = "DROP TABLE IF EXISTS " + TABLE_NAME3;
    private static final String SQL_DELETE_ENTRIES4 = "DROP TABLE IF EXISTS " + TABLE_NAME4;
    private static final String SQL_DELETE_ENTRIES5 = "DROP TABLE IF EXISTS " + TABLE_NAME5;
    private static final String SQL_DELETE_ENTRIES6 = "DROP TABLE IF EXISTS " + TABLE_NAME6;

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
        sqLiteDatabase.execSQL(SQL_UserData);
        sqLiteDatabase.execSQL(SQL_membersSalaryList_);
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
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES5);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES6);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /////////////////////////////////////////////////////////////////////Write Functions/////////////////////////////////////////////////////////////////////
    /*Our Methods Below*/
    /*Because they can be long-running, be sure that you call getWritableDatabase() or
     getReadableDatabase()
     in a background thread,
     such as with AsyncTask or IntentService. */


    public boolean writeHDBData(String name, String descriptionText, String ImgUrl) {
        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(HDBDevelopmentName, name);
        values.put(HDBDevelopmentDescription, descriptionText);
        //String HDBlat = Double.toString(getHDBDevelopmentCoordinates(HDBDevelopmentName).latitude);
        //String HDBlon = Double.toString(getHDBDevelopmentCoordinates(HDBDevelopmentName).longitude);
        values.put(HDBDevelopmentLatitude, 0.0);
        values.put(HDBDevelopmentLongitude, 0.0);
        values.put(HDBDevelopmentImgURL, ImgUrl);

        System.out.println(values);
        long newRowId = db.insert(TABLE_NAME, null, values);
        System.out.println(newRowId);
        db.close();

        return true;
        //writeAmenitiesData(HDBDevelopmentName);
        //The first argument is the table name.
        //The second argument tells the framework what to do if ContentValues is empty
        //Third argument is  content;
        // Insert the new row, returning the primary key value of the new row
        //put id number;
        //assert checkWriteFlatData == true;
    }

    public void writeHDBFlatTypeData(String name, HashMap<String, Object> ListFlatType) {
        long newRowId;

        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HDBDevelopmentName, name);
        for (String key : ListFlatType.keySet()) {
            String obj;
            if (key.contains("price")) {
                obj = (ListFlatType.get(key).toString()).replace(",", "").substring(6);
                values.put(HDBFlatPrice, Double.valueOf(obj));
            } else if (key.contains("flatType")) {
                values.put(HDBFlatType, ListFlatType.get(key).toString());
            } else if (key.contains("affordability")) {
                values.put(HDBFlatAffordability, false);
            }
        }
        System.out.println(values);
        newRowId = db.insert(TABLE_NAME2, null, values);
        System.out.println(newRowId);
        db.close();
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


    public boolean writeHDBGrantData(String incomeReq, HashMap<String, Double> grantList) {

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

    public boolean writeProfileData(UserData ud) {

        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        ContentValues valueSalary = new ContentValues();

        values.put(isMarried, ud.isMarried());
        values.put(isFirstTimeBuyer, ud.isFirstTimeBuyer());
        values.put(isSingaporean, ud.isSingaporean());
        values.put(age, ud.getAge());
        values.put(grossSalary, ud.getGrossSalary());

        values.put(isFirstTimeBuyerPartner, ud.isFirstTimeBuyerPartner());
        values.put(isSingaporeanPartner, ud.isSingaporeanPartner());
        values.put(agePartner, ud.getAgePartner());
        values.put(grossSalaryPartner, ud.getGrossSalaryPartner());

        values.put(carLoan, ud.getCarLoan());
        values.put(creditLoan, ud.getCreditLoan());
        values.put(studyLoan, ud.getStudyLoan());
        values.put(otherCommitments, ud.getOtherCommitments());

        values.put(buyer1CPF, ud.getBuyer1CPF());
        values.put(buyer2CPF, ud.getBuyer2CPF());

        for (Double salary : ud.getMembersSalaryList()) {
            valueSalary.put(numberOfAdditionalHouseholdMembers, salary);
        }
        long newRowId = db.insert(TABLE_NAME5, null, values);
        long secondNewRowId = db.insert(TABLE_NAME6, null, valueSalary);
        db.close();

        return false;

    }

    public boolean writeAmenitiesData(ArrayList<HashMap<String, String>> infoList) {

        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        for (HashMap<String, String> i : infoList) {
            for (String key : i.keySet()) {
                if (key.equals("AmenitiesType")) {
                    values.put(AmenitiesType, i.get(key));
                } else if (key.equals("AmenitiesName")) {
                    values.put(AmenitiesName, i.get(key));
                } else {
                    values.put(AmenitiesAddress, i.get(key));
                }
            }
        }

        long newRowId = db.insert(TABLE_NAME3, null, values);
        db.close();
        return true;
    }

//////////////////////////////////////Read functions///////////////////////////////////////////////////////////////////////

    public ArrayList<HDBDevelopment> readHDBData() {
        SQLiteDatabase db = getReadableDatabase();

        // How you want the results sorted in the resulting Cursor
        //Potential problem, potential solution query by ID.

        String rawQuery = "SELECT * FROM " + TABLE_NAME + "as D, " + TABLE_NAME2 + "as FT WHERE D.HDBDevelopmentName = FT.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        HashMap<String, Object> flatTypeDetails = null;

        ArrayList<HDBDevelopment> HDBDList = null;
        ArrayList<MapData> mdList = null;
        ArrayList<HashMap<String, Object>> HDBFTList = null;
        LatLng coord = null;
        String DevelopmentName = "";
        String DevelopmentDescription = "";
        String DevelopmentImgURL = "";

        while (cursor.moveToNext() && cursor != null) {

            int index;

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentName");
            DevelopmentName = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentDescription");
            DevelopmentDescription = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentLongitude");
            Double DevelopmentLongitude = cursor.getDouble(index);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentLatitude");
            Double DevelopmentLatitude = cursor.getDouble(index);

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentImgURL");
            DevelopmentImgURL = cursor.getString(index);

            coord = new LatLng(DevelopmentLatitude, DevelopmentLongitude);
            mdList = readMapData(DevelopmentName);
            HDBFTList = readHDBFlatType(DevelopmentName);

        }

        cursor.close();

        //TODO: Return created objects by calling the creation method
        createHDBDevelopmentObject(HDBFTList, DevelopmentName, DevelopmentDescription, false, coord, mdList, DevelopmentImgURL);

        return HDBDList;

    }

    public HDBDevelopment readHDBData(String developmentName) {
        SQLiteDatabase db = getReadableDatabase();

        // How you want the results sorted in the resulting Cursor
        //Potential problem, potential solution query by ID.

        String rawQuery = "SELECT * FROM " + TABLE_NAME + " as D, " + TABLE_NAME2 + " as FT WHERE D.HDBDevelopmentName = '" + developmentName + "' AND D.HDBDevelopmentName = FT.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        HashMap<String, Object> flatTypeDetails = null;

        HDBDevelopment HDBD = null;
        ArrayList<MapData> mdList = null;
        ArrayList<HashMap<String, Object>> HDBFTList = null;
        LatLng coord = null;
        String DevelopmentName = developmentName;
        String DevelopmentDescription = "";
        String DevelopmentImgURL = "";
        while (cursor.moveToNext() && cursor != null) {

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

            index = cursor.getColumnIndexOrThrow("HDBDevelopmentImgURL");
            DevelopmentImgURL = cursor.getString(index);

            mdList = readMapData(DevelopmentName);
            HDBFTList = readHDBFlatType(DevelopmentName);

        }

        cursor.close();

        //if cursor is empty
        if (HDBFTList == null) HDBFTList = new ArrayList<>();
        if (mdList == null) mdList = new ArrayList<>();

        //TODO: Return created objects by calling the creation method
        HDBD = createHDBDevelopmentObject(HDBFTList, DevelopmentName, DevelopmentDescription, false, coord, mdList, DevelopmentImgURL);

        return HDBD;

    }

    public ArrayList<MapData> readMapData(String name) {
        SQLiteDatabase db = getReadableDatabase();

        String rawQuery = "SELECT AmenitiesName, AmenitiesType, AmenitiesLongitude, AmenitiesLatitude FROM " + TABLE_NAME + " as D, " + HDBFlatType + " as FT WHERE name = FT.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        ArrayList<MapData> md = new ArrayList<MapData>();

        while (cursor.moveToNext() && cursor != null) {

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
            md.add(new MapData(AName, AType, Acoord));

        }

        assert md != null;
        cursor.close();
        return md;
    }

    public ArrayList<HashMap<String, Object>> readHDBFlatType(String name) {
        assert getReadableDatabase() != null;
        SQLiteDatabase db = getReadableDatabase();

        HashMap<String, Object> flatTypeDetails = null;
        ArrayList<HashMap<String, Object>> HDBFlatTypedetailsList = new ArrayList<HashMap<String, Object>>();

        String rawQuery = "SELECT HDBFlatType, HDBFlatPrice FROM " + TABLE_NAME2 + " as D" + " WHERE name = FT.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        while (cursor.moveToNext() && cursor != null) {

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


    public LatLng readHDBDevelopmentCoordinates(String name) {
        assert getReadableDatabase() != null;
        SQLiteDatabase db = getReadableDatabase();

        LatLng coord = new LatLng(0, 0);

        String rawQuery = "SELECT HDBDevelopmentLatitude, HDBDevelopmentLongitude FROM " + TABLE_NAME + " as D" + " WHERE name = D.HDBDevelopmentName";

        Cursor cursor = db.rawQuery(rawQuery, null);

        while (cursor.moveToNext() && cursor != null) {

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

    public HashMap<String, Double> readHDBGrantData(String incomeReq) {

        assert getReadableDatabase() != null;
        SQLiteDatabase db = getReadableDatabase();
        HashMap<String, Double> grants = new HashMap<String, Double>();


        String rawQuery = "SELECT GrantType, GrantAmount FROM " + TABLE_NAME4 + " as D" + " WHERE incomeReq = D.incomeReq";

        Cursor cursor = db.rawQuery(rawQuery, null);

        while (cursor.moveToNext() && cursor != null) {

            int index;

            index = cursor.getColumnIndexOrThrow("GrantType");
            String type = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("GrantAmount");
            Double amount = cursor.getDouble(index);

            grants.put(type, amount);
        }

        return grants;
    }


    private HDBDevelopment createHDBDevelopmentObject(ArrayList<HashMap<String, Object>> HDBFTList, String HDBDevelopmentName, String HDBDevelopmentDescription, boolean affordable, LatLng coordinates, ArrayList<MapData> amenities, String ImgURL) {
        HDBDevelopment HDBD = new HDBDevelopment(HDBFTList, HDBDevelopmentName, HDBDevelopmentDescription, false, coordinates, amenities, ImgURL);
        return HDBD;
    }

    private LatLng getHDBDevelopmentCoordinates(String HDBDevelopmentName) {
        MapAPI mapi = new MapAPI();
        LatLng coord = mapi.getHDBCoordinates(HDBDevelopmentName);
        return coord;
    }
}
