package com.taz.accessability.meditrack.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.taz.accessability.meditrack.R;

/**
 * Created by next on 12/6/17.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {

        String msgTitle = "Medicines";
        String msgTicker = "aaaaaaa";
        String msgText = "aaaa aaaaa aaaaaa";

//        mp=MediaPlayer.create(context, R.raw.alrm   );
//        mp.start();
//        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                                                .setSmallIcon(R.drawable.ic_notification)
                                                .setContentTitle(msgTitle)
                                                .setTicker(msgTicker)
                                                .setContentText(msgText);


        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());


    }
}
