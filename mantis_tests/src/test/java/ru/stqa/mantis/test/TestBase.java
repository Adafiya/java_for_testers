package ru.stqa.mantis.test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import ru.stqa.mantis.manager.ApplicationManager;

public class TestBase {

  public static ApplicationManager app;

  @BeforeEach
  public void setUp() throws IOException {
    if (app == null) {
      var properties = new Properties();
      properties.load(new FileReader(System.getProperty("target", "local.properties")));
      app = new ApplicationManager();
      app.init(System.getProperty("browser", "firefox"), properties);
    }
  }
}
