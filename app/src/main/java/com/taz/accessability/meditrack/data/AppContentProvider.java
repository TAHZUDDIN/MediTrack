package com.taz.accessability.meditrack.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.taz.accessability.meditrack.util.DateTimeUtil;

import static com.taz.accessability.meditrack.data.DatabaseHandler.getInstance;
import static com.taz.accessability.meditrack.data.UserInfoDbHandler.TABLE_NAME;


/**
 * Created by tahzuddin on 04/06/17.
 */

public class AppContentProvider extends ContentProvider {



//    static final String PROVIDER_NAME = "com.taz.accessability.meditrack.AppContentProvider";
//    static final String URL = "content://" + PROVIDER_NAME + "/students";
//    public static final Uri CONTENT_URI = Uri.parse(URL);




    private static final String AUTHORITY = "com.taz.accessability.meditrack.AppContentProvider";
    private static final String SCHEME = "content://";
    private final static String USERINFO_SCHEMA = SCHEME + AUTHORITY + "/userInfo";
    public final static Uri URI_USERINFO = Uri.parse(USERINFO_SCHEMA);
    private final static String MEDICINE_SCHEMA = SCHEME + AUTHORITY + "/medicine";
    public final static Uri URI_MEDICINE = Uri.parse(MEDICINE_SCHEMA);
    private final static String DOSE_SCHEMA = SCHEME + AUTHORITY + "/dose";
    public final static Uri URI_DOSE = Uri.parse(DOSE_SCHEMA);
    private final int USER_ALL = 1;
    private final int MEDICINE_ALL = 2;
    private final int MEDICINE_SINGLE = 3;
    private final int DOSE_ALL = 4;
    private final int DOSE_SINGLE = 5;

    private final UriMatcher uriMatcher = buildUriMatcher();
    private String TAG = AppContentProvider.class.getSimpleName();


    private UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "userInfo", USER_ALL);
        uriMatcher.addURI(AUTHORITY, "medicine", MEDICINE_ALL);
        uriMatcher.addURI(AUTHORITY, "medicine/#", MEDICINE_SINGLE);
        uriMatcher.addURI(AUTHORITY, "dose", DOSE_ALL);
        uriMatcher.addURI(AUTHORITY, "dose/#", DOSE_SINGLE);
        return uriMatcher;
    }


    /**
     * Database specific constant declarations
     */

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        return true;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match){
            case USER_ALL:
                return "vnd.android.cursor.dir/"+AUTHORITY+".userInfo";
            case MEDICINE_ALL:
                return "vnd.android.cursor.dir/"+AUTHORITY+".medicine";
            case MEDICINE_SINGLE:
                return "vnd.android.cursor.item/" + AUTHORITY + ".medicine";
            case DOSE_ALL:
                return "vnd.android.cursor.dir/"+AUTHORITY+".dose";
            case DOSE_SINGLE:
                return "vnd.android.cursor.item/" + AUTHORITY + ".dose";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }



    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = getInstance(getContext()).getReadableDatabase();
        Cursor retCursor;

        switch (uriMatcher.match(uri)) {
            case USER_ALL: {
                retCursor = db.query(
                        TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder
                );
                break;
            }
            case MEDICINE_ALL: {
                retCursor = db.query(
                        MedicinesDbHandler.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder
                );
                break;
            }

            case MEDICINE_SINGLE: {
                String itemId = uri.getPathSegments().get(1);

                retCursor = db.query(
                        MedicinesDbHandler.TABLE_NAME,
                        projection,
                        MedicinesDbHandler.COL_ID + " = " + itemId,
                        null, null, null, null
                );
                break;
            }
            case DOSE_ALL: {
                retCursor = db.query(
                        TimeOfDoseDbHandler.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder
                );
                break;
            }
            case DOSE_SINGLE: {
                String itemId = uri.getPathSegments().get(1);

                retCursor = db.query(
                        TimeOfDoseDbHandler.TABLE_NAME,
                        projection,
                        TimeOfDoseDbHandler.COL_ID + " = " + itemId,
                        null, null, null, null
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }




    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = getInstance(getContext()).getWritableDatabase();
        Uri retUri;

        contentValues.put(BaseDbHandler.COL_CREATED_AT, DateTimeUtil.getNowDateTime());
        contentValues.put(BaseDbHandler.COL_UPDATED_AT, DateTimeUtil.getNowDateTime());


        switch (uriMatcher.match(uri)) {
            case USER_ALL: {
                long insertedId = db.insert(TABLE_NAME, null, contentValues);

                if (insertedId > 0)
                    retUri = ContentUris.withAppendedId(AppContentProvider.URI_USERINFO, insertedId);
                else
                    throw new SQLException("Failed to insert row : " + uri);
                break;
            }

            case DOSE_ALL: {
                long insertedId = db.insert(TimeOfDoseDbHandler.TABLE_NAME, null, contentValues);

                if (insertedId > 0)
                    retUri = ContentUris.withAppendedId(AppContentProvider.URI_DOSE, insertedId);
                else
                    throw new SQLException("Failed to insert row : " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

        getContext().getContentResolver().notifyChange(retUri, null);
        return retUri;
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = getInstance(getContext()).getWritableDatabase();
        int rowDeleted;

        switch (uriMatcher.match(uri)) {
            case USER_ALL: {
                rowDeleted = db.delete(
                        TABLE_NAME,
                        selection, selectionArgs
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

        if (selection == null || rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowDeleted;
    }




    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        SQLiteDatabase db = getInstance(getContext()).getWritableDatabase();
        String tableName = "";
        // row updated count
        int updateCount = 0;

        contentValues.put(BaseDbHandler.COL_UPDATED_AT, DateTimeUtil.getNowDateTime());

        switch (uriMatcher.match(uri)) {
            case USER_ALL: {
                tableName = UserInfoDbHandler.TABLE_NAME;
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

        try {
            updateCount = db.update(tableName, contentValues, selection, selectionArgs);
        } catch (Exception e) {

        }

        if (updateCount > 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }



    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        SQLiteDatabase db = getInstance(getContext()).getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case USER_ALL: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = UserInfoDbHandler.getInstance(getContext()).insertOrUpdate(value);
                        if (-1 != _id)
                            returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }

            default:
                return super.bulkInsert(uri, values);
        }
    }


}
