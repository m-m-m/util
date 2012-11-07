/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import java.util.concurrent.Callable;

import net.sf.mmm.persistence.api.RevisionedPersistenceManager;
import net.sf.mmm.transaction.api.TransactionExecutor;
import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.entity.api.RevisionedEntity;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for the persistence.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class EnversTest {

  private static final String SPRING_XML = "/net/sf/mmm/persistence/impl/hibernate/beans-test-persistence-hibernate.xml";

  protected RevisionedPersistenceManager getPersistenceManager() {

    return SpringContainerPool.getInstance(SPRING_XML).getComponent(RevisionedPersistenceManager.class);
  }

  @Test
  public void testPersistence() throws Exception {

    TransactionExecutor transactionExecutor = SpringContainerPool.getInstance(SPRING_XML).getComponent(
        TransactionExecutor.class);

    DummyRevisionedFooEntity foo = transactionExecutor.doInTransaction(new Callable<DummyRevisionedFooEntity>() {

      public DummyRevisionedFooEntity call() throws Exception {

        return createAndSave();
      }
    });
    final Long fooId = foo.getId();

    // shutdown and restart to ensure we really read from DB
    SpringContainerPool.dispose(SPRING_XML);
    transactionExecutor = SpringContainerPool.getInstance(SPRING_XML).getComponent(TransactionExecutor.class);

    transactionExecutor.doInTransaction(new Callable<Void>() {

      public Void call() throws Exception {

        readAndUpdate(fooId);
        return null;
      }
    });

    // shutdown and restart to ensure we really read from DB
    SpringContainerPool.dispose(SPRING_XML);
    transactionExecutor = SpringContainerPool.getInstance(SPRING_XML).getComponent(TransactionExecutor.class);

    transactionExecutor.doInTransaction(new Callable<Void>() {

      public Void call() throws Exception {

        readAgainAndDelete(fooId);
        return null;
      }
    });

  }

  protected void readAndUpdate(Long fooId) {

    Assert.assertNotNull(fooId);
    RevisionedPersistenceManager persistenceManager = getPersistenceManager();
    DummyRevisionedFooEntityManager fooManager = (DummyRevisionedFooEntityManager) persistenceManager
        .getManager(DummyRevisionedFooEntity.class);
    DummyRevisionedFooEntity foo = fooManager.load(fooId);
    Assert.assertEquals("This is magic", foo.getValue());
    foo.setValue("It was magic");
    fooManager.save(foo);
  }

  protected void readAgainAndDelete(Long fooId) {

    Assert.assertNotNull(fooId);
    RevisionedPersistenceManager persistenceManager = getPersistenceManager();
    DummyRevisionedFooEntityManager fooManager = (DummyRevisionedFooEntityManager) persistenceManager
        .getManager(DummyRevisionedFooEntity.class);
    DummyRevisionedFooEntity foo = fooManager.load(fooId, RevisionedEntity.LATEST_REVISION);
    Assert.assertEquals("It was magic", foo.getValue());
    // fooManager.delete(foo);
    DummyRevisionedFooEntity fooHistory = fooManager.load(fooId, 1);
    Assert.assertNotNull(fooHistory);
    Assert.assertEquals("It is magic", fooHistory.getValue());
  }

  protected DummyRevisionedFooEntity createAndSave() {

    RevisionedPersistenceManager persistenceManager = getPersistenceManager();
    DummyRevisionedFooEntityManager fooManager = (DummyRevisionedFooEntityManager) persistenceManager
        .getManager(DummyRevisionedFooEntity.class);
    Assert.assertSame(DummyRevisionedFooEntity.class, fooManager.getEntityClassImplementation());
    DummyRevisionedFooEntity foo = new DummyRevisionedFooEntity();
    foo.setValue("This is magic");
    fooManager.save(foo);
    return foo;
  }

}
