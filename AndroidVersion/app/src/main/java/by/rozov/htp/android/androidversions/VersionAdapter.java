package by.rozov.htp.android.androidversions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by krozov on 12.08.14.
 */
public class VersionAdapter extends BaseAdapter {
    private static final String TAG = "VersionAdapter";

    private AndroidVersion[] mVersions;
    private final Context mContext;

    public VersionAdapter(Context context, AndroidVersion[] versions) {
        mContext = context;
        mVersions = versions;
    }

    @Override
    public int getCount() {
        return mVersions.length;
    }

    @Override
    public AndroidVersion getItem(int position) {
        return mVersions[position];
    }

    @Override
    public long getItemId(int position) {
        return mVersions[position].getCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView;
        //Проверяем новые ли элемент создается.
        if (convertView == null) {// Новый элемент
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_version, parent, false);
            Log.d(TAG, "Create new view in position " + position);
        } else { // Переиспользуем старый.
            itemView = convertView;
            Log.d(TAG, "Reuse view in position " + position);
        }

        //Получаем данные, соответствующие позиции списка.
        AndroidVersion item = mVersions[position];

        //Отображаем данные.
        TextView versionNameView = (TextView) itemView.findViewById(R.id.version_name);
        versionNameView.setText(mContext.getString(R.string.version_name_format,
                item.getReleaseNumber(), item.getName()));

        TextView versionCodeView = (TextView) itemView.findViewById(R.id.version_code);
        versionCodeView.setText(mContext.getString(R.string.api_level_format, item.getCode()));

        return itemView;
    }
}
