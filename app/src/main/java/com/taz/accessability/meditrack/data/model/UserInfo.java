package com.taz.accessability.meditrack.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.taz.accessability.meditrack.data.UserInfoDbHandler;

/**
 * Created by tahzuddin on 04/06/17.
 */

public class UserInfo extends BaseModel{

    private String name;
    private String age;
    private String sosName;
    private String sosNumber;



    public UserInfo() {
        super();
    }

    public UserInfo(long id, String createdAt, String updatedAt, String name, String age, String sosName, String sosNumber) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.age = age;
        this.sosName = sosName;
        this.sosNumber= sosNumber;
    }



    public UserInfo(final Cursor cursor) {
        super(cursor);
        this.name = cursor.getString(cursor.getColumnIndex(UserInfoDbHandler.COL_NAME));
        this.age = cursor.getString(cursor.getColumnIndex(UserInfoDbHandler.COL_AGE));
        this.sosName = cursor.getString(cursor.getColumnIndex(UserInfoDbHandler.COL_SOSNAME));
        this.sosNumber = cursor.getString(cursor.getColumnIndex(UserInfoDbHandler.COL_SOS_NUMBER));

    }

    public ContentValues getContent(boolean shouldIncludeId) {
        ContentValues values = super.getContent(shouldIncludeId);

        values.put(UserInfoDbHandler.COL_NAME, name);
        values.put(UserInfoDbHandler.COL_AGE, age);
        values.put(UserInfoDbHandler.COL_SOSNAME, sosName);
        values.put(UserInfoDbHandler.COL_SOS_NUMBER, sosNumber);

        return values;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSosName() {
        return sosName;
    }

    public void setSosName(String sosName) {
        this.sosName = sosName;
    }

    public String getSosNumber() {
        return sosNumber;
    }

    public void setSosNumber(String sosNumber) {
        this.sosNumber = sosNumber;
    }
}
