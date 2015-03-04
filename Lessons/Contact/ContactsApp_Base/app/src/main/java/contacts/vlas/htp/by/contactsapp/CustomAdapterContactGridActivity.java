package contacts.vlas.htp.by.contactsapp;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

public class CustomAdapterContactGridActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        GridView gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new ContactAdapter(ContactStorage.getContacts()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                ContactActivity.start(
                        CustomAdapterContactGridActivity.this,
                        (Contact) parent.getItemAtPosition(position)
                );
            }
        });
    }
}
