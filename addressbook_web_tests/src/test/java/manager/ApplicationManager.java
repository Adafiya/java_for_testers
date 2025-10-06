import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ApplicationManager {

  protected static WebDriver driver;

  //Разлогин
  static void logout() {

    driver.findElement(By.linkText("Logout")).click();
    driver.quit();
  }

  //Логин
  void init() {
    if (driver == null) {
      driver = new FirefoxDriver();
      driver.get("http://localhost/addressbook/");
      driver.manage().window().setSize(new Dimension(1140, 1032));
      driver.findElement(By.name("user")).sendKeys("admin");
      driver.findElement(By.name("pass")).sendKeys("secret");
      driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
    }
  }

  //Ищем элемент
  protected boolean isElementPresent(By locator) {
    try {
      driver.findElement(locator);
      return true;
    } catch (NoSuchElementException exception) {
      return false;
    }
  }

  //Создаем группу
  protected void createGroup(GroupData group) {
    driver.findElement(By.name("new")).click();
    driver.findElement(By.name("group_name")).click();
    driver.findElement(By.name("group_name")).sendKeys(group.name());
    driver.findElement(By.name("group_header")).click();
    driver.findElement(By.name("group_header")).sendKeys(group.header());
    driver.findElement(By.name("group_footer")).click();
    driver.findElement(By.name("group_footer")).sendKeys(group.footer());
    driver.findElement(By.name("submit")).click();
    driver.findElement(By.linkText("groups")).click();
  }

  //Открываем страницу групп
  protected void openGroupsPage() {
    if (!isElementPresent(By.name("new"))) {
      driver.findElement(By.linkText("groups")).click();
    }
  }

  //Проверка наличия групп
  protected boolean isGroupPresent() {
    return isElementPresent(By.name("selected[]"));
  }

  //Удаляем группу
  protected void removeGroup() {
    driver.findElement(By.name("selected[]")).click();
    driver.findElement(By.name("delete")).click();
    driver.findElement(By.linkText("group page")).click();
  }
}
