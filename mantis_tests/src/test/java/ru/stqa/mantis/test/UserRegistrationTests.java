package ru.stqa.mantis.test;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.mantis.common.CommonFunctions;

public class UserRegistrationTests extends TestBase {

  public static Stream<String> randomUser() {
    return Stream.of(CommonFunctions.randomString(5));
  }

  @ParameterizedTest
  @MethodSource("randomUser")
  void canRegisterUser(String username) { //username создается рандомно и подтягивается из randomUser
    //Создаем email
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
