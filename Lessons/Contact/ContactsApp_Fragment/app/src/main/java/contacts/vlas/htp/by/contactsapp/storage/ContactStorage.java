package contacts.vlas.htp.by.contactsapp.storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import contacts.vlas.htp.by.contactsapp.model.Contact;

/**
 * @author Kirill Rozov
 * @since 07.02.15.
 */
public final class ContactStorage {

    private static List<Contact> sContacts = new ArrayList<>(10);

    static {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTimeInMillis(Math.abs(random.nextLong()));

            Contact contact = new Contact();
            contact.setName("Contact " + i);
            contact.setPhone(
                    String.valueOf(
                            random.nextInt(1000000) + 6000000
                    )
            );
            contact.setAddress("Skoriny 58");
            contact.setEmail("ivan@gmail.com");
            contact.setBirthDate(sCalendar.getTime());
            contact.setOccupation("Android Developer");
            sContacts.add(contact);
        }
    }

    public static List<Contact> getContacts() {
        return sContacts;
    }
}
