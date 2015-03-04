package contacts.vlas.htp.by.contactsapp.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import contacts.vlas.htp.by.contactsapp.R;
import contacts.vlas.htp.by.contactsapp.model.Contact;

/**
 * Created by User on 06.02.2015.
 */
public class ContactAdapter extends BaseAdapter {

    List<Contact> mContacts;

    public ContactAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mContacts.get(position).getId();
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View itemView;
        ViewHolder vh;
        if (convertView == null) {
            itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_contact, parent, false);
            vh = new ViewHolder();
            vh.nameView = (TextView) itemView.findViewById(R.id.name);
            vh.phoneView = (TextView) itemView.findViewById(R.id.phone);
            itemView.setTag(R.id.tag, vh);
        } else {
            itemView = convertView;
            vh = (ViewHolder) itemView.getTag(R.id.tag);
        }

        vh.nameView.setText(getItem(position).getName());
        vh.phoneView.setText(getItem(position).getPhone());
        return itemView;
    }

    private class ViewHolder {
        TextView nameView;
        TextView phoneView;
    }
}
