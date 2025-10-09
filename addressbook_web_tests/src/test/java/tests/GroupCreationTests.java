package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GroupCreationTests extends TestBase {

  public static List<GroupData> groupProvider() {
    var result = new ArrayList<GroupData>();
    for (var name : List.of("", "group_name")) {
      for (var header : List.of("", "group_header")) {
        for (var footer : List.of("", "group_footer")) {
          result.add(new GroupData().withName(name).withHeader(header).withFooter(footer));
        }
      }
    }
    for (int i = 0; i < 5; i++) {
      result.add(new GroupData()
          .withName(randomString(i * 10))
          .withHeader(randomString(i * 10))
          .withFooter(randomString(i * 10)));
    }
    return result;
  }

  public static List<GroupData> negativeGroupProvider() {
    var result = new ArrayList<GroupData>(List.of(
        new GroupData("", "group_name'", "", "")));
    return result;
  }

  @ParameterizedTest
  @MethodSource("groupProvider")
  public void canCreateMultipleGroups(GroupData group) {
    var oldGroups = app.groups().getList();
    app.groups().createGroup(group);
    var newGroups = app.groups().getList();
    Comparator<GroupData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newGroups.sort(compareById);
    var expectedList = new ArrayList<>(oldGroups);
    expectedList.add(
        group.withId(newGroups.get(newGroups.size() - 1).id()).withHeader("").withFooter(""));
    expectedList.sort(compareById);
    Assertions.assertEquals(newGroups, expectedList);
  }

  @ParameterizedTest
  @MethodSource("negativeGroupProvider")
  public void canNotCreateMultipleGroups(GroupData group) {
    var oldGroups = app.groups().getList();
    app.groups().createGroup(group);
    var newGroups = app.groups().getList();
    Assertions.assertEquals(newGroups, oldGroups);
  }

  /*
  @ParameterizedTest
  @ValueSource(strings = {"group_name", "group_name'"})
  public void canCreateGroup(String name) {
    int groupCount = app.groups().getCount();
    app.groups().createGroup(new GroupData(name, "group_header", "group_footer"));
    int newGroupCount = app.groups().getCount();
    Assertions.assertEquals(groupCount + 1, newGroupCount);
  }

  @Test
  public void canCreateGroupWithEmptyName() {
    app.groups().createGroup(new GroupData());
  }

  @Test
  public void canCreateGroupWithNameOnly() {
    app.groups().createGroup(new GroupData().withName("some name"));
  }
  */
}
