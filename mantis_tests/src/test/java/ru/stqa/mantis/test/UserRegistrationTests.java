package ru.stqa.mantis.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;

public class UserRegistrationTests extends TestBase {

  @Test
  void canRegisterUser() { //ругается на параметр String username, добавила его внутрь теста
    //Создаем username и email
    var username = CommonFunctions.randomString(8);
    var email = String.format("%s@localhost", username);
    var password = "password";
    //Создаем пользователя (адрес) на почтовом сервере (JamesHelper)
    app.jamesCli().addUser(email, password);
    //Заполняем форму создания и отправляем (браузер, SessionHelper)
    app.session().createUser(username, email);
    //Ждем почту и извлекаем ссылку из письма (MailHelper)
    String url = app.mail().extractedUrl(email, password);
    //Проходим по ссылке и завершаем регистрацию (браузер, SessionHelper)
    app.session().FinishRegisterUser(url, username, password);
    //Проверяем, что пользователь может залогиниться (HttpSessionHelper)
    app.http().login(username, password);
    Assertions.assertTrue(app.http().isLoggedIn());
  }
}
