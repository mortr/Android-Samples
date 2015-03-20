package com.htp.krozov.userwordsdispalying;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserWordsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID_USER_WORDS = 1;

    private static final String[] ADAPTER_FROM = {UserDictionary.Words.WORD};
    private static final int[] ADAPTER_TO = {android.R.id.text1};

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_ID_USER_WORDS) {
            return new UserWordsLoader(getActivity());
        } else {
            throw new IllegalArgumentException(
                    String.format("Unknown loader id '%d'.", id));
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyText(getText(R.string.user_dictionary_empty));
        getLoaderManager().initLoader(LOADER_ID_USER_WORDS, null, this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor userWords) {
        ListAdapter listAdapter = getListAdapter();
        if (listAdapter instanceof SimpleCursorAdapter) {
            SimpleCursorAdapter userWordsAdapter =
                    (SimpleCursorAdapter) listAdapter;
            userWordsAdapter.swapCursor(userWords);
        } else {
            SimpleCursorAdapter userWordsAdapter = new SimpleCursorAdapter(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    userWords,
                    ADAPTER_FROM,
                    ADAPTER_TO,
                    0);
            setListAdapter(userWordsAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (getView() != null) {
            setListShown(false);
            setListAdapter(null);
        }
    }

    private static class UserWordsLoader extends CursorLoader {

        private static final Uri CONTENT_URI = UserDictionary.Words.CONTENT_URI;
        private static final String[] PROJECTION = {UserDictionary.Words._ID, UserDictionary.Words.WORD};
        private static final String SELECTION = null;
        private static final String[] SELECTION_ARGS = null;
        private static final String SORT_ORDER = UserDictionary.Words.WORD + " ASC";

        private UserWordsLoader(Context context) {
            super(context, CONTENT_URI,
                    PROJECTION, SELECTION, SELECTION_ARGS, SORT_ORDER);
        }

        @Override
        public Cursor loadInBackground() {
            try {
                // Имитируем длительную работу загрузки данных
                Thread.sleep(2000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return super.loadInBackground();
        }
    }
}
