package com.taz.accessability.meditrack.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tahzuddin on 6/6/17.
 */

public class Util {

    public static void ToastDisplay(Context c, String message){
        Toast.makeText(c, message,Toast.LENGTH_SHORT).show();
    }
}
