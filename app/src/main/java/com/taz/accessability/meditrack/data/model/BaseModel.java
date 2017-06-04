package com.taz.accessability.meditrack.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.taz.accessability.meditrack.data.BaseDbHandler;
import com.taz.accessability.meditrack.util.DateTimeUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tahzuddin on 04/06/17.
 */

public class BaseModel   implements Serializable {
    long id = -1;
    private String createdAt;
    private String updatedAt;

    public BaseModel() {
    }

    public BaseModel(long id, String createdAt, String updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BaseModel(final Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(BaseDbHandler.COL_ID));
        this.createdAt = cursor.getString(cursor.getColumnIndex(BaseDbHandler.COL_CREATED_AT));
        this.updatedAt = cursor.getString(cursor.getColumnIndex(BaseDbHandler.COL_UPDATED_AT));
    }

    public ContentValues getContent(boolean shouldIncludeId) {
        ContentValues values = new ContentValues();
        if (shouldIncludeId)
            values.put(BaseDbHandler.COL_ID, id);

        values.put(BaseDbHandler.COL_CREATED_AT, createdAt);
        values.put(BaseDbHandler.COL_UPDATED_AT, updatedAt);
        return values;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = DateTimeUtil.dateToString(createdAt);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = DateTimeUtil.dateToString(updatedAt);
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
