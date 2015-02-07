package contacts.vlas.htp.by.contactsapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Kirill Rozov
 * @since 07.02.15.
 */
public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ContactViewHolder> {

    private List<Contact> mContacts;

    public ContactRecyclerAdapter(List<Contact> contacts) {
        mContacts = new ArrayList<>(contacts);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder viewHolder, int position) {
        viewHolder.bind(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    @Override
    public void onViewRecycled(ContactViewHolder holder) {
        ButterKnife.reset(holder);
        super.onViewRecycled(holder);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.name)
        TextView nameView;

        @InjectView(R.id.phone)
        TextView phoneView;

        Contact mContact;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mContact != null) {
                        ContactActivity.start(v.getContext(), mContact);
                    }
                }
            });
        }

        public void bind(Contact contact) {
            mContact = contact;

            if (contact != null) {
                nameView.setText(contact.getName());
                phoneView.setText(contact.getPhone());
            } else {
                nameView.setText(null);
                phoneView.setText(null);
            }
        }
    }
}
