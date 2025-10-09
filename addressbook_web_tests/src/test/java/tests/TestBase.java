package tests;

import java.util.Random;
import manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

  public static ApplicationManager app;

  @BeforeEach
  public void setUp() {
    if (app == null) {
      app = new ApplicationManager();
      app.init(System.getProperty("browser", "firefox"));
    }
  }

  //Генерируем рандомный String в n символов
  public static String randomString(int n) {
    var rnd = new Random();
    var result = "";
    for (int i = 0; i < n; i++) {
      result = result + (char) ('a' + rnd.nextInt(26));
    }
    return result;
  }
}
