package contacts.vlas.htp.by.contactsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import contacts.vlas.htp.by.contactsapp.model.Contact;
import contacts.vlas.htp.by.contactsapp.storage.ContactStorage;
import contacts.vlas.htp.by.contactsapp.widget.ContactAdapter;

/**
 * @author Kirill Rozov
 * @since 04.03.15.
 */
public class ContactListFragment extends ListFragment {

    private Listener mListener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListAdapter(new ContactAdapter(ContactStorage.getContacts()));
        setEmptyText("Нет контактов");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mListener != null) {
            Contact contact = (Contact) l.getItemAtPosition(position);
            mListener.onContactItemClick(contact);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof Listener) {
            mListener = (Listener) activity;
        } else {
            throw new ClassCastException("Host Activity must implement interface" +
                    "ContactListFragment.Listener.");
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onContactItemClick(Contact contact);
    }
}
