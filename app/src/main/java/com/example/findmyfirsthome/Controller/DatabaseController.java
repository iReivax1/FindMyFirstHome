package com.example.findmyfirsthome.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.findmyfirsthome.Boundary.MapAPI;
import com.example.findmyfirsthome.Entity.CalculatedProfile;
import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.Entity.MapData;
import com.example.findmyfirsthome.Entity.UserData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

//Basically this is our DAO;
//Need to refactor this class to an interface class.
//have all controllers implement this class


//TODO: add writeGrants, and getGrants
public class DatabaseController extends SQLiteOpenHelper implements DataAccessInterfaceClass, BaseColumns {


    //Change version if schema changed;
    public static final int DATABASE_VERSION = 3;

    //----------- TABLE COLUMNS for ALL -----------//
    public static final String ID = "ID";
    //----------- TABLE COLUMNS for HDBDevelopment -----------//
    public static final String HDBDevelopmentName = "HDBDevelopmentName";
    public static final String HDBDevelopmentDescription = "HDBdevelopmentDescription";
    public static final String HDBDevelopmentLongitude = "Longitude";
    public static final String HDBDevelopmentLatitude = "Latitude";
    public static final String HDBDevelopmentImgURL = "ImgURL";
    public static final String HDBDevelopmentAffordability = "HDBAffordability";

    //----------- TABLE COLUMNS for FlatType -----------//
    public static final String HDBFlatType = "HDBFlatType";
    public static final String HDBFlatPrice = "HDBFlatPrice";
    public static final String HDBFlatAffordability = "HDBAffordability";

    //----------- TABLE COLUMNS for Amenities -----------//
    public static final String AmenitiesName = "AmenitiesName";
    public static final String AmenitiesType = "AmenitiesType";
    public static final String AmenitiesLongitude = "AmenitiesLongitude";
    public static final String AmenitiesLatitude = "AmenitiesLatitude";

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

    public static final String isFirstTimeBuyerPartner = "isFirstTimeBuyerPartner";
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

    //----------- TABLE COLUMNS for taxRate -----------//
    public static final String typeOfProperty = "typeOfProperty";
    public static final String taxRate = "taxRate";
    public static final String annualValue = "annualValue";

    //----------- TABLE COLUMNS for calulatedProfile -----------//
    public static final String maxMortgage = "maxMortgage";
    public static final String monthlyInstallment = "monthlyInstallment";
    public static final String maxMortgagePeriod = "maxMortgagePeriod";
    public static final String maxPropertyPrice = "maxPropertyPrice";
    public static final String downpayment = "downpayment";
    public static final String AHG = "AHG";
    public static final String SHG = "SHG";

    //----------- TABLE NAMES & DATABASE NAME -----------//
    public static final String DATABASE_NAME = "FindMyFirstHome.db";
    private static final String TABLE_NAME = "HDBDevelopment";
    private static final String TABLE_NAME2 = "FlatType";
    private static final String TABLE_NAME3 = "Amenities";
    private static final String TABLE_NAME4 = "Grants";
    private static final String TABLE_NAME5 = "UserData";
    private static final String TABLE_NAME6 = "membersSalaryList";
    private static final String TABLE_NAME7 = "TaxList";
    private static final String TABLE_NAME8 = "CalculatedProfile";

    //Draw the table
    private static final String SQL_HDBDevelopment = "CREATE TABLE " + TABLE_NAME + " (" + HDBDevelopmentName + " TEXT PRIMARY KEY, " + HDBDevelopmentDescription + " TEXT, " + HDBDevelopmentLongitude + " REAL, " + HDBDevelopmentLatitude + " REAL, " + HDBDevelopmentImgURL + " TEXT, " + HDBDevelopmentAffordability + " BOOLEAN " +");";

    public static final String SQL_FlatType = "CREATE TABLE " + TABLE_NAME2 + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+HDBDevelopmentName + " TEXT, " + HDBFlatType + " TEXT, " + HDBFlatPrice + " REAL, " + HDBFlatAffordability + " BOOLEAN, " + " FOREIGN KEY (" + HDBDevelopmentName + ") REFERENCES " + TABLE_NAME + "(" + HDBDevelopmentName +  "));";

    public static final String SQL_Amenities = "CREATE TABLE " + TABLE_NAME3 + " (" +  AmenitiesName + " TEXT PRIMARY KEY, " +  AmenitiesType + " TEXT, " + AmenitiesLongitude + " REAL, " + AmenitiesLatitude + " REAL " + ");";

    public static final String SQL_Grants = "CREATE TABLE " + TABLE_NAME4 + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + IncomeRequired + " TEXT, " + GrantType + " TEXT, " + GrantAmount + " REAL " + ");";

