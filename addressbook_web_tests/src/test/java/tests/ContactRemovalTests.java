package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase {

  @Test
  public void canRemoveContact() {
    if (app.hbm().getContactCount() == 0) {
      app.hbm().createContact(
          new ContactData("", "firstname", "middlename", "lastname", ""));
    }
    var oldContacts = app.hbm().getContactList();
    var rnd = new Random();
    var index = rnd.nextInt(oldContacts.size());
    app.contact().removeContact(oldContacts.get(index));
    var newContacts = app.hbm().getContactList();
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

  //Тест удаления контакта в группу
  @Test
  public void removeContactToGroup() {
    //Создаем контакт через БД, если его нет
    if (app.hbm().getContactCount() == 0) {
      app.hbm().createContact(
          new ContactData("", "firstname", "middlename", "lastname", ""));
    }
    //Создание группы через БД, если ее нет
    if (app.hbm().getGroupCount() == 0) {
      app.hbm().createGroup(new GroupData("", "group name", "group header", "group_footer"));
    }
    //Выбираем группу, в которую будет включен контакт
    var group = app.hbm().getGroupList().get(0);
    var oldContacts = app.hbm().getContactList();
    var rndContact = new Random();
    var indexContact = rndContact.nextInt(oldContacts.size());
    app.contact().removeContactFromGroup(oldContacts.get(indexContact), group);
    var newContacts = app.hbm().getContactList();
    Assertions.assertEquals(oldContacts.size(),
        newContacts.size()); //Проверка что количество контактов не изменилось. Переделать на полноценную проверку со всем содержимым
  }
}
