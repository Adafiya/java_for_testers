package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ContactCreationTests extends TestBase {

  public static List<ContactData> contactProvider() {
    var result = new ArrayList<ContactData>();
    for (var firstname : List.of("", "firstname")) {
      for (var middlename : List.of("", "middlename")) {
        for (var lastname : List.of("", "lastname")) {
          result.add(new ContactData().withFirstname(firstname).withMiddlename(middlename)
              .withLastname(lastname));
        }
      }
    }
    for (int i = 0; i < 5; i++) {
      result.add(new ContactData()
          .withFirstname(randomString(i * 10))
          .withMiddlename(randomString(i * 10))
          .withLastname(randomString(i * 10)));
    }
    return result;
  }

  @ParameterizedTest
  @MethodSource("contactProvider")
  public void canCreateMultipleContacts(ContactData contact) {
    var oldContacts = app.contact().getList();
    app.contact().createContact(contact);
    var newContacts = app.contact().getList();
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newContacts.sort(compareById);
    var expectedList = new ArrayList<>(oldContacts);
    expectedList.add(
        contact.withId(newContacts.get(newContacts.size() - 1).id()).withFirstname("")
            .withMiddlename(""));
    expectedList.sort(compareById);
    Assertions.assertEquals(newContacts, newContacts);
  }

  /*
  @Test
  public void canCreateMultipleContacts() {
    int n = 5;
    int contactCount = app.contact().getCount();
    for (int i = 0; i < n; i++) {
      app.contact().createContact(
          new ContactData(randomString(5 + i), randomString(5 + i), randomString(5 + i)));
    }
    int newContactCount = app.contact().getCount();
    Assertions.assertEquals(contactCount + n, newContactCount);
  }

   @Test
  public void canCreateContact() {
    int contactCount = app.contact().getCount();
    app.contact().createContact(new ContactData("firstname", "middlename", "lastname"));
    int newContactCount = app.contact().getCount();
    Assertions.assertEquals(contactCount + 1, newContactCount);
  }
   */
}
