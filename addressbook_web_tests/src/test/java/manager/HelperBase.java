package manager;

import java.nio.file.Paths;
import org.openqa.selenium.By;

public class HelperBase {

  protected final ApplicationManager manager;

  public HelperBase(ApplicationManager manager) {
    this.manager = manager;
  }

  //Выбрать локатор
  protected void click(By locator) {
    manager.driver.findElement(locator).click();
  }

  //Заполнить поле ввода
  protected void type(By locator, String text) {
    click(locator);
    manager.driver.findElement(locator).clear();
    manager.driver.findElement(locator).sendKeys(text);
  }

  //Прикрепить фото
  protected void attach(By locator, String file) {
    //Преобразовать относительный путь в абсолютный путь
    manager.driver.findElement(locator).sendKeys(Paths.get(file).toAbsolutePath().toString());
  }
}
