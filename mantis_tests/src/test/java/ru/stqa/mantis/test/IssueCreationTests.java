package ru.stqa.mantis.test;

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.model.IssueData;

public class IssueCreationTests extends TestBase {

  //Тест проверка создания баг-репорта через удаленный интерфейс через Rest
  @Test
  void canCreateIssueRest() {
    app.rest().createIssue(new IssueData()
        .withSummary(CommonFunctions.randomString(10))
        .withDescription(CommonFunctions.randomString(50))
        .withProject(1L));
  }

  //Тест проверка создания баг-репорта через удаленный интерфейс через Soap
  @Test
  void canCreateIssueSoap() {
    app.soap().createIssue(new IssueData()
        .withSummary(CommonFunctions.randomString(10))
        .withDescription(CommonFunctions.randomString(50))
        .withProject(1L));
  }
}
