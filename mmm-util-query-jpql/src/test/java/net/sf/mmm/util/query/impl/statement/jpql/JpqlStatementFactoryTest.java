/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.impl.statement.jpql;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import net.sf.mmm.jpa.query.api.statenent.jpql.JpqlDeleteStatement;
import net.sf.mmm.jpa.query.api.statenent.jpql.JpqlSelectStatement;
import net.sf.mmm.jpa.query.api.statenent.jpql.JpqlStatementFactory;
import net.sf.mmm.jpa.query.api.statenent.jpql.JpqlUpdateStatement;
import net.sf.mmm.jpa.query.base.statement.jpql.Jpql;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.lang.api.Id;
import net.sf.mmm.util.query.SpringTestConfig;
import net.sf.mmm.util.query.api.ListQuery;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.base.example.Contact;
import net.sf.mmm.util.query.base.example.ContactBean;
import net.sf.mmm.util.query.base.example.ContactEntity;

/**
 * This is the test of {@link JpqlStatementFactory}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ TransactionalTestExecutionListener.class,
DependencyInjectionTestExecutionListener.class })
@SpringApplicationConfiguration(classes = SpringTestConfig.class)
@Transactional
public class JpqlStatementFactoryTest extends Assertions {

  @Inject
  private JpqlStatementFactory statementFactory;

  @Inject
  private BeanFactory beanFactory;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  public void testStatements() {

    Id contact1Id = insertContact("Peter", "Pan", true, 20).getId();
    Id contact2Id = insertContact("Peter", "Pan2", false, 21).getId();
    Id contact3Id = insertContact("Peter", "Pan3", false, 22).getId();
    ContactBean prototype = this.beanFactory.createPrototype(ContactBean.class);
    checkUpdate(prototype, contact2Id, contact3Id);
    checkDelete(prototype, contact2Id, contact3Id);
    checkSelect(prototype, contact1Id);
    checkSelectMapped(prototype, contact1Id);
  }

  private ContactEntity insertContact(String firstName, String lastName, boolean friend, int age) {

    ContactEntity entity = new ContactEntity();
    entity.setFirstName(firstName);
    entity.setLastName(lastName);
    entity.setFriend(friend);
    entity.setAge(age);
    this.entityManager.persist(entity);
    return entity;
  }

  private void checkUpdate(ContactBean prototype, Id contact2Id, Id contact3Id) {

    EntityAlias<Contact> contact = Jpql.alias(Contact.class, ContactEntity.class, prototype).as("c");
    JpqlUpdateStatement<Contact> updateStatement = this.statementFactory.update(contact)
        .where(contact.to(prototype.LastName()).like("%Pan_")).set(prototype.FirstName(), prototype.LastName())
        .set(prototype.Age(), 60);
    assertThat(updateStatement.getSql())
        .isEqualTo("UPDATE ContactEntity AS c SET c.firstName = c.lastName, c.age = ?1 WHERE c.lastName LIKE ?2");
    long changes = updateStatement.execute();
    assertThat(changes).isEqualTo(2);
  }

  private void checkDelete(ContactBean prototype, Id contact2Id, Id contact3Id) {

    EntityAlias<Contact> contact = Jpql.alias(Contact.class, ContactEntity.class, prototype).as("c");
    JpqlDeleteStatement<Contact> deleteStatement = this.statementFactory.deleteFrom(contact)
        .where(contact.to(prototype.FirstName()).like("%Pan_"), contact.to(prototype.Age()).geq(60));

    assertThat(deleteStatement.getSql())
        .isEqualTo("DELETE FROM ContactEntity AS c WHERE c.firstName LIKE ?1 AND c.age >= ?2");
    long changes = deleteStatement.execute();
    assertThat(changes).isEqualTo(2);
  }

  private void checkSelect(ContactBean prototype, Id contactId) {

    EntityAlias<Contact> contact = Jpql.alias(Contact.class, ContactEntity.class, prototype).as("c");
    checkSelect(prototype, contactId, contact, Contact.class);
  }

  private void checkSelectMapped(ContactBean prototype, Id contactId) {

    EntityAlias<ContactBean> contact = Jpql.alias(ContactEntity.class, prototype).as("c");
    checkSelect(prototype, contactId, contact, ContactBean.class);
  }

  private <C extends Contact> void checkSelect(ContactBean prototype, Id contactId, EntityAlias<C> contact,
      Class<C> type) {

    String firstName = "Peter";
    String lastNamePattern = "%Pan";
    Integer minAge = Integer.valueOf(18);
    Integer maxAge = Integer.valueOf(42);
    JpqlSelectStatement<C> selectStatement = this.statementFactory.selectFrom(contact)
        .where(
            contact.to(prototype.FirstName()).eq(firstName)
                .and(contact.to(prototype.LastName()).like(lastNamePattern)
                    .or(contact.to(prototype.Age()).between(minAge, maxAge))))
        .orderBy(contact.to(prototype.Age()));
    ListQuery<C> query = selectStatement.query();
    assertThat(query.getSql()).isEqualTo(
        "SELECT c FROM ContactEntity AS c WHERE c.firstName = ?1 AND (c.lastName LIKE ?2 OR c.age BETWEEN ?3 AND ?4) ORDER BY c.age");
    assertThat(selectStatement.getParameters()).containsExactly(firstName, lastNamePattern, minAge, maxAge);
    List<C> hits = query.execute();
    assertThat(hits).hasSize(1);
    C hit = hits.get(0);
    assertThat(hit).isInstanceOf(type);
    assertThat(hit.getId()).isEqualTo(contactId);
    assertThat(hit.getFirstName()).isEqualTo("Peter");
    assertThat(hit.getLastName()).isEqualTo("Pan");
    assertThat(hit.getAge()).isEqualTo(20);
    assertThat(hit.isFriend()).isTrue();
  }

}
