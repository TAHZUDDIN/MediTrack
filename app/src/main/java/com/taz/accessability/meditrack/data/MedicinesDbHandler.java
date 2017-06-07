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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tahzuddin on 04/06/17.
 */

public class MedicinesDbHandler extends BaseDbHandler{


    public static final String TABLE_NAME = "medicine";
    // Column names
    public static final String COL_NAME = "name";
    public static final String COL_DOS_FREQUENCY = "dos_frequency";
    public static final String COL_DOS_QUANTITY = "dos_quantity";
    public static final String COL_DOSES_PERDAY = "doses_perday";
    //public static final String COL_STATUS = "status";
    public static final String COL_NUMBER_PURCHASED = "number_purchased";
    public static final String COL_SORT_ORDER = "sort_order";

    public static final String[] FIELDS = {
            COL_ID,
            COL_NAME,
            COL_DOS_FREQUENCY,
            COL_DOS_QUANTITY,
            COL_DOSES_PERDAY,
            //COL_STATUS,
            COL_NUMBER_PURCHASED,
            COL_SORT_ORDER,
            COL_CREATED_AT,
            COL_UPDATED_AT
    };
    // Create table statement
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_NAME + " TEXT NOT NULL,"
                    + COL_DOS_FREQUENCY + " TEXT NOT NULL,"
                    + COL_DOS_QUANTITY + " TEXT NOT NULL,"
                    + COL_DOSES_PERDAY + " TEXT NOT NULL,"
                    //+ COL_STATUS + " INTEGER DEFAULT 0,"
                    + COL_NUMBER_PURCHASED + " TEXT NOT NULL,"
                    + CREATE_BASE_FIELDS
                    + ")";


    private static MedicinesDbHandler singleton;
    private final String TAG = MedicinesDbHandler.class.getSimpleName();
    private Context context;

    public MedicinesDbHandler(Context context) {
        this.context = context;
    }

    public static MedicinesDbHandler getInstance(Context context) {
        if (singleton == null)
            singleton = new MedicinesDbHandler(context);
        return singleton;
    }


    @Override
    Uri buildUri(long id) {
        return AppContentProvider.URI_MEDICINE.buildUpon().appendPath(id + "").build();
    }


    @Override
    public BaseModel get(long id) {
        Cursor cursor = context.getContentResolver().query(
                buildUri(id), null, null, null, null
        );

        Medicines medicines = null;

        if ((cursor.moveToFirst()) && cursor.getCount() != 0) {
            //cursor is not empty
            medicines = new Medicines(cursor);
        }

        cursor.close();
        return medicines;
    }

    @Override
    public BaseModel get() {
        return null;
    }

    public List<TimeOfDoses> getTimeOfDoses(long id) {
        Cursor cursor = context.getContentResolver().query(
                AppContentProvider.URI_DOSE, null, TimeOfDoseDbHandler.COL_MEDICINE_ID + " = " + id, null, TimeOfDoseDbHandler.COL_ORDER + " ASC, "
        );

        List<TimeOfDoses> timeOfDoses = new ArrayList<>();

        while (cursor.moveToNext()) {
            TimeOfDoses dose_time = new TimeOfDoses(cursor);
            timeOfDoses.add(dose_time);
        }

        cursor.close();
        return timeOfDoses;
    }



    public List<TimeOfDoses> getTimeOfDoses(Medicines medicines) {
        return getTimeOfDoses(medicines.getId());
    }


    @Override
    public void insert(BaseModel model) {
        context.getContentResolver().insert(
                AppContentProvider.URI_MEDICINE, model.getContent(true)
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
                AppContentProvider.URI_MEDICINE,
                COL_ID + " = " + model.getId(),
                null
        );
        context.getContentResolver().delete(
                AppContentProvider.URI_DOSE,
                TimeOfDoseDbHandler.COL_MEDICINE_ID + " = " + model.getId(),
                null
        );
    }


    public long insertOrUpdate(ContentValues value) {
        Medicines medicines = (Medicines) get(value.getAsLong(COL_ID));
        if (medicines != null) {
            //update existing offer
            if (value.containsKey(COL_ID))
                value.remove(COL_ID);
            if (value.containsKey(COL_CREATED_AT))
                value.remove(COL_CREATED_AT);
            value.put(COL_UPDATED_AT, DateTimeUtil.getNowDateTime());

            return DatabaseHandler.getInstance(context).getWritableDatabase().update(MedicinesDbHandler.TABLE_NAME, value, COL_ID + " = " + medicines.getId(), null);
        } else {
            // insert new offer
            value.put(COL_CREATED_AT, DateTimeUtil.getNowDateTime());
            value.put(COL_UPDATED_AT, DateTimeUtil.getNowDateTime());

            return DatabaseHandler.getInstance(context).getWritableDatabase().insert(MedicinesDbHandler.TABLE_NAME, null, value);
        }
    }


    public void bulkInsert(List<Medicines> medicines) {
        ContentValues[] values = new ContentValues[medicines.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = medicines.get(i).getContent(true);
        }

        bulkInsert(values);
    }



    public void bulkInsert(ContentValues[] values) {
        context.getContentResolver().bulkInsert(
                AppContentProvider.URI_MEDICINE, values
        );
    }



    public Loader<Cursor> getAllCursorLoader() {
        return new CursorLoader(context, AppContentProvider.URI_MEDICINE, FIELDS, null, null, COL_SORT_ORDER + " DESC");
    }


    public Cursor getAll() {
        return context.getContentResolver().query(
                AppContentProvider.URI_MEDICINE, FIELDS, null, null, COL_SORT_ORDER + " DESC"
        );
    }



}
