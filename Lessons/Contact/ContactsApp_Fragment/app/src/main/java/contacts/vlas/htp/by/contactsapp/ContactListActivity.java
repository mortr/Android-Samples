package contacts.vlas.htp.by.contactsapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

import contacts.vlas.htp.by.contactsapp.model.Contact;
import contacts.vlas.htp.by.contactsapp.phone.ContactActivity;

/**
 * @author Kirill Rozov
 * @since 04.03.15.
 */
public class ContactListActivity extends FragmentActivity
        implements ContactListFragment.Listener {

    private static final String TAG_CONTACT_FRAGMENT = "contact";

    private boolean mDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        mDualPane = findViewById(R.id.contact_fragment_container) != null;

        if (mDualPane) {
            FragmentManager fm = getSupportFragmentManager();
            ContactListFragment contactListFragment
                    = (ContactListFragment) fm.findFragmentById(R.id.contact_list_fragment);
            contactListFragment.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onContactItemClick(Contact contact) {
        if (mDualPane) {
            ContactFragment contactFragment = ContactFragment.newInstance(contact);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.contact_fragment_container, contactFragment, TAG_CONTACT_FRAGMENT)
                    .commit();
        } else {
            ContactActivity.start(this, contact);
        }
    }
}
