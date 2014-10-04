package by.htp.rozov.sample.notifications.item;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import by.htp.rozov.sample.notifications.NotificationSampleActivity;
import by.htp.rozov.sample.notifications.R;

/**
 * @author Kirill Rozov
 * @since 10/4/14.
 */
public class SimpleNotifyWithVibrate extends NotifyItem {

    public SimpleNotifyWithVibrate() {
        super("Simple notification with vibrate");
    }

    @Override
    public void showNotification(Context context, int id) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(mTitle)
                .setContentText("Sample content text")
                .setSmallIcon(R.drawable.ic_stat_htp)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

        Intent activityIntent = new Intent(context, NotificationSampleActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);
        builder.setContentIntent(contentIntent);

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(id, builder.build());
    }
}
