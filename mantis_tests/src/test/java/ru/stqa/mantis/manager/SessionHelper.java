package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase {

  public SessionHelper(ApplicationManager manager) {
    super(manager);
  }

  //Логин
  public void login(String username, String password) {
    //Заполнение формы логина
    type(By.name("username"), username); //"username" - локатор, user - заполняемое значение
    //Нажать кнопку "Login"
    click(By.cssSelector("input[type='submit']"));
    //Заполнение формы пароля
    type(By.name("password"), password); //"password" - локатор, password - заполняемое значение
    //Нажать кнопку "Login"
    click(By.cssSelector("input[type='submit']"));
  }

  public boolean isLoggedIn() {
    return isElementPresent(By.cssSelector("span.user-info"));
  }

  //Создание пользователя
  public void createUser(String username, String email) {
    openPageNewAccount();
    fillUserForm(username, email);
    submitUserCreation();
    openPageAfterNewAccount();
  }

  //Проходим по ссылке и завершаем регистрацию (браузер, SessionHelper)
  public void FinishRegisterUser(String url, String username, String password) {
    openPageForFinishRegistration(url);
    fillUserFormForFinishRegistration(username, password);
    submitForFinishRegistration();
  }

  //Открываем страницу создания нового пользователя
  private void openPageNewAccount() {
    click(By.linkText("Signup for a new account"));
  }

  //Заполнить форму создания нового пользователя
  private void fillUserForm(String username, String email) {
    type(By.name("username"), username);
    type(By.name("email"), email);
  }

  //Подтвердить создание пользователя
  private void submitUserCreation() {
    click(By.xpath("//input[@value=\'Signup\']"));
  }

  //Нажать кнопку Proceed после создания пользователя
  private void openPageAfterNewAccount() {
    click(By.linkText("Proceed"));
  }

  //Перейти по ссылке после создания пользователя
  private void openPageForFinishRegistration(String url) {
    manager.driver().get(url);
  }

  //Заполнить форму после создания пользователя
  private void fillUserFormForFinishRegistration(String username, String password) {
    type(By.name("realname"), username);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
  }

  //Подтвердить создания пользователя в конце процесса
  private void submitForFinishRegistration() {
    click(By.cssSelector("button[type='submit'].btn-success"));
  }
}
