package com.example.findmyfirsthome.Presenter;

import android.content.Context;

import com.example.findmyfirsthome.Interface.DataAccessInterfaceClass;

public class DataAccessFactory {

    private static volatile DataAccessInterfaceClass databaseController = null;

    public static DataAccessInterfaceClass getDatabaseCtrlInstance(Context context)
    {
        if(databaseController == null)
        {
            databaseController = new SqliteDatabaseController(context);

            return databaseController;
        }
        else
            return databaseController;
    }
}
