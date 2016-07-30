package com.findmeout.android.data.provider.sql;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.findmeout.android.data.provider.DataContract;
import com.findmeout.android.data.provider.SQLDDL;

/**
 * Created by umesh0492 on 15/02/16.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "F_M_O.db";

    public DbHelper (Context context) {
        super (context, DATABASE_NAME, null, DataContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {
        for (String stmt : SQLDDL.getSQLTableCreateStatements ()) {
            sqLiteDatabase.execSQL (stmt);
        }
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
       // Utils.backUpCurrentDB (MainApplication.context.getDatabasePath (DATABASE_NAME));
    }

    @Override
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion) {
       // Utils.rollBackCurrentDB (MainApplication.context.getDatabasePath (DATABASE_NAME));
    }

}
