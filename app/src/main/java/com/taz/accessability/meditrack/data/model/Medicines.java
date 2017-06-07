package com.taz.accessability.meditrack.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.taz.accessability.meditrack.data.AppContentProvider;
import com.taz.accessability.meditrack.data.MedicinesDbHandler;

/**
 * Created by tahzuddin on 5/6/17.
 */

public class Medicines extends  BaseModel {


    private String name;
    private String dose_frequency;
    private String dose_quantity;
    private String doses_perday;
    //private int status;
    private String no_dose_purchased;


    public Medicines() {
        super();
    }

    public Medicines(long id, String createdAt, String updatedAt, String name, String dose_frequency, String dose_quantity, String doses_perday, String no_dose_purchased) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.dose_frequency = dose_frequency;
        this.dose_quantity = dose_quantity;
        this.doses_perday = doses_perday;
        //this.status = status;
        this.no_dose_purchased = no_dose_purchased;
    }

    public Medicines(final Cursor cursor) {
        super(cursor);
        this.name = cursor.getString(cursor.getColumnIndex(MedicinesDbHandler.COL_NAME));
        this.dose_frequency = cursor.getString(cursor.getColumnIndex(MedicinesDbHandler.COL_DOS_FREQUENCY));
        this.dose_quantity = cursor.getString(cursor.getColumnIndex(MedicinesDbHandler.COL_DOS_QUANTITY));
        this.doses_perday = cursor.getString(cursor.getColumnIndex(MedicinesDbHandler.COL_DOSES_PERDAY));
        //this.status = cursor.getInt(cursor.getColumnIndex(OfferDbHandler.COL_STATUS));
        this.no_dose_purchased = cursor.getString(cursor.getColumnIndex(MedicinesDbHandler.COL_NUMBER_PURCHASED));

    }

    public ContentValues getContent(boolean shouldIncludeId) {
        ContentValues values = super.getContent(shouldIncludeId);

        values.put(MedicinesDbHandler.COL_NAME, name);
        values.put(MedicinesDbHandler.COL_DOS_FREQUENCY, dose_frequency);
        values.put(MedicinesDbHandler.COL_DOS_QUANTITY, dose_quantity);
        values.put(MedicinesDbHandler.COL_DOSES_PERDAY,doses_perday);
        //values.put(OfferDbHandler.COL_STATUS, status);
        values.put(MedicinesDbHandler.COL_NUMBER_PURCHASED, no_dose_purchased);

        return values;
    }

    public Uri buildUri() {
        return AppContentProvider.URI_MEDICINE.buildUpon().appendPath(id + "").build();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose_frequency() {
        return dose_frequency;
    }

    public void setDose_frequency(String dose_frequency) {
        this.dose_frequency = dose_frequency;
    }

    public String getDose_quantity() {
        return dose_quantity;
    }

    public void setDose_quantity(String dose_quantity) {
        this.dose_quantity = dose_quantity;
    }

    public String getDoses_perday() {
        return doses_perday;
    }

    public void setDoses_perday(String doses_perday) {
        this.doses_perday = doses_perday;
    }

    public String getNo_dose_purchased() {
        return no_dose_purchased;
    }

    public void setNo_dose_purchased(String no_dose_purchased) {
        this.no_dose_purchased = no_dose_purchased;
    }
}
