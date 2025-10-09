package manager;

import model.ContactData;
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
  public void removeContact() {
    openContactPage();
    selectContact();
    removeSelectedContacts();
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
  private void selectContact() {
    click(By.name("selected[]"));
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
}

