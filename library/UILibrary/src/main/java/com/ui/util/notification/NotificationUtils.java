package com.ui.util.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {

    private Context context;

    private NotificationManager notificationManager;
    private Notification callingNotification;
    private String mChannelId;

    public NotificationUtils(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void init(String NIM_CHANNEL_ID, String NIM_CHANNEL_NAME, String NIM_CHANNEL_DESC) {
        NotificationChannelCompat26 notificationChannelCompat26 = new NotificationChannelCompat26(NIM_CHANNEL_ID, NIM_CHANNEL_NAME, NIM_CHANNEL_DESC);
        notificationChannelCompat26.createNIMMessageNotificationChannel(context);
        mChannelId = notificationChannelCompat26.getNIMChannelId(context);
    }

    public void buildNotification(PendingIntent pendingIntent, String title, String content,
                                  int iconId, boolean ring, boolean vibrate, int pri) {
        if (callingNotification == null) {

            callingNotification = makeNotification(pendingIntent, title, content,
                    iconId, ring, vibrate, pri);
        }
    }

    public void buildQuickNotification(PendingIntent pendingIntent, String title, String content,
                                       int iconId, int pri) {
        if (callingNotification == null) {

            callingNotification = makeNotification(pendingIntent, title, content,
                    iconId, true, true, pri);
        }
    }

    private Notification makeNotification(PendingIntent pendingIntent, String title, String content,
                                          int iconId, boolean ring, boolean vibrate, int pri) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, mChannelId);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(iconId)
                // 设置该通知优先级
                .setPriority(pri);
        int defaults = Notification.DEFAULT_LIGHTS;
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (ring) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        builder.setDefaults(defaults);
//        builder.setFullScreenIntent(pendingIntent, true);

        return builder.build();
    }

    public void activeNotification(boolean active, int CALLING_NOTIFY_ID) {
        if (notificationManager != null) {
            if (active) {
                if (callingNotification != null) {
                    notificationManager.notify(CALLING_NOTIFY_ID, callingNotification);
                }
            } else {
                notificationManager.cancel(CALLING_NOTIFY_ID);
            }
        }
    }

}
