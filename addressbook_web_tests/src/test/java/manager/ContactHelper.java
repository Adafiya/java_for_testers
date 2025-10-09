package manager;

import java.util.ArrayList;
import java.util.List;
import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;

public class ContactHelper extends HelperBase {

  public ContactHelper(ApplicationManager manager) {
    super(manager);
  }

  //Открыть страницу контактов
  public void openContactPage() {
    click(By.linkText("home"));
  }

  /*
  //Проверить наличие контактов
  public boolean isContactPresent() {
    openContactPage();
    return manager.isElementPresent(By.name("selected[]"));
  }
  */

  //Создать контакт
  public void createContact(ContactData contact) {
    openContactPage();
    initContactCreation();
    fillContactForm(contact);
    submitContactCreation();
    openContactPage();
  }

  //Удалить контакт
  public void removeContact(ContactData contact) {
    openContactPage();
    selectContact(contact);
    removeSelectedContacts();
    openContactPage();
  }


  //Редактировать контакт
  public void modifyGroup(ContactData contact, ContactData modifiedContact) {
    openContactPage();
    selectContact(contact);
    initContactModification();
    fillContactForm(modifiedContact);
    submitContactModification();
    openContactPage();
  }

  //Подтвердить создание контакта
  private void submitContactCreation() {
    click(By.xpath("(//input[@name=\'submit\'])[2]"));
  }

  //Нажать кнопку создания нового контакта
  private void initContactCreation() {
    click(By.linkText("add new"));
  }

  //Нажать кнопку удаления контакта
  private void removeSelectedContacts() {
    click(By.name("delete"));
  }

  //Заполнить форму контактов
  private void fillContactForm(ContactData contact) {
    type(By.name("firstname"), contact.firstname());
    type(By.name("middlename"), contact.middlename());
    type(By.name("lastname"), contact.lastname());
  }

  //Выбрать контакт
  private void selectContact(ContactData contact) {
    click(By.cssSelector(String.format("input[value='%s']", contact.id())));
  }

  //Считать сколько было контактов
  public int getCount() {
    openContactPage();
    return manager.driver.findElements(By.name("selected[]")).size();
  }

  //Удальть все группы разом
  public void removeAllContacts() {
    selectAllContacts();
    removeSelectedContacts();
    openContactPage();
  }

  //Выбор всех контактов
  private void selectAllContacts() {
    var checkboxes = manager.driver.findElements(By.name("selected[]"));
    for (var checkbox : checkboxes) {
      checkbox.click();
    }
  }

  //Нажать кнопку редактирования контакта
  private void initContactModification() {
    click(By.xpath("//img[@alt=\'Edit\']"));
  }

  //Нажать кнопку подтверждения обновиления контакта
  private void submitContactModification() {
    click(By.xpath("(//input[@name=\'update\'])[2]"));
  }

  //Получить список всех контактов (их id и name)
  public List<ContactData> getList() {
    openContactPage();
    var contacts = new ArrayList<ContactData>();
    var checkboxes = manager.driver.findElements(By.name("selected[]"));
    for (var checkbox : checkboxes) {
      var id = checkbox.getAttribute("value");
//      var name = checkbox.getAttribute("title");
//      var size = contacts.size() + 2;
//      var a = "tr:nth-child(" + (contacts.size() + 2)+ ") > td:nth-child(2)";
//      var lastname = checkbox.getCssValue(String.valueOf(By.cssSelector("tr.center")));
//      var firstname = checkbox.getCssValue(String.valueOf(By.cssSelector("td:nth-child(3)")));
      contacts.add(new ContactData().withId(String.valueOf(id)));
      //.withLastname(String.valueOf(lastname)).withFirstname(String.valueOf(firstname)));
    }
    return contacts;
  }
}

