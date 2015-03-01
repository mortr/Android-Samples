package by.htp.krozov.android.sample.githubclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import by.htp.krozov.android.sample.githubclient.model.Repo;
import by.htp.krozov.android.sample.githubclient.networking.GitHubServiceHolder;


public class MainActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<List<Repo>> {

    private static final String STATE_REPOS_LOADED = "reposLoaded";

    // ID Loader для загрузки списка репозиториев
    private static final int REPO_LOADER_ID = 100;

    // IntentFilter изменения состояния сети
    private static final IntentFilter NETWORK_STATE_INTENT_FILTER
            = new IntentFilter();

    static {
        // Инициализация NETWORK_STATE_INTENT_FILTER
        // Задаем action при изменение состояния сети
        NETWORK_STATE_INTENT_FILTER.addAction(
                ConnectivityManager.CONNECTIVITY_ACTION);
    }

    public static final String GITHUB_USERNAME = "kirich1409";

    /**
     * Флаг об том был ли загружены репозитории
     */
    private boolean mReposLoaded;

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
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mReposLoaded = savedInstanceState.getBoolean(STATE_REPOS_LOADED, false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // При каждом показе Activity на экране запускаем загрузку списка репозиториев


        // Загружаем список репозиторие только в случае если он еще не был успешно загружен либо
        // не был загружен и есть сетевое подключение
        if (mReposLoaded || NetworkUtils.hasInternetConnection(this)) {
            loadRepos();
        } else {
            // Регистрируем BroadcastReceiver для наблюдением за измнением состояния сети
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
            mNetworkStateReceiver = new NetworkStateBroadcastReceiver();
            registerReceiver(mNetworkStateReceiver, NETWORK_STATE_INTENT_FILTER);
        }
    }

    @Override
    protected void onStop() {
        // При скрытие Activity с экрана отписываемся от событий изменения состояния сети
        // В противном случае мы можем получить Memory Leak
        unregisterNetworkStateReceiver();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                mReposLoaded = false;
                setProgressVisible(true);
                getSupportLoaderManager()
                        .restartLoader(REPO_LOADER_ID, null, this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_REPOS_LOADED, mReposLoaded);
    }

    /**
     * Отображаем прогресс. Одновременно может быть виден только спиоск репозиторие либо прогресс.
     */
    private void setProgressVisible(boolean visible) {
        if (visible) {
            mProgressView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mProgressView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Отмена регистрации BroadcastReceiver и его уничтожение.
     */
    private void unregisterNetworkStateReceiver() {
        if (mNetworkStateReceiver != null) {
            unregisterReceiver(mNetworkStateReceiver);
            mNetworkStateReceiver = null;
        }
    }

    void loadRepos() {
        setProgressVisible(true);
        getSupportLoaderManager().initLoader(REPO_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Repo>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case REPO_LOADER_ID:
                return new RepoLoader(this, GITHUB_USERNAME);

            default:
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<List<Repo>> loader, List<Repo> data) {
        switch (loader.getId()) {
            case REPO_LOADER_ID:
                mListView.setAdapter(new RepoAdapter(this, data));
                setProgressVisible(false);
                mReposLoaded = true;
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
            // При уведомление об имзенение состояния сети, необходимо проверить есть ли активное соединиение.
            if (NetworkUtils.hasInternetConnection(context)) {
                unregisterNetworkStateReceiver();
                loadRepos();
            }
        }
    }
}
