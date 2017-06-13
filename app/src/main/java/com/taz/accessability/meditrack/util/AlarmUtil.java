package com.taz.accessability.meditrack.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.taz.accessability.meditrack.app.MyApplication;
import com.taz.accessability.meditrack.constants.Constants;
import com.taz.accessability.meditrack.data.MedicinesDbHandler;
import com.taz.accessability.meditrack.data.TimeOfDoseDbHandler;
import com.taz.accessability.meditrack.data.model.Medicines;
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

//        Medicines medicines;
//
//        Context context = MyApplication.getInstance();
//        timeOfDoses = TimeOfDoseDbHandler.getInstance(context).getAll();
//
//        AlarmManager mgrAlarm = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//        intentArray = new ArrayList<PendingIntent>();
//
//        for(int i = 0; i < timeOfDoses.size(); ++i)
//        {
//            medicines = MedicinesDbHandler.getInstance(context).getMedicines(timeOfDoses.get(i).getMedicineId());
//            long time = getTimeForAlarm(timeOfDoses.get(i).getDosetime());
//            int id = (int)timeOfDoses.get(i).getId();
//
//            Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
//            intent.putExtra(Constants.MEDICINE_IN_ALARM,medicines);
//            intent.putExtra(Constants.TIMEOFDOSE_IN_ALARM,timeOfDoses.get(i));
//            // Loop counter `i` is used as a `requestCode`
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
//            // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
//            mgrAlarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, time, pendingIntent);
//
//            intentArray.add(pendingIntent);
//        }


        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        Context context = MyApplication.getInstance();

        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,  AlarmBroadcastReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

// Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 15);

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);



    }



    public void cancelAlarmIfExists(){

        Context mContext = MyApplication.getInstance();
        AlarmManager am=(AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);

        if(intentArray == null)
            return;

        try{
            for(int i =0; i<intentArray.size(); i++){
                PendingIntent pendingIntent = intentArray.get(i);
                am.cancel(pendingIntent);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent,0);
            }
            intentArray = null;

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



    public void cancelAndStartAlarmManager(){
        MyApplication.getInstance().getInstanceAlarmUtil().cancelAlarmIfExists();
        MyApplication.getInstance().getInstanceAlarmUtil().setAlarmTime();
    }


    public ArrayList<PendingIntent> alarmActiveOrNot(){
        return intentArray;
    }


}
