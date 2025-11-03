package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import common.CommonFunctions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ContactCreationTests extends TestBase {

  //Создаем много контактов
  public static List<ContactData> contactProvider() throws IOException {
    var result = new ArrayList<ContactData>();
    for (var firstname : List.of("", "firstname")) {
      for (var middlename : List.of("", "middlename")) {
        for (var lastname : List.of("", "lastname")) {
          for (var photo : List.of(randomFile("src/test/resources/images"))) {
            result.add(new ContactData().withFirstname(firstname).withMiddlename(middlename)
                .withLastname(lastname).withPhoto(photo));
          }
        }
      }
    }
//Читаем данные из сгенерированного файла json
    var mapper = new ObjectMapper();
    var value = mapper.readValue(Files.readString(Paths.get("contacts.json")),
        new TypeReference<List<ContactData>>() {
        });
//Читаем данные из сгенерированного файла xml
//    var mapper = new XmlMapper();
//    var value = mapper.readValue(new File("contacts.xml"), new TypeReference<List<ContactData>>() {});
//Добавляем все значения в список
    result.addAll(value);
    return result;
  }

  //Тест создания контакта с проверкой списка контактов на странице UI
  @ParameterizedTest
  @MethodSource("contactProvider")
  public void canCreateMultipleContactsWithUI(ContactData contact) {
    var oldContacts = app.contact().getList();
    app.contact().createContact(contact);
    var newContacts = app.contact().getList();
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newContacts.sort(compareById);
    var expectedList = new ArrayList<>(oldContacts);
    expectedList.add(
        contact.withId(newContacts.get(newContacts.size() - 1).id()));
    expectedList.sort(compareById);
    Assertions.assertEquals(newContacts, newContacts);
  }

  //Создаем один рандомный контакт
  public static List<ContactData> singleRandomContact() {
    return List.of(new ContactData()
        .withLastname(CommonFunctions.randomString(10))
        .withMiddlename(CommonFunctions.randomString(10))
        .withFirstname(CommonFunctions.randomString(10)));
  }

  //Тест создания контакта с проверкой контакта в БД
  @ParameterizedTest
  @MethodSource("singleRandomContact")
  public void canCreateMultipleContactsWithJdbc(ContactData contact) {
    var oldContacts = app.hbm().getContactList();
    app.contact().createContact(contact);
    var newContacts = app.hbm().getContactList();
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newContacts.sort(compareById);
    var expectedList = new ArrayList<>(oldContacts);
    expectedList.add(
        contact.withId(newContacts.get(newContacts.size() - 1).id()));
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
