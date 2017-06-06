package com.taz.accessability.meditrack.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.taz.accessability.meditrack.data.model.BaseModel;
import com.taz.accessability.meditrack.data.model.Medicines;
import com.taz.accessability.meditrack.data.model.TimeOfDoses;
import com.taz.accessability.meditrack.util.DateTimeUtil;

import java.util.List;

import static com.taz.accessability.meditrack.data.BaseDbHandler.COL_CREATED_AT;
import static com.taz.accessability.meditrack.data.BaseDbHandler.COL_ID;
import static com.taz.accessability.meditrack.data.BaseDbHandler.COL_UPDATED_AT;
import static com.taz.accessability.meditrack.data.UserInfoDbHandler.COL_NAME;

/**
 * Created by tahzuddin on 6/6/17.
 */

public class TimeOfDoseDbHandler extends BaseDbHandler {

    public static final String TABLE_NAME = "Dose";
    // Column names
    public static final String COL_MEDICINE_ID = "medicine_id";
    public static final String COL_DODE_TIME = "dose_time";
    public static final String COL_ORDER = "_order";
    public static final String COL_TYPE = "type";

    public static final String[] FIELDS = {
            COL_ID,
            COL_MEDICINE_ID,
            COL_DODE_TIME,
            COL_CREATED_AT,
            COL_UPDATED_AT,
            COL_ORDER

    };


    // Create table statement
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_MEDICINE_ID + " INTEGER NOT NULL,"
                    + COL_DODE_TIME + " TEXT NOT NULL,"
                    + CREATE_BASE_FIELDS
                    + ")";
    private static TimeOfDoseDbHandler singleton;
    private final String TAG = TimeOfDoseDbHandler.class.getSimpleName();
    private Context context;

    public TimeOfDoseDbHandler(Context context) {
        this.context = context;
    }

    public static TimeOfDoseDbHandler getInstance(Context context) {
        if (singleton == null)
            singleton = new TimeOfDoseDbHandler(context);
       return singleton;
    }

    @Override
    Uri buildUri(long id) {
        return AppContentProvider.URI_DOSE.buildUpon().appendPath(id + "").build();
    }

    @Override
    public BaseModel get(long id) {
        Cursor cursor = context.getContentResolver().query(
                buildUri(id), null, null, null, null
        );

        TimeOfDoses timeOfDoses = null;

        if ((cursor.moveToFirst()) && cursor.getCount() != 0) {
            //cursor is not empty
            timeOfDoses = new TimeOfDoses(cursor);
        }

        cursor.close();
        return timeOfDoses;
    }



     public Medicines getMedicines(long id) {
        Cursor cursor = context.getContentResolver().query(
                AppContentProvider.URI_MEDICINE, null, MedicinesDbHandler.COL_ID + " = " + id, null, null
        );

        Medicines medicines = null;

        if (cursor.moveToFirst() && cursor.getCount() != 0) {
            medicines = new Medicines(cursor);
        }

        cursor.close();
        return medicines;
    }



    public Medicines getMedicine(TimeOfDoses timeOfDoses) {
        return getMedicines(timeOfDoses.getMedicineId());
    }



    @Override
    public BaseModel get() {
        return null;
    }

    @Override
    public void insert(BaseModel model) {
        context.getContentResolver().insert(
                AppContentProvider.URI_DOSE, model.getContent(true)
        );
    }

    @Override
    public void update(BaseModel model) {
        context.getContentResolver().update(
                buildUri(model.getId()), model.getContent(false), null, null
        );
    }


    @Override
    public void delete(BaseModel model) {

        context.getContentResolver().delete(
                AppContentProvider.URI_DOSE,
                COL_ID + " = " + model.getId(),
                null
        );
   }



    public long insertOrUpdate(ContentValues value) {
        TimeOfDoses task = (TimeOfDoses) get(value.getAsLong(COL_ID));
        if (task != null) {
            // update the task details
            if (value.containsKey(COL_ID))
                value.remove(COL_ID);

            if (value.containsKey(COL_CREATED_AT))
                value.remove(COL_CREATED_AT);

            value.put(COL_UPDATED_AT, DateTimeUtil.getNowDateTime());

            return DatabaseHandler.getInstance(context).getWritableDatabase().update(TimeOfDoseDbHandler.TABLE_NAME, value, COL_ID + " = " + task.getId(), null);
        } else {
            // insert new task
            value.put(COL_CREATED_AT, DateTimeUtil.getNowDateTime());
            value.put(COL_UPDATED_AT, DateTimeUtil.getNowDateTime());

            return DatabaseHandler.getInstance(context).getWritableDatabase().insert(TimeOfDoseDbHandler.TABLE_NAME, null, value);
        }
    }



    public void bulkInsert(List<TimeOfDoses> timeOfDoses) {
        ContentValues[] values = new ContentValues[timeOfDoses.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = timeOfDoses.get(i).getContent(true);
        }

        bulkInsert(values);
    }



    public void bulkInsert(ContentValues[] values) {
        context.getContentResolver().bulkInsert(
                AppContentProvider.URI_DOSE, values
        );
    }


    public Cursor getAll(Medicines medicines) {
        return context.getContentResolver().query(
                AppContentProvider.URI_DOSE, FIELDS, COL_MEDICINE_ID + " = " + medicines.getId(), null, null
        );
    }


    public Loader<Cursor> getOfferTaskCursorLoader(Medicines medicines) {
        return new CursorLoader(
                context, AppContentProvider.URI_DOSE, FIELDS, COL_MEDICINE_ID + " = " + medicines.getId(), null, COL_ORDER + " ASC, " + COL_TYPE + " ASC"
        );
    }


//    public void updateStatus(long id, int status) {
//        ContentValues values = new ContentValues();
//        values.put(COL_STATUS, status);
//
//        context.getContentResolver().update(
//                buildUri(id), values, null, null
//        );
//    }



}
