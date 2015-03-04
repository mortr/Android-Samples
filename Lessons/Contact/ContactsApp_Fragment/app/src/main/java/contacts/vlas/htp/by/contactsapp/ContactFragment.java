package contacts.vlas.htp.by.contactsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import contacts.vlas.htp.by.contactsapp.model.Contact;

/**
 * @author Kirill Rozov
 * @since 04.03.15.
 */
public class ContactFragment extends Fragment {

    private static final String ARG_CONTACT = "contact";

    public static ContactFragment newInstance(@NonNull Contact contact) {
        Bundle args = new Bundle(1);
        args.putSerializable(ARG_CONTACT, contact);

        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Contact mContact;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContact = (Contact) getArguments().getSerializable(ARG_CONTACT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);

        mNameView.setText(mContact.getName());
        mPhoneView.setText(mContact.getPhone());
        mEmailView.setText(mContact.getEmail());
        mAddressView.setText(mContact.getAddress());
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(getActivity());
        mBirthDateView.setText(dateFormat.format(mContact.getBirthDate()));
        mOccupationView.setText(mContact.getOccupation());
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }
}
