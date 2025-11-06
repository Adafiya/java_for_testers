package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ContactData;
import model.GroupData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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

  //Создать контакт с вхождением в группу
  public void createContact(ContactData contact, GroupData group) {
    openContactPage();
    initContactCreation();
    fillContactForm(contact);
    selectGroup(group);
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
  public void modifyContact(ContactData contact, ContactData modifiedContact) {
    openContactPage();
    selectContact(contact);
    initContactModification(contact);
    fillContactForm(modifiedContact);
    submitContactModification();
    openContactPage();
  }

  //Добавить контакт в группу
  public void addContactToGroup(ContactData contact, GroupData group) {
    openContactPage();
    selectContact(contact);
    chooseGroup(group);
    addToGroup(contact);
    openContactPage();
  }

  //Удалить контакт из группы
  public void removeContactFromGroup(ContactData contact, GroupData group) {
    openContactPage();
    chooseGroupForRemove(group);
    selectContact(contact);
    removeFromGroup();
    openContactPage();
  }

  //Находить группы при создании контактов (выбор группы)
  private void selectGroup(GroupData group) {
    //Работа с выпадающими списками new Select
    new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
  }

  //Выбрать группу, куда добавить контакт
  private void chooseGroup(GroupData group) {
    //Выбрать группу
    //click(By.name("to_group"));
    new Select(manager.driver.findElement(By.name("to_group"))).selectByValue(group.id());
  }

  //Выбрать группу, куда добавить контакт при удалении
  private void chooseGroupForRemove(GroupData group) {
    //Выбрать группу
    //click(By.name("group"));
    new Select(manager.driver.findElement(By.name("group"))).selectByValue(group.id());
  }

  //Добавить контакт в группу
  private void addToGroup(ContactData contact) {
    click(By.name("add"));
  }

  //Удалить контакт из группы
  private void removeFromGroup() {
    click(By.name("remove"));
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
    //attach(By.name("photo"), contact.photo()); //Разобраться почему тесты с этой строчкой падают
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
  private void initContactModification(ContactData contact) {
    click(By.cssSelector(String.format("a[href=\'edit.php?id=%s\'] img", contact.id())));
    //click(By.xpath("//img[@alt=\'Edit\']"));
  }

  //Нажать кнопку подтверждения обновиления контакта
  private void submitContactModification() {
    click(By.xpath("(//input[@name=\'update\'])[2]"));
  }

  //Получить список всех контактов (их id и name)
  public List<ContactData> getList() {
    openContactPage();
    var contacts = new ArrayList<ContactData>();
    var trs = manager.driver.findElements(By.cssSelector("tr[name =\'entry\']"));
    for (var tr : trs) {
      var lastname = tr.findElement(By.cssSelector("td:nth-child(2)")).getText();
      var firstname = tr.findElement(By.cssSelector("td:nth-child(3)")).getText();
      //var name = tr.getText();
      var checkbox = tr.findElement(By.name("selected[]"));
      var id = checkbox.getAttribute("value");
      contacts.add(
          new ContactData().withId(String.valueOf(id)).withLastname(String.valueOf(lastname))
              .withFirstname(String.valueOf(firstname)));
    }
    return contacts;
  }

  public String getPhones(ContactData contact) {
    //String.format используем при '%s', /.. - подъем на каждый уровень вверх, td[6] - 6ая ячейка (phones), getText() - возвращаем текст
    return manager.driver.findElement(
        By.xpath(String.format("//input[@id='%s']/../../td[6]", contact.id()))).getText();
  }

  public String getAddress(ContactData contact) {
    //String.format используем при '%s', /.. - подъем на каждый уровень вверх, td[4] - 4ая ячейка (address), getText() - возвращаем текст
    return manager.driver.findElement(
        By.xpath(String.format("//input[@id='%s']/../../td[4]", contact.id()))).getText();
  }

  public String getEmails(ContactData contact) {
    //String.format используем при '%s', /.. - подъем на каждый уровень вверх, td[5] - 5ая ячейка(email), getText() - возвращаем текст
    return manager.driver.findElement(
        By.xpath(String.format("//input[@id='%s']/../../td[5]", contact.id()))).getText();
  }

  //Извлекаем пару id -телефон
  public Map<String, String> getPhones() {
    var result = new HashMap<String, String>();
    List<WebElement> rows = manager.driver.findElements(By.name("entry"));
    for (WebElement row : rows) {
      var id = row.findElement(By.tagName("input")).getAttribute("id"); //извлекаем id
      var phones = row.findElements(By.tagName("td")).get(5).getText(); //извлекаем ячейку 5 (phones) и берем ее текст
      result.put(id, phones); //помещаем пару в map
    }
    return result;
  }

  //Извлекаем пару id - address
  public Map<String, String> getAddress() {
    var result = new HashMap<String, String>();
    List<WebElement> rows = manager.driver.findElements(By.name("entry"));
    for (WebElement row : rows) {
      var id = row.findElement(By.tagName("input")).getAttribute("id"); //извлекаем id
      var address = row.findElements(By.tagName("td")).get(3).getText(); //извлекаем ячейку 3 (address) и берем ее текст
      result.put(id, address); //помещаем пару в map
    }
    return result;
  }

  //Извлекаем пару id - email
  public Map<String, String> getEmails() {
    var result = new HashMap<String, String>();
    List<WebElement> rows = manager.driver.findElements(By.name("entry"));
    for (WebElement row : rows) {
      var id = row.findElement(By.tagName("input")).getAttribute("id"); //извлекаем id
      var emails = row.findElements(By.tagName("td")).get(4).getText(); //извлекаем ячейку 4 (email) и берем ее текст
      result.put(id, emails); //помещаем пару в map
    }
    return result;
  }
}

