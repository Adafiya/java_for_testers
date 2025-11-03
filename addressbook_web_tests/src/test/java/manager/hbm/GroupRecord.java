package manager.hbm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "group_list")
public class GroupRecord {

  @Id
  @Column(name = "group_id")
  public int id;
  @Column(name = "group_name")
  public String name;
  @Column(name = "group_header")
  public String header;
  @Column(name = "group_footer")
  public String footer;

  public Date deprecated = new Date();

  @ManyToMany
  @JoinTable(name = "address_in_groups",
      // Столбец в БД указывает на группу
      joinColumns = @JoinColumn(name = "group_id"),
      // Столбец в БД указывает на контакт
      inverseJoinColumns = @JoinColumn(name = "id"))

  public List<ContactRecord> contacts;

  public GroupRecord() {
  }

  public GroupRecord(int id, String name, String header, String footer) {
    this.id = id;
    this.name = name;
    this.header = header;
    this.footer = footer;
  }
}
