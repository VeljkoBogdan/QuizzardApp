package com.veljkobogdan.quizzardapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.veljkobogdan.quizzardapp.R;

public class EventReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String eventTitle = intent.getStringExtra("EVENT_TITLE");

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "event_reminder_channel";

        NotificationChannel channel = new NotificationChannel(
                channelId, "Event Reminders",
                NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("Event Reminder")
                .setContentText("Don't forget: " + eventTitle)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .build();

        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }
}
