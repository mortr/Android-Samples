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
 * @since 10/4/14.
 */
public class DeterminateNotify extends NotifyItem {

    public DeterminateNotify() {
        super("Determinate notification");
    }

    @Override
    public void showNotification(Context context, int id) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(mTitle)
                .setProgress(100, 40, false)
                .setContentText("40% done")
                .setSmallIcon(R.drawable.ic_stat_htp);

        Intent activityIntent = new Intent(context, NotificationSampleActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);
        builder.setContentIntent(contentIntent);

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(id, builder.build());
    }
}
