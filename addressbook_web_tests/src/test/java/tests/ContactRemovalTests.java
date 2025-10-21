package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase {

  @Test
  public void canRemoveContact() {
    if (app.contact().getCount() == 0) {
      app.contact().createContact(
          new ContactData("", "firstname_remove", "middlename_remove", "lastname_remove", ""));
    }
    var oldContacts = app.contact().getList();
    var rnd = new Random();
    var index = rnd.nextInt(oldContacts.size());
    app.contact().removeContact(oldContacts.get(index));
    var newContacts = app.contact().getList();
    var expectedList = new ArrayList<>(oldContacts);
    expectedList.remove(index);
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newContacts.sort(compareById);
    expectedList.sort(compareById);
    Assertions.assertEquals(newContacts, expectedList);
  }

  @Test
  public void canRemoveAllContactsAtOnce() {
    if (app.contact().getCount() == 0) {
      app.contact().createContact(
          new ContactData("", "firstname_remove", "middlename_remove", "lastname_remove", ""));
    }
    app.contact().removeAllContacts();
    Assertions.assertEquals(0, app.contact().getCount());
  }
}
