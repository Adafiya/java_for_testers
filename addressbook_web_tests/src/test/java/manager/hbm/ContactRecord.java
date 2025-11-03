package manager.hbm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addressbook")
public class ContactRecord {

  @Id
  @Column(name = "id")
  public int id;
  @Column(name = "firstname")
  public String firstname;
  @Column(name = "middlename")
  public String middlename;
  @Column(name = "lastname")
  public String lastname;

  public ContactRecord() {
  }

  public ContactRecord(int id, String firstname, String middlename, String lastname) {

    this.id = id;
    this.firstname = firstname;
    this.middlename = middlename;
    this.lastname = lastname;
  }
}
