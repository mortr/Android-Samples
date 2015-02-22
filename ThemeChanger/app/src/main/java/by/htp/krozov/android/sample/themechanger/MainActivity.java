package by.htp.krozov.android.sample.themechanger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MainActivity extends ActionBarActivity {

    private static final String PREF_THEME_ID = "theme_id";

    private RadioGroup mThemesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int themeId = getPreferences(MODE_PRIVATE).getInt(PREF_THEME_ID, R.id.theme_dark);
        updateTheme(themeId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mThemesView = (RadioGroup) findViewById(R.id.themes);
        ((RadioButton) mThemesView.findViewById(themeId)).setChecked(true);
        findViewById(R.id.apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTheme();
            }
        });
    }

    void updateTheme() {
        updateTheme(mThemesView.getCheckedRadioButtonId());
        finish();
        startActivity(new Intent(this, this.getClass()));
    }

    private void updateTheme(@IdRes int themeId) {
        final SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        switch (themeId) {
            case R.id.theme_dark:
                setTheme(R.style.Theme_AppCompat);
                editor.putInt(PREF_THEME_ID, R.id.theme_dark);
                break;

            case R.id.theme_light:
                setTheme(R.style.Theme_AppCompat_Light);
                editor.putInt(PREF_THEME_ID, R.id.theme_light);
                break;

            case R.id.theme_light_dark_action_bar:
                setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
                editor.putInt(PREF_THEME_ID, R.id.theme_light_dark_action_bar);
                break;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
