/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import net.sf.mmm.persistence.api.RevisionMetadata;
import net.sf.mmm.persistence.api.RevisionedPersistenceManager;
import net.sf.mmm.test.TestUser;
import net.sf.mmm.transaction.api.TransactionExecutor;
import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.entity.api.RevisionedEntity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This is the test-case for the persistence.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class EnversTest extends Assert {

  private static final String SPRING_XML = "/net/sf/mmm/persistence/impl/hibernate/beans-test-persistence-hibernate.xml";

  protected RevisionedPersistenceManager getPersistenceManager() {

    return SpringContainerPool.getInstance(SPRING_XML).get(RevisionedPersistenceManager.class);
  }

  @Test
  public void testPersistence() throws Exception {

    TransactionExecutor transactionExecutor = SpringContainerPool.getInstance(SPRING_XML)
        .get(TransactionExecutor.class);
    // MutableUserSession session = (MutableUserSession) UserSessionAccess.getSession();
    // session.setUser(TestUser.DEFAULT_USER);
    SecurityContextHolder.getContext().setAuthentication(TestUser.DEFAULT_USER);

    DummyRevisionedFooEntity foo = transactionExecutor.doInTransaction(new Callable<DummyRevisionedFooEntity>() {

      public DummyRevisionedFooEntity call() throws Exception {

        return createAndSave();
      }
    });
    final Long fooId = foo.getId();

    // shutdown and restart to ensure we really read from DB
    SpringContainerPool.dispose(SPRING_XML);
    transactionExecutor = SpringContainerPool.getInstance(SPRING_XML).get(TransactionExecutor.class);

    transactionExecutor.doInTransaction(new Callable<Void>() {

      public Void call() throws Exception {

        readAndUpdate(fooId);
        return null;
      }
    });

    // shutdown and restart to ensure we really read from DB
    SpringContainerPool.dispose(SPRING_XML);
    transactionExecutor = SpringContainerPool.getInstance(SPRING_XML).get(TransactionExecutor.class);

    transactionExecutor.doInTransaction(new Callable<Void>() {

      public Void call() throws Exception {

        readAgainAndDelete(fooId);
        return null;
      }
    });

  }

  protected void readAndUpdate(Long fooId) {

    assertNotNull(fooId);
    RevisionedPersistenceManager persistenceManager = getPersistenceManager();
    DummyRevisionedFooEntityDao fooManager = (DummyRevisionedFooEntityDao) persistenceManager
        .getDao(DummyRevisionedFooEntity.class);
    DummyRevisionedFooEntity foo = fooManager.find(fooId);
    assertEquals("This is magic", foo.getValue());
    foo.setValue("It was magic");
    fooManager.save(foo);
  }

  protected void readAgainAndDelete(Long fooId) {

    assertNotNull(fooId);
    Date now = new Date();
    RevisionedPersistenceManager persistenceManager = getPersistenceManager();
    DummyRevisionedFooEntityDao fooManager = (DummyRevisionedFooEntityDao) persistenceManager
        .getDao(DummyRevisionedFooEntity.class);
    DummyRevisionedFooEntity foo = fooManager.load(fooId, RevisionedEntity.LATEST_REVISION);
    assertEquals("It was magic", foo.getValue());
    List<RevisionMetadata> history = fooManager.getRevisionHistoryMetadata(fooId);
    assertEquals(2, history.size());
    RevisionMetadata firstRevision = history.get(0);
    assertEquals(TestUser.DEFAULT_NAME, firstRevision.getCreator());
    RevisionMetadata secondRevision = history.get(1);
    assertEquals(TestUser.DEFAULT_NAME, firstRevision.getCreator());
    assertEquals(firstRevision.getRevision().longValue() + 1, secondRevision.getRevision());
    Date firstDate = firstRevision.getDate();
    Date secondDate = secondRevision.getDate();
    assertTrue(firstDate.before(secondDate));
    assertTrue(secondDate.before(now));
    // duration from first revision to now in millis
    long delta = now.getTime() - firstDate.getTime();
    assertTrue(delta < 6000L);
    DummyRevisionedFooEntity fooHistory = fooManager.load(fooId, firstRevision.getRevision());
    assertNotNull(fooHistory);
    assertEquals("This is magic", fooHistory.getValue());
    fooManager.delete(foo);
  }

  protected DummyRevisionedFooEntity createAndSave() {

    RevisionedPersistenceManager persistenceManager = getPersistenceManager();
    DummyRevisionedFooEntityDao fooManager = (DummyRevisionedFooEntityDao) persistenceManager
        .getDao(DummyRevisionedFooEntity.class);
    assertSame(DummyRevisionedFooEntity.class, fooManager.getEntityClassImplementation());
    DummyRevisionedFooEntity foo = new DummyRevisionedFooEntity();
    foo.setValue("This is magic");
    fooManager.save(foo);
    return foo;
  }

}
