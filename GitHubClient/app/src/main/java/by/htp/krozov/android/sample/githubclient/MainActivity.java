package by.htp.krozov.android.sample.githubclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import by.htp.krozov.android.sample.githubclient.model.Repo;
import by.htp.krozov.android.sample.githubclient.networking.GitHubServiceHolder;


public class MainActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<List<Repo>> {

    private static final int REPO_LOADER_ID = 100;
    private static final IntentFilter NETWORK_STATE_INTENT_FILTER
            = new IntentFilter();

    static {
        NETWORK_STATE_INTENT_FILTER.addAction(
                ConnectivityManager.CONNECTIVITY_ACTION);
    }

    private ListView mListView;
    private View mProgressView;
    private NetworkStateBroadcastReceiver mNetworkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(android.R.id.list);
        mProgressView = findViewById(android.R.id.progress);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (NetworkUtils.hasInterneConnection(this)) {
            loadRepos();
        } else {
            mNetworkStateReceiver = new NetworkStateBroadcastReceiver();
            registerReceiver(mNetworkStateReceiver, NETWORK_STATE_INTENT_FILTER);
        }
    }

    @Override
    protected void onPause() {
        unregisterNetworkStateReceiver();

        super.onPause();
    }

    private void unregisterNetworkStateReceiver() {
        if (mNetworkStateReceiver != null) {
            unregisterReceiver(mNetworkStateReceiver);
            mNetworkStateReceiver = null;
        }
    }

    void loadRepos() {
        mProgressView.setVisibility(View.VISIBLE);
        getSupportLoaderManager().initLoader(REPO_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Repo>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case REPO_LOADER_ID:
                return new RepoLoader(this, "kirich1409");

            default:
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<List<Repo>> loader, List<Repo> data) {
        switch (loader.getId()) {
            case REPO_LOADER_ID:
                mProgressView.setVisibility(View.GONE);
                mListView.setAdapter(new RepoAdapter(this, data));
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Repo>> loader) {
    }

    /**
     * Loader для загрузки списка репозиторие из сети.
     */
    static class RepoLoader extends SimpleAsyncTaskLoader<List<Repo>> {

        private final String mUser;

        RepoLoader(Context context, String user) {
            super(context);
            mUser = user;
        }

        @Override
        public List<Repo> loadInBackground() {
            try {
                return GitHubServiceHolder.getService().listRepos(mUser);
            } catch (Exception e) {
                return Collections.emptyList();
            }
        }
    }

    class NetworkStateBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkUtils.hasInterneConnection(context)) {
                unregisterNetworkStateReceiver();
                loadRepos();
            }
        }
    }
}
