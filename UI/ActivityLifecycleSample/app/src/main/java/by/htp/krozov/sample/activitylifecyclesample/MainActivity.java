package by.htp.krozov.sample.activitylifecyclesample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private static final String STATE_TEXT = "text";

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.state_log);
        if (savedInstanceState != null) {
            mTextView.setText(savedInstanceState.getCharSequence(STATE_TEXT));
            mTextView.append("Activity recreated\n");
        } else {
            mTextView.append("Activity first creation\n");
        }
        mTextView.append("onCreate\n");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTextView.append("onStart\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTextView.append("onResume\n");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTextView.append("onPause\n");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTextView.append("onStop\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTextView.append("onDestroy\n");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear_log:
                mTextView.setText(null);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(STATE_TEXT, mTextView.getText());
    }
}
