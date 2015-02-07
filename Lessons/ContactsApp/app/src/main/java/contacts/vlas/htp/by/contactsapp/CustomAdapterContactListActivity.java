package contacts.vlas.htp.by.contactsapp;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CustomAdapterContactListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new ContactAdapter(ContactStorage.getContacts()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                ContactActivity.start(
                        CustomAdapterContactListActivity.this,
                        (Contact) parent.getItemAtPosition(position)
                );
            }
        });
    }
}
