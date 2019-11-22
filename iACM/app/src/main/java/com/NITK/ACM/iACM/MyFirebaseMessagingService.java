package com.NITK.ACM.iACM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static String msg;
    public static String eventname;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("remotemsg", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("payload", "Message data payload: " + remoteMessage.getData());


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("messageee", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            eventname=remoteMessage.getNotification().getBody();
            if(remoteMessage.getNotification().getTitle().equals("Reminder"))
                msg="Reminder";

            else
                msg="New";

            addNotification();

        }
        else {
            Log.i("message", "null");
        }


    }

    @Override
    public void onDeletedMessages() {
        Log.i("del", "message");
    }


    public void addNotification() {
        Log.i("make", "notif");

        if(AddEvent.event.getTitle()==null)
            Log.i("event",EventAdaptor.e.getTitle());

        if(AddEvent.event.getTitle()!=null || EventAdaptor.e.getTitle()!=null) {

            NotificationCompat.Builder mBuilder;
            if(msg.equals("New")) {
                mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.acm_logo)
                                .setContentTitle("New Event!")
                                .setContentText(eventname);
            }

            else
            {
                mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.acm_logo)
                                .setContentTitle("Reminder!")
                                .setContentText(eventname);
            }
            Intent resultIntent = new Intent(this, events.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(events.class);

            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager =

                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "YOUR_CHANNEL_ID";
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager.createNotificationChannel(channel);
                mBuilder.setChannelId(channelId);
            }

            if (AddEvent.event != null)
                notificationManager.notify(001, mBuilder.build());

            Log.i("notf", "done");
        }
    }


}