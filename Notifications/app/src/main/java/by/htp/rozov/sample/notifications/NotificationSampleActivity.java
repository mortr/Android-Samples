package by.htp.rozov.sample.notifications;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import by.htp.rozov.sample.notifications.item.ActionNotifyItem;
import by.htp.rozov.sample.notifications.item.DeterminateNotify;
import by.htp.rozov.sample.notifications.item.IndeterminateNotify;
import by.htp.rozov.sample.notifications.item.NotifyItem;
import by.htp.rozov.sample.notifications.item.SimpleNotify;
import by.htp.rozov.sample.notifications.item.SimpleNotifyWithVibrate;

/**
 * @author Kirill Rozov
 * @since 10/4/14.
 */
public class NotificationSampleActivity extends ListActivity {

    private static final int NOTIFICATION_ID = 10414;

    private static final NotifyItem[] NOTIFY_ITEMS = new NotifyItem[]{
            new SimpleNotify(),
            new SimpleNotifyWithVibrate(),
            new IndeterminateNotify(),
            new DeterminateNotify(),
            new ActionNotifyItem()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, NOTIFY_ITEMS));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        NotifyItem notifyItem = (NotifyItem) l.getItemAtPosition(position);
        notifyItem.showNotification(this, NOTIFICATION_ID);
    }
}