    public static final String SQL_UserData = "CREATE TABLE " + TABLE_NAME5 + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + isMarried + " BOOLEAN, "
            + isFirstTimeBuyer + " BOOLEAN, " + isSingaporean + " BOOLEAN, " + age + " REAL, " + grossSalary + " REAL, " + isFirstTimeBuyerPartner + " BOOLEAN, " + isSingaporeanPartner + " BOOLEAN, " + agePartner + " REAL, " + grossSalaryPartner + " REAL, "
            + carLoan + " REAL, " + creditLoan + " REAL, " + studyLoan + " REAL, " + otherCommitments + " REAL, " + buyer1CPF + " REAL, " + buyer2CPF + " REAL, "
            + numberOfAdditionalHouseholdMembers + " INTEGER " + ")";

    public static final String SQL_membersSalaryList_ = "CREATE TABLE " + TABLE_NAME6 + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + membersSalaryList + " REAL, " + " FOREIGN KEY (" + ID + ") REFERENCES " + TABLE_NAME5 + "(" + ID + "));";

    public static final String SQL_TaxList = "CREATE TABLE " + TABLE_NAME7 + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + typeOfProperty + " TEXT, " + annualValue + " REAL, "  + taxRate + " REAL " + ");";

    public static final String SQL_CalculatedProfile = "CREATE TABLE " + TABLE_NAME8 + " (" +  ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  maxMortgage + " REAL, " + monthlyInstallment + " REAL, " + maxMortgagePeriod + " REAL, " + maxPropertyPrice + " REAL, " + downpayment + " REAL, " + AHG + " REAL, " + SHG + " REAL " + ");";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + TABLE_NAME2;
    private static final String SQL_DELETE_ENTRIES3 = "DROP TABLE IF EXISTS " + TABLE_NAME3;
    private static final String SQL_DELETE_ENTRIES4 = "DROP TABLE IF EXISTS " + TABLE_NAME4;
    private static final String SQL_DELETE_ENTRIES5 = "DROP TABLE IF EXISTS " + TABLE_NAME5;
    private static final String SQL_DELETE_ENTRIES6 = "DROP TABLE IF EXISTS " + TABLE_NAME6;
    private static final String SQL_DELETE_ENTRIES7 = "DROP TABLE IF EXISTS " + TABLE_NAME7;
    private static final String SQL_DELETE_ENTRIES8 = "DROP TABLE IF EXISTS " + TABLE_NAME8;

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
        sqLiteDatabase.execSQL(SQL_TaxList);
        sqLiteDatabase.execSQL(SQL_CalculatedProfile);
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
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES7);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES8);
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


    public boolean writeHDBData(String name, String descriptionText, String ImgUrl, Boolean affordable) {
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
        values.put(HDBDevelopmentAffordability, affordable);

        //System.out.println(values);
        long newRowId = db.insert(TABLE_NAME, null, values);
        //System.out.println(newRowId);
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

    public void writeHDBFlatTypeData(String name, HashMap<String, Object> ListFlatType, Boolean affordable) {
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
                values.put(HDBFlatAffordability, affordable);
            }
        }
       // System.out.println(values);
        newRowId = db.insert(TABLE_NAME2, null, values);
        //System.out.println(newRowId);
        db.close();
    }

    public boolean writeAmenitiesData(HashMap<String, Object> infoList) {

        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        for (String key : infoList.keySet()) {
                if (key.equals("AmenitiesType")) {
                    values.put(AmenitiesType, infoList.get(key).toString());
                } else if (key.equals("AmenitiesName")) {
                    values.put(AmenitiesName, infoList.get(key).toString());
                } else if(key.equals("AmenitiesLng")){
                    values.put(AmenitiesLongitude, (Double)infoList.get(key));
                } else if(key.equals("AmenitiesLat")){
                    values.put(AmenitiesLatitude, (Double)infoList.get(key));
                }
        }

        long newRowId = db.insert(TABLE_NAME3, null, values);
        db.close();
        return true;
    }

    public boolean writeHDBGrantData(String incomeReq, HashMap<String, Double> grantList) {

        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        long newRowId = 0;

        values.put(IncomeRequired, incomeReq);
        for (String key : grantList.keySet()) {
            String grantType = key;
            Double grantAmount = grantList.get(key);
            values.put(GrantType, grantType);
            values.put(GrantAmount, grantAmount);
            newRowId = db.insert(TABLE_NAME4, null, values);
            //clear values
            values.clear();
        }

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

        long newRowId = db.insert(TABLE_NAME5, null, values);
        for (Double salary : ud.getMembersSalaryList()) {
            valueSalary.put(membersSalaryList, salary);
            long secondNewRowId = db.insert(TABLE_NAME6, null, valueSalary);
            valueSalary.clear();
        }
        db.close();

        return false;
    }

    public boolean writeTax(ArrayList<HashMap<String, String>> infoList){
        // Gets the data repository in write mode , getWritableDatabase is sqlite function
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        for (HashMap<String, String> i : infoList) {
            for (String key : i.keySet()) {
                if (key.equals("typeOfProperty")) {
                    values.put(typeOfProperty, i.get(key));
                } else if (key.equals("taxRate")) {
                    values.put(taxRate, i.get(key));
                } else {
                    values.put(annualValue, i.get(key));
                }
            }
        }

        long newRowId = db.insert(TABLE_NAME7, null, values);
        db.close();
        return true;
    }

    public boolean writeCalculatedProfile(CalculatedProfile cp){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(maxMortgage,cp.getMaxMortgage());
        values.put(monthlyInstallment,cp.getMonthlyInstallment());
        values.put(maxMortgagePeriod,cp.getMaxMortgagePeriod());
        values.put(maxPropertyPrice,cp.getMaxPropertyPrice());
        values.put(downpayment,cp.getDownpayment());
        values.put(AHG,cp.getAHG());
        values.put(SHG,cp.getSHG());
        long newRowId = db.insert(TABLE_NAME8, null, values);
        db.close();
        return true;
    }

