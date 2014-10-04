package by.htp.rozov.sample.downloadmanager;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity implements View.OnClickListener {

    private static final String STATE_DOWNLOAD_ID = "downloadId";

    private Button mStartDownloadButton;
    private EditText mUriEditText;
    private Button mOpenFileButton;

    private DownloadManager mDownloadManager;

    static long mDownloadId = Long.MIN_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        if (savedInstanceState != null) {
            mDownloadId = savedInstanceState.getLong(STATE_DOWNLOAD_ID);
        }

        setContentView(R.layout.activity_main);

        mStartDownloadButton = (Button) findViewById(R.id.start_download);
        mUriEditText = (EditText) findViewById(R.id.uri);
        mOpenFileButton = (Button) findViewById(R.id.open_file);

        mStartDownloadButton.setOnClickListener(this);
        mOpenFileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_file:
                try {
                    mDownloadManager.openDownloadedFile(mDownloadId);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.start_download:
                String uriString = mUriEditText.getText().toString();
                if (validateUri(uriString)) {
                    mUriEditText.setEnabled(false);
                    mStartDownloadButton.setEnabled(false);

                    Uri uri = Uri.parse(uriString);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setAllowedOverRoaming(false);
                    request.setNotificationVisibility(
                            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setTitle(getString(R.string.download_title));
                    request.setAllowedOverRoaming(false);
                    request.setVisibleInDownloadsUi(false);

                    mDownloadId = mDownloadManager.enqueue(request);
                } else {
                    Toast.makeText(this, R.string.wrong_uri_format, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(STATE_DOWNLOAD_ID, mDownloadId);
    }

    private static boolean validateUri(String uri) {
        URL url;
        try {
            url = new URL(uri);
        } catch (Exception e1) {
            return false;
        }
        return "http".equals(url.getProtocol());
    }
}
