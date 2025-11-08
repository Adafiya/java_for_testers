package ru.stqa.mantis.test;

import java.time.Duration;
import java.util.regex.Pattern;
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
    //Получаем почту
    var messages = app.mail().receive("user1@localhost", "password", Duration.ofSeconds(10));
    //Берем текст первого письма
    var text = messages.get(0).content();
    //Pattern (то что ниже) - описывает регуляр, на соответствие которому нужно что-то проверять
    var pattern = Pattern.compile("http://\\S*");// \\s - пробел, \\S - НЕ пробел
    //Проверяем текс на соответствие регуляра
    var matcher = pattern.matcher(text);
    //Проверяем содержится ли этот фрагмент в тексте
    if (matcher.find()) {
      //Узнаем начало и конец фрагмента
      var url = text.substring(matcher.start(), matcher.end());
      System.out.println(url);
    }
  }
}
