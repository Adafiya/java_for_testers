package ru.stqa.mantis.test;

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;

public class JamesTests extends TestBase {

  //Тест создать пользователя (адрес) на почтовом сервере (JamesHelper)
  @Test
  void canCreateUser() {
    app.jamesCli().addUser(
        String.format("%s@localhost", CommonFunctions.randomString(8)), "password");
  }
}
