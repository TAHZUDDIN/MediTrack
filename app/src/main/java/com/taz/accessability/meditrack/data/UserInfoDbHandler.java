package com.taz.accessability.meditrack.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.taz.accessability.meditrack.data.model.BaseModel;
import com.taz.accessability.meditrack.data.model.UserInfo;
import com.taz.accessability.meditrack.util.DateTimeUtil;

/**
 * Created by tahzuddin on 04/06/17.
 */

public class UserInfoDbHandler extends BaseDbHandler {


    public static final String TABLE_NAME = "userInfo";
    // Column names
    public static final String COL_NAME = "name";
    public static final String COL_AGE = "age";
    public static final String COL_SOS_NUMBER = "sos_number";
    public static final String COL_SOSNAME = "sos_name";


    public static final String[] FIELDS = {
            COL_ID,
            COL_NAME,
            COL_AGE,
            COL_SOS_NUMBER,
            COL_SOSNAME,
            COL_CREATED_AT,
            COL_UPDATED_AT
    };
    // Create table statement
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_NAME + " TEXT NOT NULL,"
                    + COL_AGE + " TEXT NOT NULL,"
                    + COL_SOS_NUMBER+ " TEXT NOT NULL,"
                    + COL_SOSNAME + " TEXT NOT NULL,"
                    + CREATE_BASE_FIELDS
                    + ")";
    private static UserInfoDbHandler singleton;
    private final String TAG = UserInfoDbHandler.class.getSimpleName();
    private Context context;

    public UserInfoDbHandler(Context context) {
        this.context = context;
    }

    public static UserInfoDbHandler getInstance(Context context) {
        if (singleton == null)
            singleton = new UserInfoDbHandler(context);

        return singleton;
    }




    @Override
    Uri buildUri(long id) {
        return AppContentProvider.URI_USERINFO.buildUpon().build();
    }

    @Override
    public BaseModel get(long id) {
        return null;
    }


    @Override
    public BaseModel get() {
        Cursor cursor = context.getContentResolver().query(
                AppContentProvider.URI_USERINFO, null, null, null, null
        );

        UserInfo userInfo = null;

        if ((cursor.moveToFirst()) && cursor.getCount() != 0) {
            //cursor is not empty
            userInfo = new UserInfo(cursor);
        }

        cursor.close();
        return userInfo;
    }

    @Override
    public void insert(BaseModel model) {
        context.getContentResolver().insert(
                AppContentProvider.URI_USERINFO, model.getContent(true)
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
    }



    public long insertOrUpdate(ContentValues value) {
        UserInfo offer = (UserInfo) get();
        if (offer != null) {
            //update existing offer
            if (value.containsKey(COL_ID))
                value.remove(COL_ID);
            if (value.containsKey(COL_CREATED_AT))
                value.remove(COL_CREATED_AT);
            value.put(COL_UPDATED_AT, DateTimeUtil.getNowDateTime());

            return DatabaseHandler.getInstance(context).getWritableDatabase().update(UserInfoDbHandler.TABLE_NAME, value, COL_ID + " = " + offer.getId(), null);
        } else {
            // insert new offer
            value.put(COL_CREATED_AT, DateTimeUtil.getNowDateTime());
            value.put(COL_UPDATED_AT, DateTimeUtil.getNowDateTime());

            return DatabaseHandler.getInstance(context).getWritableDatabase().insert(UserInfoDbHandler.TABLE_NAME, null, value);
        }
    }
}
