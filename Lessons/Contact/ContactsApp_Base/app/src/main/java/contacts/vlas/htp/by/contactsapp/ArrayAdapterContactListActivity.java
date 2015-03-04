package contacts.vlas.htp.by.contactsapp;


import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

public class ArrayAdapterContactListActivity extends BaseContactListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Contact> contacts = ContactStorage.getContacts();
        ArrayAdapter<Contact> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, android.R.id.text1, contacts
        );
        setListAdapter(arrayAdapter);
    }
}
