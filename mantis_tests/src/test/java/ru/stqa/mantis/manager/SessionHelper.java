package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase {

  public SessionHelper(ApplicationManager manager) {
    super(manager);
  }

  //Логин
  public void login(String user, String password) {
    //Заполнение формы логина
    type(By.name("username"), user); //"username" - локатор, user - заполняемое значение
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
}
