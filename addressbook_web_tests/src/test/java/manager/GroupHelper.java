package manager;

import java.util.ArrayList;
import java.util.List;
import model.GroupData;
import org.openqa.selenium.By;

public class GroupHelper extends HelperBase {

  public GroupHelper(ApplicationManager manager) {
    super(manager);
  }

  //Открыть страницу групп
  public void openGroupsPage() {
    if (!manager.isElementPresent(By.name("new"))) {
      click(By.linkText("groups"));
    }
  }

  //Создать группу
  public void createGroup(GroupData group) {
    openGroupsPage();
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    openGroupsPage();
  }

  //Удалить группу
  public void removeGroup(GroupData group) {
    openGroupsPage();
    selectGroup(group);
    removeSelectedGroups();
    returnToGroupPage();
  }

  //Редактировать группу
  public void modifyGroup(GroupData group, GroupData modifiedGroup) {
    openGroupsPage();
    selectGroup(group);
    initGroupModification();
    fillGroupForm(modifiedGroup);
    submitGroupModification();
    returnToGroupPage();
  }

  //Подтвердить создание группы
  private void submitGroupCreation() {
    click(By.name("submit"));
  }


  //Нажать кнопку создания новой группы
  private void initGroupCreation() {
    click(By.name("new"));
  }


  //Нажать кнопку удаления группу
  private void removeSelectedGroups() {
    click(By.name("delete"));
  }

  //Перейти на страницу с группами
  private void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  //Нажать кнопку подтверждения обновиления группу
  private void submitGroupModification() {
    click(By.name("update"));
  }

  //Заполнить форму группы
  private void fillGroupForm(GroupData group) {
    type(By.name("group_name"), group.name());
    type(By.name("group_header"), group.header());
    type(By.name("group_footer"), group.footer());
  }

  //Нажать кнопку редактирования группы
  private void initGroupModification() {
    click(By.name("edit"));
  }

  //Выбрать группу
  private void selectGroup(GroupData group) {
    click(By.cssSelector(String.format("input[value='%s']", group.id())));
  }

  //Считать сколько было групп
  public int getCount() {
    openGroupsPage();
    return manager.driver.findElements(By.name("selected[]")).size();
  }

  //Удальть все группы разом
  public void removeAllGroups() {
    selectAllGroups();
    removeSelectedGroups();
    openGroupsPage();
  }

  //Выбор всех групп
  private void selectAllGroups() {
    var checkboxes = manager.driver.findElements(By.name("selected[]"));
    for (var checkbox : checkboxes) {
      checkbox.click();
    }
  }

  //Получить список всех групп (их id и name)
  public List<GroupData> getList() {
    openGroupsPage();
    var groups = new ArrayList<GroupData>();
    var spans = manager.driver.findElements(By.cssSelector("span.group")); //тег span класс group
    for (var span : spans) {
      var name = span.getText();
      var checkbox = span.findElement(By.name("selected[]"));
      var id = checkbox.getAttribute("value");
      groups.add(new GroupData().withId(id).withName(name));
    }
    return groups;
  }

   /*
  //Проверить наличие групп
  public boolean isGroupPresent() {
    openGroupsPage();
    return manager.isElementPresent(By.name("selected[]"));
  }
  */
}
