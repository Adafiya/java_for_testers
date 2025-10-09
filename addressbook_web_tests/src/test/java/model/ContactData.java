package model;

public record ContactData(String id, String firstname, String middlename, String lastname) {

  public ContactData() {
    this("", "", "", "");
  }

  public ContactData withId(String id) {
    return new ContactData(id, this.firstname, this.middlename, this.lastname);
  }

  public ContactData withFirstname(String firstname) {
    return new ContactData(this.id, firstname, this.middlename, this.lastname);
  }

  public ContactData withMiddlename(String middlename) {
    return new ContactData(this.id, this.firstname, middlename, this.lastname);
  }

  public ContactData withLastname(String lastname) {
    return new ContactData(this.id, this.firstname, this.middlename, lastname);
  }
}