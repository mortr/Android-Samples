package by.htp.rozov.sample.notifications.item;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import by.htp.rozov.sample.notifications.NotificationSampleActivity;
import by.htp.rozov.sample.notifications.R;

/**
 * @author Kirill Rozov
 * @since 03.03.15.
 */
public class ActionNotifyItem extends NotifyItem {

    public ActionNotifyItem() {
        super("Notification with Actions");
    }

    @Override
    public void showNotification(Context context, int id) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(mTitle)
                .setContentText("Sample content text")
                .setSmallIcon(R.drawable.ic_stat_htp);

        Intent activityIntent = new Intent(context, NotificationSampleActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);

        NotificationCompat.Action.Builder sampleActionBuilder =
                new NotificationCompat.Action.Builder(
                        android.R.drawable.ic_menu_close_clear_cancel, "Sample action", contentIntent);
        builder.addAction(sampleActionBuilder.build());
        builder.setContentIntent(contentIntent);

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(id, builder.build());
    }
}
