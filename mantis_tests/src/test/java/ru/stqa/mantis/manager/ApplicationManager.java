package ru.stqa.mantis.manager;

import java.util.Properties;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ApplicationManager {

  private WebDriver driver;
  private String string;
  private Properties properties;
  private SessionHelper sessionHelper;
  private HttpSessionHelper httpSessionHelper;
  private JamesCliHelper jamesCliHelper;
  private MailHelper mail;

  //Логин
  public void init(String browser, Properties properties) {
    this.string = browser;
    this.properties = properties;
  }

  //Проверка браузера
  public WebDriver driver() {
    if (driver == null) {
      if ("firefox".equals(string)) {
        driver = new FirefoxDriver();
      } else if ("chrome".equals(string)) {
        driver = new ChromeDriver();
      } else {
        throw new IllegalArgumentException(String.format("Unknown browser %s", string));
      }
      //Разлогин и закрытие браузера
      Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
      //Открытие страницы
      driver.get(properties.getProperty("web.baseUrl"));
      driver.manage().window().setSize(new Dimension(1140, 1032));
    }
    return driver;
  }

  //Метод для ленивой инициализации
  public SessionHelper session() {
    if (sessionHelper == null) {
      sessionHelper = new SessionHelper(this);
    }
    return sessionHelper;
  }

  public HttpSessionHelper http() {
    if (httpSessionHelper == null) {
      httpSessionHelper = new HttpSessionHelper(this);
    }
    return httpSessionHelper;
  }

  public JamesCliHelper jamesCli() {
    if (jamesCliHelper == null) {
      jamesCliHelper = new JamesCliHelper(this);
    }
    return jamesCliHelper;
  }

  public MailHelper mail() {
    if (mail == null) {
      mail = new MailHelper(this);
    }
    return mail;
  }

  public String property(String name) {
    return properties.getProperty(name);
  }
}
