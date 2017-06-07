package com.taz.accessability.meditrack.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.taz.accessability.meditrack.data.UserInfoDbHandler;
import com.taz.accessability.meditrack.data.model.UserInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tahzuddin on 6/6/17.
 */

public class Util {

    public static void ToastDisplay(Context c, String message){
        Toast.makeText(c, message,Toast.LENGTH_SHORT).show();
    }


    public static void makeACall(Activity activity){
        UserInfo userInfo =(UserInfo) UserInfoDbHandler.getInstance(activity).get();
        String tel = "tel:"+(userInfo.getSosNumber()).trim();;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(tel));

        if (ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            activity.startActivity(callIntent);
        }
    }



    public static Date convertStringToDateFormat(String time){

        DateFormat outFormat = new SimpleDateFormat( "HH:mm");
        Date date = null;

        try
        {
            date = outFormat.parse(time);
        }
        catch ( ParseException e )
        {
            e.printStackTrace();
        }

        return date;
    }


    public void getAM_PM(int hours,int minutes){

//        Calendar someDate = Calendar.getInstance();
//        someDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        someDate.set(Calendar.MINUTE, minute);
//        someDate.set(Calendar.SECOND, second);
//        if (someDate.get(AM_PM == Calendar.AM)
////          System.println('The selected time is AM');
//        else
////        System.println('The selected time is PM');

    }
}
