package manager;

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

  //Проверить наличие групп
  public boolean isGroupPresent() {
    openGroupsPage();
    return manager.isElementPresent(By.name("selected[]"));
  }

  //Создать группу
  public void createGroup(GroupData group) {
    openGroupsPage();
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    returnToGroupPage();
  }

  //Удалить группу
  public void removeGroup() {
    openGroupsPage();
    selectGroup();
    removeSelectedGroup();
    returnToGroupPage();
  }

  //Редактировать группу
  public void modifyGroup(GroupData modifiedGroup) {
    openGroupsPage();
    selectGroup();
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
  private void removeSelectedGroup() {
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
  private void selectGroup() {
    click(By.name("selected[]"));
  }

}
