package by.htp.krozov.linkviewer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    public static final String SCHEME_HTTP = "http://";
    private EditText mLinkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLinkView = (EditText) findViewById(R.id.link);

        mLinkView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    openUrl(((TextView) v).getText().toString());
                    return true;
                } else {
                    return false;
                }
            }
        });

        findViewById(R.id.open).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openUrl(mLinkView.getText().toString());
                    }
                }
        );
    }

    void openUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(this, R.string.msg_empty_url, Toast.LENGTH_SHORT)
                    .show();
        } else {
            Uri data = Uri.parse(url);
            if (TextUtils.isEmpty(data.getScheme())) {
                Uri.Builder builder = data.buildUpon();
                builder.scheme("http");
                data = builder.build();
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, data);
            if (canResolve(intent)) {
                Log.d(TAG, "Open link '" + data.toString() + "'.");
                hideKeyboard();
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.msg_now_browsers, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    void hideKeyboard() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromInputMethod(
                        mLinkView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    boolean canResolve(Intent intent) {
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfos != null && !resolveInfos.isEmpty();
    }
}
