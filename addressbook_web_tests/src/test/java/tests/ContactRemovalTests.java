package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase {

  @Test
  public void canRemoveContact() {
    if (!app.contact().isContactPresent()) {
      app.contact().createContact(
          new ContactData("firstname_remove", "middlename_remove", "lastname_remove"));
    }

    app.contact().removeContact();
  }

}
