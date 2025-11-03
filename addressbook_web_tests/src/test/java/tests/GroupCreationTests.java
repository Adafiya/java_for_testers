package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import common.CommonFunctions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GroupCreationTests extends TestBase {

  //Создаем много групп
  public static List<GroupData> groupProvider() throws IOException {
    var result = new ArrayList<GroupData>();
    for (var name : List.of("", "group_name")) {
      for (var header : List.of("", "group_header")) {
        for (var footer : List.of("", "group_footer")) {
          result.add(new GroupData().withName(name).withHeader(header).withFooter(footer));
        }
      }
    }

    //Читаем данные из сгенерированного файла
//    var json = "";
//    try (var reader = new FileReader("groups.json");
//    var breader = new BufferedReader(reader)) {
//      var line = breader.readLine();
//      while (line != null) {
//        json = json + line;
//        line = breader.readLine();
//      }
//    }

//Читаем данные из сгенерированного файла json
//    var json = Files.readString(Paths.get("groups.json"));
//    ObjectMapper mapper = new ObjectMapper();
//    var value = mapper.readValue(json, new TypeReference<List<GroupData>>() {});
//Читаем данные из сгенерированного файла xml
    var mapper = new XmlMapper();
    var value = mapper.readValue(new File("groups.xml"), new TypeReference<List<GroupData>>() {
    });
//Добавляем все значения в список
    result.addAll(value);
    return result;
  }

  public static List<GroupData> negativeGroupProvider() {
    var result = new ArrayList<GroupData>(List.of(
        new GroupData("", "group_name'", "", "")));
    return result;
  }

  //Тест создания групп с проверкой списка групп на странице UI
  @ParameterizedTest
  @MethodSource("groupProvider")
  public void canCreateMultipleGroupsWithUI(GroupData group) {
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

  //Создаем одну рандомную группу
  public static List<GroupData> singleRandomGroup() {
    return List.of(new GroupData()
        .withName(CommonFunctions.randomString(10))
        .withHeader(CommonFunctions.randomString(20))
        .withFooter(CommonFunctions.randomString(30)));
  }

  //Тест создания группы с проверкой группы в БД
  @ParameterizedTest
  @MethodSource("singleRandomGroup")
  public void canCreateGroupsWishJdbc(GroupData group) {
    var oldGroups = app.hbm().getGroupList();
    app.groups().createGroup(group);
    var newGroups = app.hbm().getGroupList();
    Comparator<GroupData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newGroups.sort(compareById);
    var maxId = newGroups.get(newGroups.size() - 1).id();
    var expectedList = new ArrayList<>(oldGroups);
    expectedList.add(
        group.withId(maxId));
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
