package contacts.vlas.htp.by.contactsapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

/**
 * @author Kirill Rozov
 * @since 07.02.15.
 */
public class BaseContactListActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                ContactActivity.start(
                        BaseContactListActivity.this,
                        (Contact) parent.getItemAtPosition(position)
                );
            }
        });
    }
}
