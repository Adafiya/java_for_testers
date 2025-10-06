package manager;

import static tests.TestBase.app;

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

  //Проверить наличие контактов
  public boolean isContactPresent() {
    openContactPage();
    return manager.isElementPresent(By.name("selected[]"));
  }

  //Создать контакт
  public void createContact(ContactData contact) {
    initContactCreation();
    fillContactForm(contact);
    submitContactCreation();
    openContactPage();
  }

  //Удалить контакт
  public void removeContact() {
    openContactPage();
    selectContact();
    removeSelectedContact();
    //Не поняла зачем и куда подставлять строчку. И без нее все работает, а с ней ловлю исключение NoAlertPresentException
    //app.driver.switchTo().alert().accept();
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
  private void removeSelectedContact() {
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

}

