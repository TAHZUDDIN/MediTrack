package com.taz.accessability.meditrack.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.taz.accessability.meditrack.data.AppContentProvider;
import com.taz.accessability.meditrack.data.TimeOfDoseDbHandler;

import static android.R.attr.name;
import static android.R.attr.order;
import static android.R.attr.type;

/**
 * Created by tahzuddin on 6/6/17.
 */

public class TimeOfDoses extends BaseModel{


    private long medicineId;
    private String dosetime;



    public TimeOfDoses() {
        super();
    }


    public TimeOfDoses(long id, String createdAt, String updatedAt, long offerId, String dosetime) {
        super(id, createdAt, updatedAt);
        this.medicineId = offerId;
        this.dosetime = dosetime;
    }


    public TimeOfDoses(final Cursor cursor) {
        super(cursor);
        this.medicineId = cursor.getLong(cursor.getColumnIndex(TimeOfDoseDbHandler.COL_MEDICINE_ID));
        this.dosetime = cursor.getString(cursor.getColumnIndex(TimeOfDoseDbHandler.COL_DODE_TIME));
    }


    public ContentValues getContent(boolean shouldIncludeId) {
        ContentValues values = super.getContent(shouldIncludeId);

        values.put(TimeOfDoseDbHandler.COL_MEDICINE_ID, medicineId);
        values.put(TimeOfDoseDbHandler.COL_DODE_TIME, name);

        return values;
    }

    public Uri buildUri() {
        return AppContentProvider.URI_DOSE.buildUpon().appendPath(id + "").build();
    }


    public long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(long medicineId) {
        this.medicineId = medicineId;
    }

    public String getDosetime() {
        return dosetime;
    }

    public void setDosetime(String dosetime) {
        this.dosetime = dosetime;
    }
}
