package manager;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ApplicationManager {

  protected WebDriver driver;
  private LoginHelper session;
  private GroupHelper groups;
  private ContactHelper contact;
  private Properties properties;

  //Логин
  public void init(String browser, Properties properties) {
    this.properties = properties;
    //Проверка браузера
    if (driver == null) {
      if ("firefox".equals(browser)) {
        driver = new FirefoxDriver();
      } else if ("chrome".equals(browser)) {
        driver = new ChromeDriver();
      } else {
        throw new IllegalArgumentException(String.format("Unknown browser %s", browser));
      }
      //Разлогин и закрытие браузера
      Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
      //Открытие страницы
      driver.get(properties.getProperty("web.baseUrl"));
      driver.manage().window().setSize(new Dimension(1140, 1032));
      //Логин
      session().login(properties.getProperty("web.username"),
          properties.getProperty("web.password"));
    }
  }

  public LoginHelper session() {
    if (session == null) {
      session = new LoginHelper(this);
    }
    return session;
  }

  public GroupHelper groups() {
    if (groups == null) {
      groups = new GroupHelper(this);
    }
    return groups;
  }

  public ContactHelper contact() {
    if (contact == null) {
      contact = new ContactHelper(this);
    }
    return contact;
  }

  //Ищем элемент
  public boolean isElementPresent(By locator) {
    try {
      driver.findElement(locator);
      return true;
    } catch (NoSuchElementException exception) {
      return false;
    }
  }
}
