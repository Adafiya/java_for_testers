package tests;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactInfoTests extends TestBase {

  //Тест проверки телефонов
  @Test
  void testPhones() {
    if (app.hbm().getContactCount() == 0) {
      app.contact().createContact(
          new ContactData().withFirstname("firstname").withLastname("lastname").withHome("8976")
              .withMobile("7876").withAddress("Address").withEmail("Email").withEmail2("Email2"));
      //app.hbm().createContact(new ContactData("", "firstname", "middlename", "lastname", "", "2345", "7890", "", "", "Address", "Email", "", ""));
    }
    var contacts = app.hbm().getContactList();
    var expected = contacts.stream().collect(
        Collectors.toMap(ContactData::id, //contact -> contact.id() тоже самое что и ContactData::id
            contact -> Stream.of(contact.home(), contact.mobile(), contact.work(),
                    contact.secondary())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n")))); //преобразуем список контактов в map
    var phones = app.contact().getPhones(); //создаем пары ключ-значение для id и телефонов
    Assertions.assertEquals(expected, phones);
    /*for (var contact : contacts) {
      //var phones = app.contact().getPhones(contact);
      var expected = Stream.of(contact.home(), contact.mobile(), contact.work(),
              contact.secondary())
          .filter(s -> s != null && !"".equals(s))
          .collect(Collectors.joining("\n"));
      Assertions.assertEquals(expected, phones.get(contact.id()));
    }
     */
  }

  //Тест проверки почтового адреса
  @Test
  void testAddress() {
    if (app.hbm().getContactCount() == 0) {
      app.contact().createContact(
          new ContactData().withFirstname("firstname").withLastname("lastname").withHome("8976")
              .withMobile("7876").withAddress("Address").withEmail("Email").withEmail2("Email2"));
    }
    //Формируем список контактов
    var contacts = app.hbm().getContactList();
    //Выбираем рандомный контакт
    var rnd = new Random();
    var index = rnd.nextInt(contacts.size());
    var contact = contacts.get(index);
    var expected = Stream.of(contact.address())
        .filter(s -> s != null && !"".equals(s))
        .collect(Collectors.joining("\n")); //достаем адрес 
    var address = app.contact().getAddress(contact); //создаем пару ключ-значение для id и адрес
    Assertions.assertEquals(expected, address);
  }

  //Тест проверки адресов электронной почты
  @Test
  void testEmail() {
    if (app.hbm().getContactCount() == 0) {
      app.contact().createContact(
          new ContactData().withFirstname("firstname").withLastname("lastname").withHome("8976")
              .withMobile("7876").withAddress("Address").withEmail("Email").withEmail2("Email2"));
    }
    //Формируем список контактов
    var contacts = app.hbm().getContactList();
    //Выбираем рандомный контакт
    var rnd = new Random();
    var index = rnd.nextInt(contacts.size());
    var contact = contacts.get(index);
    var expected = Stream.of(contact.email(), contact.email2(), contact.email3())
        .filter(s -> s != null && !"".equals(s))
        .collect(Collectors.joining("\n")); //достаем Email
    var emails = app.contact().getEmails(contact); //создаем пару ключ-значение для id и Email
    Assertions.assertEquals(expected, emails);
  }
}
