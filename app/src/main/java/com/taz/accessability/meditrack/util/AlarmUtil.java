package com.taz.accessability.meditrack.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.taz.accessability.meditrack.app.MyApplication;
import com.taz.accessability.meditrack.data.TimeOfDoseDbHandler;
import com.taz.accessability.meditrack.data.model.TimeOfDoses;
import com.taz.accessability.meditrack.receiver.AlarmBroadcastReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by tahzuddin on 12/6/17.
 */

public class AlarmUtil {


    ArrayList<PendingIntent> intentArray;
    List<TimeOfDoses> timeOfDoses;

    public void setAlarmTime(){

        Context context = MyApplication.getInstance();
        timeOfDoses = TimeOfDoseDbHandler.getInstance(context).getAll();

        AlarmManager mgrAlarm = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        intentArray = new ArrayList<PendingIntent>();

        for(int i = 0; i < timeOfDoses.size(); ++i)
        {
            long time = getTimeForAlarm("14:11");
            int id = (int)timeOfDoses.get(i).getId();

            Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
            // Loop counter `i` is used as a `requestCode`
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
            // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
            mgrAlarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 60000 * i,
                    pendingIntent);

            intentArray.add(pendingIntent);
        }
    }



    public void cancelAlarmIfExists(){

        Context mContext = MyApplication.getInstance();
        AlarmManager am=(AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);

        try{
            for(int i =0; i<intentArray.size(); i++){
                PendingIntent pendingIntent = intentArray.get(0);
                am.cancel(pendingIntent);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent,0);
           }

        }catch (Exception e){
            e.printStackTrace();
        }
    }





    public long getTimeForAlarm(String time){

        int hour = convertStringToIntHour(time);
        int minutes = convertStringToIntMinutes(time);

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.HOUR_OF_DAY, hour);
        calendar.set(calendar.MINUTE, minutes);
        calendar.set(calendar.SECOND, 0);
        calendar.set(calendar.MILLISECOND, 0);
        long sdl = calendar.getTimeInMillis();
        return sdl;
    }



    public int convertStringToIntHour(String time){
        String[] animalsArray = time.split(":");
        return Integer.valueOf(animalsArray[0]);
    }



    public int convertStringToIntMinutes(String time){
        String[] animalsArray = time.split(":");
        return Integer.valueOf(animalsArray[1]);
    }


}
