package com.taz.accessability.meditrack.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tahzuddin on 04/06/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "Medicine_db";
    static final int DATABASE_VERSION = 1;


    private static DatabaseHandler singleton;
    private final Context context;
    private String TAG = DatabaseHandler.class.getSimpleName();

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static DatabaseHandler getInstance(final Context context) {
        if (singleton == null) {
            singleton = new DatabaseHandler(context);
        }
        return singleton;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserInfoDbHandler.CREATE_TABLE);
        db.execSQL(MedicinesDbHandler.CREATE_TABLE);
        db.execSQL(TimeOfDoseDbHandler.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db == null) {
            db = this.getWritableDatabase();
        }
        db.execSQL("DROP TABLE IF EXISTS " + UserInfoDbHandler.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MedicinesDbHandler.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TimeOfDoseDbHandler.TABLE_NAME);
        onCreate(db);
    }
}
