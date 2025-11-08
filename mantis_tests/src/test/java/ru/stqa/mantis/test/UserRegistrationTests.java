package ru.stqa.mantis.test;

import org.junit.jupiter.api.Test;

public class UserRegistrationTests extends TestBase {

  @Test
  void canRegisterUser(String username) {
    var email = String.format("%s@localhost", username);
    //создать пользователя (адрес) на почтовом сервере (JamesHelper)
    //заполняем форму создания и отправляем (браузер)
    //Ждем почту (MailHelper)
    //извлекаем ссылку из письма (в лекции 8.6)
    //проходим по ссылке и завершаем регистрацию (браузер)
    //проверяем, что пользователь может залогиниться (HttpSessionHelper)
  }
}
