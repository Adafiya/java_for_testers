package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactModificationTests extends TestBase {

  @Test
  void canModifyContact() {
    if (app.contact().getCount() == 0) {
      app.contact().createContact(
          new ContactData("", "firstname", "middlename", "lastname"));
    }
    var oldContacts = app.contact().getList();
    var rnd = new Random();
    var index = rnd.nextInt(oldContacts.size());
    var testData = new ContactData().withLastname("lastname_modified");
    app.contact().modifyContact(oldContacts.get(index), testData);
    var newContacts = app.contact().getList();
    var expectedList = new ArrayList<>(oldContacts);
    expectedList.set(index,
        testData.withId(oldContacts.get(index).id()).withMiddlename(""));
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newContacts.sort(compareById);
    expectedList.sort(compareById);
    Assertions.assertEquals(newContacts, expectedList);
  }
}
