package com.example.customgraph.tracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Admin on 27-06-2018.
 */

public class AlarmReceiver extends BroadcastReceiver {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i=new Intent(context,CalendarActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi=PendingIntent.getActivity(context,100,i,PendingIntent.FLAG_UPDATE_CURRENT);

        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notification=new NotificationCompat.Builder(context,"1")
                .setContentTitle("StudyTracker")
                .setContentText("It is time to tell how did you study!!!")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.alert_light_frame)
                .setChannelId("1")
                .setSound(sound)
                .setDefaults(Notification.DEFAULT_VIBRATE);
        notificationManager.notify(1,notification.build());

    }
}
