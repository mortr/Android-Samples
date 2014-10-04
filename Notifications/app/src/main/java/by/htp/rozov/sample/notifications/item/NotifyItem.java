package by.htp.rozov.sample.notifications.item;

import android.content.Context;

/**
 * @author Kirill Rozov
 * @since 10/4/14.
 */
public abstract class NotifyItem {
    protected final String  mTitle;

    protected NotifyItem(String title) {
        mTitle = title;
    }

    public abstract void showNotification(Context context, int id);

    @Override
    public String toString() {
        return mTitle;
    }
}
