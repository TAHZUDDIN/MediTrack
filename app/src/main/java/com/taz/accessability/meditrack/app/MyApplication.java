package com.taz.accessability.meditrack.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;

import com.taz.accessability.meditrack.util.AlarmUtil;
import com.taz.accessability.meditrack.util.PermissionsUtil;


/**
 * Created by tahzuddin on 6/6/17.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;
    public final String TAG = MyApplication.class.getSimpleName();
    PermissionsUtil permissionsUtil;
    AlarmUtil alarmUtil;


    public static synchronized MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public PermissionsUtil getInstancePermissionUtil() {
        if (permissionsUtil == null)
            return permissionsUtil = new PermissionsUtil();
        else
            return permissionsUtil;
    }


    public AlarmUtil getInstanceAlarmUtil(){
        if(alarmUtil == null)
            return alarmUtil = new AlarmUtil();
        else
            return alarmUtil;
    }



}
