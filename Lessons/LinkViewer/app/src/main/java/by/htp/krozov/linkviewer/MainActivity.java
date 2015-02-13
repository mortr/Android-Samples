package by.htp.krozov.linkviewer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends Activity {

    public static final String SCHEME_HTTP = "http";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText linkView = (EditText) findViewById(R.id.link);
        findViewById(R.id.open).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(linkView.getText())) {
                            Toast.makeText(MainActivity.this, R.string.msg_empty_url, Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Uri data = Uri.parse(linkView.getText().toString());
                            if (TextUtils.isEmpty(data.getScheme())) {
                                Uri.Builder builder = data.buildUpon();
                                builder.scheme(SCHEME_HTTP);
                                data = builder.build();
                            }

                            Intent intent = new Intent(Intent.ACTION_VIEW, data);
                            if (canResolve(intent)) {
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, R.string.msg_now_browsers, Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    }
                }
        );
    }

    boolean canResolve(Intent intent) {
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfos == null || resolveInfos.isEmpty();
    }
}
