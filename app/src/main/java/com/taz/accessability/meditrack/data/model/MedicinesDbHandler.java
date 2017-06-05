package com.taz.accessability.meditrack.data.model;

import android.content.Context;
import android.net.Uri;

import com.taz.accessability.meditrack.data.AppContentProvider;
import com.taz.accessability.meditrack.data.BaseDbHandler;
/**
 * Created by tahzuddin on 5/6/17.
 */

public class MedicinesDbHandler {

//
//    public static final String TABLE_NAME = "medicines";
//    // Column names
//    public static final String COL_MEDICINE_NAME = "medicine_name";
//    public static final String COL_DOSE_FREQUENCY = "dose_frequency";
//    public static final String COL_Quantity = "quantity";
//    public static final String COL_NUMBER_OF_DOSES = "Number_of_dose";
//    public static final String COL_NUMBER_PURCHASED= "Number_purchased";
//
//
//    public static final String[] FIELDS = {
//            COL_ID,
//            COL_MEDICINE_NAME,
//            COL_DOSE_FREQUENCY,
//            COL_Quantity,
//            COL_NUMBER_OF_DOSES,
//            COL_NUMBER_PURCHASED,
//            COL_CREATED_AT,
//            COL_UPDATED_AT
//    };
//
//
//    // Create table statement
//    public static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + "("
//                    + COL_MEDICINE_NAME + " TEXT NOT NULL,"
//                    + COL_DOSE_FREQUENCY + " TEXT NOT NULL,"
//                    + COL_Quantity+ " TEXT NOT NULL,"
//                    + COL_NUMBER_OF_DOSES + " TEXT NOT NULL,"
//                    + COL_NUMBER_PURCHASED + " TEXT NOT NULL,"
//                    + CREATE_BASE_FIELDS
//                    + ")";
//
//
//
//
//    private static MedicinesDbHandler singleton;
//    private final String TAG = MedicinesDbHandler.class.getSimpleName();
//    private Context context;
//
//    public MedicinesDbHandler(Context context) {
//        this.context = context;
//    }
//
//    public static MedicinesDbHandler getInstance(Context context) {
//        if (singleton == null)
//            singleton = new MedicinesDbHandler(context);
//
//        return singleton;
//    }
//
//
//
//    Bui
//
//
//
//    @Override
//    public BaseModel get(long id) {
//        return null;
//    }
//
//    @Override
//    public BaseModel get() {
//        return null;
//    }
//
//    @Override
//    public void insert(BaseModel model) {
//
//    }
//
//    @Override
//    public void update(BaseModel model) {
//
//    }
//
//    @Override
//    public void delete(BaseModel model) {
//
//    }
}
