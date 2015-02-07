package contacts.vlas.htp.by.contactsapp;


import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SimpleAdapterContactListActivity extends BaseContactListActivity {

    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";

    public static final String[] FROM = {KEY_NAME, KEY_PHONE};
    public static final int[] TO = {R.id.name, R.id.phone};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Contact> contacts = ContactStorage.getContacts();
        List<Map<String, CharSequence>> data = new ArrayList<>(contacts.size());
        for (Contact contact : contacts) {
            Map<String, CharSequence> item = new HashMap<>(2);
            item.put(KEY_NAME, contact.getName());
            item.put(KEY_PHONE, contact.getPhone());
            data.add(item);
        }
        setListAdapter(new SimpleAdapter(this, data, R.layout.item_contact, FROM, TO));
    }
}
