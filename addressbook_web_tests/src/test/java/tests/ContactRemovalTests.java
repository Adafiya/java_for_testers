package tests;

import static javax.swing.UIManager.get;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
          new ContactData("", "firstname", "middlename", "lastname", "", "", "", "", "", "", "", "",
              ""));
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

  /*
  @Test
  public void canRemoveAllContactsAtOnce() {
    if (app.contact().getCount() == 0) {
      app.contact().createContact(
          new ContactData("", "firstname_remove", "middlename_remove", "lastname_remove", "", "",
              "", "", "", "", "", "", ""));
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
          new ContactData("", "firstname", "middlename", "lastname", "", "", "", "", "", "", "", "",
              ""));
    }
    //Создание группы через БД, если ее нет
    if (app.hbm().getGroupCount() == 0) {
      app.hbm().createGroup(new GroupData("", "group name", "group header", "group_footer"));
    }
    //Выбираем группу, из которой будет исключен контакт
    var group = app.hbm().getGroupList().get(0);
    var contacts = app.hbm().getContactList();
    var rndContact = new Random();
    var indexContact = rndContact.nextInt(contacts.size());
    List<ContactData> oldRelated;
    //Проверка наличия контактов в выбранной группе и добавление контакта в группу, если его нет
    if (!app.hbm().getContactsInGroup(group).contains(get(indexContact))) {
      app.contact().addContactToGroup(contacts.get(indexContact), group);
      oldRelated = app.hbm().getContactsInGroup(group);
    } else {
      oldRelated = app.hbm().getContactsInGroup(group);
    }
    app.contact().removeContactFromGroup(contacts.get(indexContact), group);
    var newRelated = app.hbm().getContactsInGroup(group);
    Assertions.assertEquals(oldRelated.size() - 1,
        newRelated.size()); //Проверка что кол-во связей стало на 1 меньше
  }
   */
}
