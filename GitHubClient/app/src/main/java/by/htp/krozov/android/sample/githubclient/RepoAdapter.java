package by.htp.krozov.android.sample.githubclient;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import by.htp.krozov.android.sample.githubclient.model.Repo;

/**
* @author Kirill Rozov
* @since 27.02.15.
*/
public class RepoAdapter extends ArrayAdapter<Repo> {
    RepoAdapter(Context context, List<Repo> objects) {
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
