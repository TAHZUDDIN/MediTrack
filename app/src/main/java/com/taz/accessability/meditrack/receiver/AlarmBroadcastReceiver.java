package com.taz.accessability.meditrack.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;

import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.activity.MainActivity;
import com.taz.accessability.meditrack.constants.Constants;
import com.taz.accessability.meditrack.data.model.Medicines;
import com.taz.accessability.meditrack.data.model.TimeOfDoses;

/**
 * Created by tahzuddin on 12/6/17.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {


        Medicines medicines =(Medicines)intent.getSerializableExtra(Constants.MEDICINE_IN_ALARM);
        TimeOfDoses timeOfDoses =(TimeOfDoses)intent.getSerializableExtra(Constants.TIMEOFDOSE_IN_ALARM);

        String msgTitle = medicines.getName();
        String msgTicker = timeOfDoses.getDosetime();
        String msgText = medicines.getDose_quantity()+" doses";

//        String msgTitle = "msg title";
//        String msgTicker = "ticker";
//        String msgText = "msg Text";

        // Open NotificationView Class on Notification Click
        Intent intnt = new Intent(context, MainActivity.class);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

//        mp=MediaPlayer.create(context, R.raw.alarmclock);
//        mp.start();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                                                .setSmallIcon(R.drawable.ic_notification)
                                                .setContentTitle(msgTitle)
                                                .setTicker(msgTicker)
                                                .setContentIntent(pIntent)
                                                .setContentText(msgText);


        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());


    }
}
