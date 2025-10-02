import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GroupCreationTests {

  private static WebDriver driver;

  @BeforeEach
  public void setUp() {
    if (driver == null) {
      driver = new FirefoxDriver();
      //В конце теста выполнить функцию ниже
      Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
      driver.get("http://localhost/addressbook/");
      driver.manage().window().setSize(new Dimension(1140, 1032));
      driver.findElement(By.name("user")).sendKeys("admin");
      driver.findElement(By.name("pass")).sendKeys("secret");
      driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
    }
  }

  @Test
  public void canCreateGroup() {
    //Если блок группы не открыт, то открываем его
    if (!isElementPresent(By.name("new"))) {
      driver.findElement(By.linkText("groups")).click();
    }
    driver.findElement(By.name("new")).click();
    driver.findElement(By.name("group_name")).click();
    driver.findElement(By.name("group_name")).sendKeys("group name");
    driver.findElement(By.name("group_header")).click();
    driver.findElement(By.name("group_header")).sendKeys("group header");
    driver.findElement(By.name("group_footer")).click();
    driver.findElement(By.name("group_footer")).sendKeys("group_footer");
    driver.findElement(By.name("submit")).click();
    driver.findElement(By.linkText("groups")).click();

  }


  @Test
  public void canCreateGroupWithEmptyName() {
    //Если блок группы не открыт, то открываем его
    if (!isElementPresent(By.name("new"))) {
      driver.findElement(By.linkText("groups")).click();
    }
    driver.findElement(By.name("new")).click();
    driver.findElement(By.name("group_name")).click();
    driver.findElement(By.name("group_name")).sendKeys("");
    driver.findElement(By.name("group_header")).click();
    driver.findElement(By.name("group_header")).sendKeys("");
    driver.findElement(By.name("group_footer")).click();
    driver.findElement(By.name("group_footer")).sendKeys("");
    driver.findElement(By.name("submit")).click();
    driver.findElement(By.linkText("groups")).click();

  }

  private boolean isElementPresent(By locator) {
    try {
      driver.findElement(locator);
      return true;
    } catch (NoSuchElementException exception) {
      return false;
    }
  }
}
