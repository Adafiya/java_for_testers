package tests;

import java.util.ArrayList;
import java.util.Random;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase {

  @Test
  public void canRemoveAllGroupsAtOnce() {
    if (app.groups().getCount() == 0) {
      app.groups().createGroup(new GroupData("", "group name", "group header", "group_footer"));
    }
    app.groups().removeAllGroups();
    Assertions.assertEquals(0, app.groups().getCount());
  }

  @Test
  public void canRemoveGroupWithList() {
    if (app.groups().getCount() == 0) {
      app.groups().createGroup(new GroupData("", "group name", "group header", "group_footer"));
    }
    var oldGroups = app.groups().getList();
    var rnd = new Random();
    var index = rnd.nextInt(oldGroups.size());
    app.groups().removeGroup(oldGroups.get(index));
    var newGroups = app.groups().getList();
    var expectedList = new ArrayList<>(oldGroups);
    expectedList.remove(index);
    Assertions.assertEquals(newGroups, expectedList); // Сравнение всех id и name списков до и после
    //Assertions.assertEquals(newGroups.size() - 1, oldGroups.size()); // Сравнение размера списков до и после
  }

  /*
  @Test
  public void canRemoveGroup() {
    if (app.groups().getCount() == 0) {
      app.groups().createGroup(new GroupData("", "group name", "group header", "group_footer"));
    }
    int groupCount = app.groups().getCount();
    app.groups().removeGroup();
    int newGroupCount = app.groups().getCount();
    Assertions.assertEquals(groupCount - 1, newGroupCount);
  }
  */
}
