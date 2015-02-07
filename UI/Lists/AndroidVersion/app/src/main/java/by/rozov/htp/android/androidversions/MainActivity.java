package by.rozov.htp.android.androidversions;

import android.app.ListActivity;
import android.os.Bundle;

/**
 * Created by krozov on 12.08.14.
 */
public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidVersion[] versions = new AndroidVersion[]{
                new AndroidVersion(this, R.string.android_1_0_version_name,
                        R.integer.android_1_0_version_code,
                        R.string.android_1_0_release),

                new AndroidVersion(this, R.string.android_1_1_version_name,
                        R.integer.android_1_1_version_code,
                        R.string.android_1_1_release),

                new AndroidVersion(this, R.string.android_1_5_version_name,
                        R.integer.android_1_5_version_code,
                        R.string.android_1_5_release),

                new AndroidVersion(this, R.string.android_1_6_version_name,
                        R.integer.android_1_6_version_code,
                        R.string.android_1_6_release),

                new AndroidVersion(this, R.string.android_2_0_version_name,
                        R.integer.android_2_0_version_code,
                        R.string.android_2_0_release),

                new AndroidVersion(this, R.string.android_2_1_version_name,
                        R.integer.android_2_1_version_code,
                        R.string.android_2_1_release),

                new AndroidVersion(this, R.string.android_2_2_version_name,
                        R.integer.android_2_2_version_code,
                        R.string.android_2_2_release),

                new AndroidVersion(this, R.string.android_2_3_version_name,
                        R.integer.android_2_3_version_code,
                        R.string.android_2_3_release),

                new AndroidVersion(this, R.string.android_2_3_3_version_name,
                        R.integer.android_2_3_3_version_code,
                        R.string.android_2_3_3_release),

                new AndroidVersion(this, R.string.android_3_1_version_name,
                        R.integer.android_3_1_version_code,
                        R.string.android_3_1_release),

                new AndroidVersion(this, R.string.android_3_2_version_name,
                        R.integer.android_3_2_version_code,
                        R.string.android_3_2_release),

                new AndroidVersion(this, R.string.android_4_0_version_name,
                        R.integer.android_4_0_version_code,
                        R.string.android_4_0_release),

                new AndroidVersion(this, R.string.android_4_0_3_version_name,
                        R.integer.android_4_0_3_version_code,
                        R.string.android_4_0_3_release),

                new AndroidVersion(this, R.string.android_4_1_version_name,
                        R.integer.android_4_1_version_code,
                        R.string.android_4_1_release),

                new AndroidVersion(this, R.string.android_4_2_version_name,
                        R.integer.android_4_2_version_code,
                        R.string.android_4_2_release),

                new AndroidVersion(this, R.string.android_4_3_version_name,
                        R.integer.android_4_3_version_code,
                        R.string.android_4_3_release),

                new AndroidVersion(this, R.string.android_4_4_version_name,
                        R.integer.android_4_4_version_code,
                        R.string.android_4_4_release),
        };

        VersionAdapter adapter = new VersionAdapter(this, versions);
        setListAdapter(adapter);
    }
}
