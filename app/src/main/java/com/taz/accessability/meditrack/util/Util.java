package com.taz.accessability.meditrack.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.taz.accessability.meditrack.activity.MainActivity;
import com.taz.accessability.meditrack.data.UserInfoDbHandler;
import com.taz.accessability.meditrack.data.model.UserInfo;

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
}
