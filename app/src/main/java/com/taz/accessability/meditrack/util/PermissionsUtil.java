package com.taz.accessability.meditrack.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.taz.accessability.meditrack.app.MyApplication;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tahzuddin on 6/6/17.
 */

public class PermissionsUtil {

    int REQUEST_CODE = 0;
    String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,

    };
    private Activity mActivity;

    public void showPermissionIfRequired(Activity activity) {
        mActivity = activity;
        if (!hasAllPermissions())
            if (canShowRequestPermissions())
                requestPermission(PERMISSIONS, REQUEST_CODE);
            else {
                Toast.makeText(mActivity, "Please allow us to access required permissions.", Toast.LENGTH_LONG).show();
                goToSettings();
            }
    }

    public boolean hasAllPermissions() {
        for (String permission : PERMISSIONS)
            if (!checkPermission(permission))
                return false;
        return true;
    }


    private boolean checkPermission(String permission) {
        int result = ContextCompat.checkSelfPermission(mActivity, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    private boolean canShowRequestPermissions() {
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission))
                return false;
        }
        return true;
    }


    public void requestPermission(String[] permissions, int requestCode) {
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (!checkPermission(permission)) {
                permissionList.add(permission);
            }
        }

        if (permissionList.size() > 0) {
            permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(mActivity, permissions, requestCode);
        }
    }


    private void goToSettings() {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + MyApplication.getInstance().getApplicationContext().getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivityForResult(myAppSettings, REQUEST_CODE);
    }


}
