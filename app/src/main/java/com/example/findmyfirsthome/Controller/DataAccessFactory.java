package com.example.findmyfirsthome.Controller;

import android.content.Context;

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
