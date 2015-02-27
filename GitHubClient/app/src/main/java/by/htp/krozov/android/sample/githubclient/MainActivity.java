package by.htp.krozov.android.sample.githubclient;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import by.htp.krozov.android.sample.githubclient.model.Repo;
import by.htp.krozov.android.sample.githubclient.networking.GitHubServiceHolder;
import retrofit.RestAdapter;


public class MainActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<List<Repo>> {

    private ListView mListView;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(android.R.id.list);
        mProgressView = findViewById(android.R.id.progress);

        mProgressView.setVisibility(View.VISIBLE);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<Repo>> onCreateLoader(int id, Bundle args) {
        return new RepoLoader(this, "kirich1409");
    }

    @Override
    public void onLoadFinished(Loader<List<Repo>> loader, List<Repo> data) {
        mProgressView.setVisibility(View.GONE);
        mListView.setAdapter(new RepoAdapter(this, data));
    }

    @Override
    public void onLoaderReset(Loader<List<Repo>> loader) {
    }

    private static class RepoAdapter extends ArrayAdapter<Repo> {
        private RepoAdapter(Context context, List<Repo> objects) {
            super(context, android.R.layout.simple_list_item_2, android.R.id.text1, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }
            viewHolder.bind(getItem(position));
            return view;
        }

        private static class ViewHolder {
            private TextView mNameView;
            private TextView mDescriptionView;

            private ViewHolder(View itemVIew) {
                mNameView = (TextView) itemVIew.findViewById(android.R.id.text1);
                mDescriptionView = (TextView) itemVIew.findViewById(android.R.id.text2);
            }

            public void bind(Repo repo) {
                mNameView.setText(repo.getName());
                mDescriptionView.setText(repo.getDescription());
            }
        }
    }

    /**
     * Loader для загрузки списка репозиторие из сети.
     */
    private static class RepoLoader extends SimpleAsyncTaskLoader<List<Repo>> {

        private final String mUser;

        private RepoLoader(Context context, String user) {
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
}
