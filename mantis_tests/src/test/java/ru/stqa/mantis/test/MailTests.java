package ru.stqa.mantis.test;

import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MailTests extends TestBase {

  //Тест удаления почты для отладки без проверки
  @Test
  void canDrainInbox() {
    app.mail().drain("user1@localhost", "password");
  }

  //Тест получения почты
  @Test
  void canReceiveEmail() {
    var messages = app.mail().receive("user1@localhost", "password",
        Duration.ofSeconds(10)); //Duration.ofSeconds(10) - время ожидания
    Assertions.assertEquals(1, messages.size());
    System.out.println(messages);
  }

  //Тест извлечения ссылки письма
  @Test
  void canExtractUser() {
    app.mail().extractedUrl("user1@localhost");
  }
}
