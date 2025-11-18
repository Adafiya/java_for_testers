package manager;

import io.qameta.allure.Step;
import java.util.List;
import java.util.stream.Collectors;
import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.AvailableSettings;

public class HibernateHelper extends HelperBase {

  private SessionFactory sessionFactory;

  public HibernateHelper(ApplicationManager manager) {
    super(manager);

    sessionFactory = new Configuration()
        .addAnnotatedClass(ContactRecord.class)
        .addAnnotatedClass(GroupRecord.class)
        .setProperty(AvailableSettings.URL,
            "jdbc:mysql://localhost/addressbook?zeroDateTimeBehavior=CONVERT_TO_NULL")
        .setProperty(AvailableSettings.USER, "root")
        .setProperty(AvailableSettings.PASS, "")
        .buildSessionFactory();
  }

  static List<GroupData> convertList(List<GroupRecord> records) {
    return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    /*
    List<GroupData> result = new ArrayList<>();
    for (var record : records) {
      result.add(convert(record));
    }
    return result;
     */
  }

  //GroupRecord преобразуем в GroupData
  private static GroupData convert(GroupRecord record) {
    return new GroupData("" + record.id, record.name, record.header, record.footer);
  }

  //GroupData преобразуем в GroupRecord
  private static GroupRecord convert(GroupData data) {
    var id = data.id();
    if ("".equals(id)) {
      id = "0";
    }
    return new GroupRecord(Integer.parseInt(id), data.name(), data.header(), data.footer());
  }

  @Step
  public List<GroupData> getGroupList() {
    return convertList(sessionFactory.fromSession(session -> {
      return session.createQuery("from GroupRecord", GroupRecord.class).list();
    }));
  }

  //Счетчик групп. long т.к. int може не хватить
  public long getGroupCount() {
    return sessionFactory.fromSession(session -> {
      return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
    });
  }

  public void createGroup(GroupData groupData) {
    sessionFactory.inSession(session -> {
          //Сначала открываем транзакцию, после выполнения всех нужных действий закрываем транзакцию
          session.getTransaction().begin();
          session.persist(convert(groupData));
          session.getTransaction().commit();
        }
    );
  }

  static List<ContactData> convertContactList(List<ContactRecord> records) {
    return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    /*
    List<ContactData> result = new ArrayList<>();
    for (var record : records) {
      result.add(convert(record));
    }
    return result;
     */
  }

  //ContactRecord преобразуем в ContactData
  private static ContactData convert(ContactRecord record) {
    return new ContactData().withId("" + record.id).withLastname(record.lastname)
        .withMiddlename(record.middlename)
        .withFirstname(record.firstname)
        .withHome(record.home)
        .withMobile(record.mobile)
        .withWork(record.work)
        .withSecondary(record.phone2);
  }

  //ContactData преобразуем в ContactRecord
  private static ContactRecord convert(ContactData data) {
    var id = data.id();
    if ("".equals(id)) {
      id = "0";
    }
    return new ContactRecord(Integer.parseInt(id), data.lastname(), data.middlename(),
        data.firstname());
  }

  @Step
  public List<ContactData> getContactList() {
    return sessionFactory.fromSession(session -> {
      return convertContactList(
          session.createQuery("from ContactRecord", ContactRecord.class).list());
    });
  }

  //Счетчик контактов. long т.к. int може не хватить
  public long getContactCount() {
    return sessionFactory.fromSession(session -> {
      return session.createQuery("select count (*) from ContactRecord", Long.class)
          .getSingleResult();
    });
  }

  @Step
  public void createContact(ContactData contactData) {
    sessionFactory.inSession(session -> {
      //Сначала открываем транзакцию, после выполнения всех нужных действий закрываем транзакцию
      session.getTransaction().begin();
      session.persist(convert(contactData));
      session.getTransaction().commit();
    });
  }

  public List<ContactData> getContactsInGroup(GroupData group) {
    return sessionFactory.fromSession(session -> {
      return convertContactList(session.find(GroupRecord.class, group.id()).contacts);
    });
  }
}
