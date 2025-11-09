package ru.stqa.mantis.manager;

import static ru.stqa.mantis.test.TestBase.app;

import jakarta.mail.Flags.Flag;
import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import ru.stqa.mantis.model.MailMessage;

public class MailHelper extends HelperBase {

  public MailHelper(ApplicationManager manager) {
    super(manager);
  }

  //Извлекаем ссылку из письма
  public String extractedUrl(String email, String password) {
    //Получаем почту
    var messages = app.mail().receive(email, password, Duration.ofSeconds(10));
    //Берем текст первого письма
    var text = messages.get(0).content();
    //Pattern (то что ниже) - описывает регуляр, на соответствие которому нужно что-то проверять
    var pattern = Pattern.compile("http://\\S*");// \\s - пробел, \\S - НЕ пробел
    //Проверяем текс на соответствие регуляра
    var matcher = pattern.matcher(text);
    String url = null;
    //Проверяем содержится ли этот фрагмент в тексте
    if (matcher.find()) {
      //Узнаем начало и конец фрагмента
      url = text.substring(matcher.start(), matcher.end());
    }
    return url;
  }

  //Получение почты
  public List<MailMessage> receive(String username, String password, Duration duration) {
    //Запоминаем время выполнения метода
    var start = System.currentTimeMillis(); //возвращает время в мсек
    while (System.currentTimeMillis() < start + duration.toMillis()) {
      try {
        //Получаем inbox
        var inbox = getInbox(username, password);
        //Отрываем inbox на чтение
        inbox.open(Folder.READ_ONLY);
        var messages = inbox.getMessages();
        var result = Arrays.stream(messages)
            .map(m -> {
              try {
                return new MailMessage()
                    .withFrom(m.getFrom()[0].toString())
                    .withContent((String) m.getContent());
              } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
              }
            })
            .toList();
        //Закрываем inbox
        inbox.close();
        //Закрываем хранилище
        inbox.getStore().close();
        if (result.size() > 0) {
          return result;
        }
      } catch (MessagingException e) {
        throw new RuntimeException(e);
      }
      try {
        //Засыпание системы 1 сек
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    throw new RuntimeException("No mail");
  }

  //Подготовительные работы для получения почты
  private static Folder getInbox(String username, String password) {
    try {
      var session = Session.getInstance(new Properties());
      Store store = session.getStore("pop3");
      store.connect("localhost", username, password);
      var inbox = store.getFolder("INBOX");
      return inbox;
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  //Удаление почты
  public void drain(String username, String password) {
    try {
      //Получаем inbox
      var inbox = getInbox(username, password);
      //Отрываем inbox на чтение
      inbox.open(Folder.READ_WRITE);
      //Каждое письмо отмечаем как удаленное
      Arrays.stream(inbox.getMessages()).forEach(m -> {
        try {
          m.setFlag(Flag.DELETED, true);
        } catch (MessagingException e) {
          throw new RuntimeException(e);
        }
      });
      //Закрываем inbox
      inbox.close();
      //Закрываем хранилище
      inbox.getStore().close();
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