//////////////////////////////////////Read functions///////////////////////////////////////////////////////////////////////

    public CalculatedProfile readCalculatedProfile() {

        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "SELECT * FROM " + TABLE_NAME8;
        Cursor cursor = db.rawQuery(rawQuery,null);
        double AHG=0.0;
        double SHG=0.0;
        double maxMortgage=0.0;
        double monthlyInstallment=0.0;
        double maxMortgagePeriod=0.0;
        double maxPropertyPrice=0.0;
        double downpayment=0.0;

        while (cursor.moveToNext() && cursor != null){
            int index;
            index = cursor.getColumnIndexOrThrow("maxMortgage");
            maxMortgage = cursor.getDouble(index);
            index = cursor.getColumnIndexOrThrow("monthlyInstallment");
            monthlyInstallment = cursor.getDouble(index);
            index = cursor.getColumnIndexOrThrow("maxMortgagePeriod");
            maxMortgagePeriod = cursor.getDouble(index);
            index = cursor.getColumnIndexOrThrow("downpayment");
            downpayment = cursor.getDouble(index);
            index = cursor.getColumnIndexOrThrow("AHG");
            AHG = cursor.getDouble(index);
            index = cursor.getColumnIndexOrThrow("SHG");
            SHG = cursor.getDouble(index);
        }
        cursor.close();
        CalculatedProfile cp = new CalculatedProfile(AHG,SHG,maxMortgage,monthlyInstallment,maxMortgagePeriod,maxPropertyPrice,downpayment);

        return cp;
    }

    public ArrayList<HDBDevelopment> readHDBData() {
        SQLiteDatabase db = getReadableDatabase();

        // How you want the results sorted in the resulting Cursor
        //Potential problem, potential solution query by ID.

        String rawQuery = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(rawQuery, null);

        HashMap<String, Object> flatTypeDetails = null;

        ArrayList<HDBDevelopment> HDBDList = new ArrayList<HDBDevelopment>();
        ArrayList<MapData> mdList = new ArrayList<MapData>();
        ArrayList<HashMap<String, Object>> HDBFTList = new ArrayList<HashMap<String, Object>>();
        LatLng coord = null;
        String DevelopmentName = "";
        String DevelopmentDescription = "";
        String DevelopmentImgURL = "";
        if (cursor.moveToFirst()){
            while (cursor != null) {
                DevelopmentName = cursor.getString(cursor.getColumnIndexOrThrow("HDBDevelopmentName"));
                System.out.println("1.1 "+DevelopmentName);
                DevelopmentDescription = cursor.getString(cursor.getColumnIndexOrThrow("HDBdevelopmentDescription"));
                System.out.println("1.2 "+DevelopmentDescription);
                Double DevelopmentLongitude = cursor.getDouble(cursor.getColumnIndexOrThrow("Longitude"));
                System.out.println("1.3 "+DevelopmentLongitude);
                Double DevelopmentLatitude = cursor.getDouble(cursor.getColumnIndexOrThrow("Latitude"));
                System.out.println("1.4 "+DevelopmentLatitude);
                DevelopmentImgURL = cursor.getString(cursor.getColumnIndexOrThrow("ImgURL"));
                System.out.println("1.5 "+DevelopmentImgURL);
                coord = new LatLng(DevelopmentLatitude, DevelopmentLongitude);
                System.out.println("1.6 "+coord);
                mdList = readMapData(DevelopmentName);
                HDBFTList = readHDBFlatType(DevelopmentName);

                HDBDList.add(createHDBDevelopmentObject(HDBFTList, DevelopmentName, DevelopmentDescription, false, coord, mdList, DevelopmentImgURL));

                if(cursor.isLast()) break;
                cursor.moveToNext();
            }
        }

        cursor.close();

        return HDBDList;

    }

    public HDBDevelopment readHDBData(String developmentName) {
        SQLiteDatabase db = getReadableDatabase();

        // How you want the results sorted in the resulting Cursor
        //Potential problem, potential solution query by ID.

        String rawQuery = "SELECT * FROM " + TABLE_NAME + " as D WHERE D.HDBDevelopmentName = '" + developmentName + "'";

        Cursor cursor = db.rawQuery(rawQuery, null);

        HashMap<String, Object> flatTypeDetails = null;

        HDBDevelopment HDBD = null;
        ArrayList<MapData> mdList = null;
        ArrayList<HashMap<String, Object>> HDBFTList = null;
        LatLng coord = null;
        String DevelopmentName = developmentName;
        String DevelopmentDescription = "";
        String DevelopmentImgURL = "";
        if(cursor.moveToFirst()) {
            while (cursor != null) {
                DevelopmentName = cursor.getString(cursor.getColumnIndexOrThrow("HDBDevelopmentName"));

                DevelopmentDescription = cursor.getString(cursor.getColumnIndexOrThrow("HDBdevelopmentDescription"));

                Double DevelopmentLongitude = cursor.getDouble(cursor.getColumnIndexOrThrow("Longitude"));

                Double DevelopmentLatitude = cursor.getDouble(cursor.getColumnIndexOrThrow("Latitude"));

                coord = new LatLng(DevelopmentLatitude, DevelopmentLongitude);

                DevelopmentImgURL = cursor.getString(cursor.getColumnIndexOrThrow("ImgURL"));

                mdList = readMapData(DevelopmentName);
                HDBFTList = readHDBFlatType(DevelopmentName);
                if(cursor.isLast()) break;
                cursor.moveToNext();
            }
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

        String rawQuery = "SELECT AmenitiesName, AmenitiesType, AmenitiesLongitude, AmenitiesLatitude FROM " + TABLE_NAME3;

        Cursor cursor = db.rawQuery(rawQuery, null);

        ArrayList<MapData> md = new ArrayList<MapData>();

        if(cursor.moveToFirst()) {
            while (cursor != null) {
                String AName = cursor.getString(cursor.getColumnIndexOrThrow("AmenitiesName"));

                Double ALongitude = cursor.getDouble(cursor.getColumnIndexOrThrow("AmenitiesLongitude"));

                Double ALatitude = cursor.getDouble(cursor.getColumnIndexOrThrow("AmenitiesLatitude"));

                String AType = cursor.getString(cursor.getColumnIndexOrThrow("AmenitiesType"));

                LatLng Acoord = new LatLng(ALatitude, ALongitude);

                //add data to mapData arrayList
                md.add(new MapData(AName, AType, Acoord));
                if(cursor.isLast()) break;
                cursor.moveToNext();
            }
        }

        //assert md != null;
        cursor.close();
        return md;
    }

    public ArrayList<HashMap<String, Object>> readHDBFlatType(String name) {
        assert getReadableDatabase() != null;
        SQLiteDatabase db = getReadableDatabase();

        HashMap<String, Object> flatTypeDetails = null;
        ArrayList<HashMap<String, Object>> HDBFlatTypedetailsList = new ArrayList<HashMap<String, Object>>();

        String rawQuery = "SELECT HDBFlatType, HDBFlatPrice FROM " + TABLE_NAME2 + " as FT " + "WHERE FT.HDBDevelopmentName = '" + name +"'";

        Cursor cursor = db.rawQuery(rawQuery, null);

        if(cursor.moveToFirst()) {
            while (cursor != null) {
                Double HDBFlatPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("HDBFlatPrice"));

                Integer HDBFlatType = cursor.getInt(cursor.getColumnIndexOrThrow("HDBFlatType"));

                //add data to flatType
                flatTypeDetails = new HashMap<String, Object>();
                flatTypeDetails.put("price", HDBFlatPrice);
                flatTypeDetails.put("flatType", HDBFlatType);
                flatTypeDetails.put("affordability", false);
                HDBFlatTypedetailsList.add(flatTypeDetails);
                if(cursor.isLast()) break;
                cursor.moveToNext();
            }
        }

        assert HDBFlatTypedetailsList != null;
        cursor.close();

        return HDBFlatTypedetailsList;
    }


    public LatLng readHDBDevelopmentCoordinates(String name) {
        assert getReadableDatabase() != null;
        SQLiteDatabase db = getReadableDatabase();

        LatLng coord = new LatLng(0, 0);

        String rawQuery = "SELECT HDBDevelopmentLatitude, HDBDevelopmentLongitude FROM " + TABLE_NAME + " as D" + " WHERE D.HDBDevelopmentName = '" + name +"'";

        Cursor cursor = db.rawQuery(rawQuery, null);

        if(cursor.moveToFirst()) {
            while (cursor != null) {
                Double lat = cursor.getDouble(cursor.getColumnIndexOrThrow("HDBDevelopmentLatitude"));

                Double lng = cursor.getDouble(cursor.getColumnIndexOrThrow("HDBDevelopmentLongitude"));
                coord = new LatLng(lat, lng);
                if(cursor.isLast()) break;
                cursor.moveToNext();
            }
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

        if(cursor.moveToFirst()) {
            while (cursor != null) {
                String type = cursor.getString(cursor.getColumnIndexOrThrow("GrantType"));

                Double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("GrantAmount"));

                grants.put(type, amount);
                if(cursor.isLast()) break;
                cursor.moveToNext();
            }
            cursor.close();
        }

        return grants;
    }

    public UserData readUserData(){

        UserData ud = null;

        assert getReadableDatabase() != null;
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Double> memList = new ArrayList<>();

        String rawQuery = "SELECT * FROM " + TABLE_NAME5;

        Cursor cursor = db.rawQuery(rawQuery, null);

        while (cursor.moveToNext() && cursor != null) {

            int index;
            index = cursor.getColumnIndexOrThrow("isMarried");
            Boolean married = Boolean.parseBoolean(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("isFirstTimeBuyer");
            Boolean firstTimeBuyer = Boolean.parseBoolean(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("isSingapore");
            Boolean isSG = Boolean.parseBoolean(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("age");
            Integer age = Integer.parseInt(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("grossSalary");
            Double grossSalary = Double.parseDouble(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("isFirstTimeBuyerPartner");
            Boolean partnerFirstTimeBuyer = Boolean.parseBoolean(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("isSingaporeanPartner");
            Boolean partnerSG = Boolean.parseBoolean(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("agePartner");
            Integer partnerAge = Integer.parseInt(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("grossSalaryPartner");
            Double partnerGrossSalary = Double.parseDouble(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("carLoan");
            Double carLoan = Double.parseDouble(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("creditLoan");
            Double creditLoan = Double.parseDouble(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("studyLoan");
            Double studyLoan = Double.parseDouble(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("otherCommitments");
            Double otherCommit = Double.parseDouble(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("buyer1CPF");
            Double buyer1CPF = Double.parseDouble(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("buyer2CPF");
            Double buyer2CPF = Double.parseDouble(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("numberOfAdditionalHouseholdMembers");
            Integer numberOfAdditionalHouseholdMembers = Integer.parseInt(cursor.getString(index));

            memList = readMembersSalaryList();

            ud = new UserData(married,firstTimeBuyer,isSG,age,grossSalary,partnerFirstTimeBuyer,partnerSG,partnerAge,partnerGrossSalary,carLoan,creditLoan,studyLoan,otherCommit,buyer1CPF,buyer2CPF,numberOfAdditionalHouseholdMembers,memList);

        }

        return ud;


    }

    public ArrayList<Double> readMembersSalaryList(){

        ArrayList<Double> memList = new ArrayList<>();
        int index;

        assert getReadableDatabase() != null;
        SQLiteDatabase db = getReadableDatabase();
        HashMap<String, Double> grants = new HashMap<String, Double>();

        String rawQuery = "SELECT * FROM " + TABLE_NAME6;

        Cursor cursor = db.rawQuery(rawQuery, null);

        while (cursor.moveToNext() && cursor != null) {
            index = cursor.getColumnIndexOrThrow("membersSalaryList");
            String membersSalary = cursor.getString(index);
            Double salary = Double.parseDouble(membersSalary);
            memList.add(salary);
        }
            return memList;
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

    ///////////////////////////////Delete Function////////////////////////////////////
    public void deleteHDBData() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.delete(TABLE_NAME2,null,null);
        db.close();
    }
}
