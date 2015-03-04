package contacts.vlas.htp.by.contactsapp.phone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import butterknife.ButterKnife;
import contacts.vlas.htp.by.contactsapp.ContactFragment;
import contacts.vlas.htp.by.contactsapp.R;
import contacts.vlas.htp.by.contactsapp.model.Contact;

/**
 * Created by _guest on 02.02.2015.
 */
public class ContactActivity extends FragmentActivity {

    private static final String TAG_CONTACT_FRAGMENT = "contact";
    public static final String EXTRA_CONTACT = "contact";

    public static void start(Activity activity, Contact contact) {
        Intent intent = new Intent(activity, ContactActivity.class);
        intent.putExtra(EXTRA_CONTACT, contact);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.inject(this);

        Contact contact = (Contact) getIntent().getSerializableExtra(EXTRA_CONTACT);
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Проверяем не был ли уже добавлен ContactFragment в Activity
        ContactFragment contactFragment =
                (ContactFragment) fragmentManager.findFragmentByTag(TAG_CONTACT_FRAGMENT);
        if (contactFragment == null) {
            contactFragment = ContactFragment.newInstance(contact);
            fragmentManager.beginTransaction()
                    .add(android.R.id.content, contactFragment, TAG_CONTACT_FRAGMENT)
                    .commit();
        }
    }
}
