package manager;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.GroupData;

public class JdbcHelper extends HelperBase {

  public JdbcHelper(ApplicationManager manager) {
    super(manager);
  }

  public List<GroupData> getGroupList() {
    var groups = new ArrayList<GroupData>();
    try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
        var statement = conn.createStatement();
        //Выполняем запрос в БД
        var result = statement.executeQuery(
            "SELECT group_id, group_name, group_header, group_footer FROM group_list")) {
      while (result.next()) {
        //Перебираем значения и сохраняем в список
        groups.add(new GroupData()
            .withId(result.getString("group_id"))
            .withName(result.getString("group_name"))
            .withHeader(result.getString("group_header"))
            .withFooter(result.getString("group_footer")));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return groups;
  }
}
