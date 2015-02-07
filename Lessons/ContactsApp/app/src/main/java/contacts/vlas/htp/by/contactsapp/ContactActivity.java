package contacts.vlas.htp.by.contactsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by _guest on 02.02.2015.
 */
public class ContactActivity extends Activity {

    public static final String EXTRA_CONTACT = "contact";

    public static void start(Context from, Contact contact) {
        Intent intent = new Intent(from, ContactActivity.class);
        intent.putExtra(ContactActivity.EXTRA_CONTACT, contact);
        from.startActivity(intent);
    }

    @InjectView(R.id.name)
    TextView mNameView;

    @InjectView(R.id.phone)
    TextView mPhoneView;

    @InjectView(R.id.email)
    TextView mEmailView;

    @InjectView(R.id.address)
    TextView mAddressView;

    @InjectView(R.id.birthdate)
    TextView mBirthDateView;

    @InjectView(R.id.occupation)
    TextView mOccupationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.inject(this);
        Contact contact =
                (Contact) getIntent().getSerializableExtra(EXTRA_CONTACT);
        init(contact);
    }

    private void init(Contact contact) {
        getActionBar().setTitle(contact.getName());
        mNameView.setText(contact.getName());
        mPhoneView.setText(contact.getPhone());
        mEmailView.setText(contact.getEmail());
        mAddressView.setText(contact.getAddress());
        mBirthDateView.setText(DateFormat.getDateFormat(this).format(contact.getBirthDate()));
        mOccupationView.setText(contact.getOccupation());
    }
}
