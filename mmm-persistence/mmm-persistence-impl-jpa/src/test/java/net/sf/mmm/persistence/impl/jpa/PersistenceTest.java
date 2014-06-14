/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import java.util.concurrent.Callable;

import net.sf.mmm.persistence.api.GenericDao;
import net.sf.mmm.persistence.api.PersistenceManager;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyBarEntity;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyBarEntityDao;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyFooEntity;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyFooEntityDao;
import net.sf.mmm.persistence.impl.jpa.test.impl.DummyBarEntityImpl;
import net.sf.mmm.persistence.impl.jpa.test.impl.DummyFooEntityImpl;
import net.sf.mmm.transaction.api.TransactionExecutor;
import net.sf.mmm.util.component.impl.SpringContainerPool;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for the persistence. It performs a full integration test using hibernate as OR mapper
 * and an embedded database.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PersistenceTest {

  private static final String SPRING_XML = "/net/sf/mmm/persistence/impl/jpa/beans-test-persistence-jpa.xml";

  protected PersistenceManager getPersistenceManager() {

    return SpringContainerPool.getInstance(SPRING_XML).get(PersistenceManager.class);
  }

  @Test
  public void testPersistence() throws Exception {

    TransactionExecutor transactionExecutor = SpringContainerPool.getInstance(SPRING_XML)
        .get(TransactionExecutor.class);

    DummyFooEntity foo = transactionExecutor.doInTransaction(new Callable<DummyFooEntity>() {

      public DummyFooEntity call() throws Exception {

        return createAndSave();
      }
    });
    final Integer fooId = foo.getId();
    final Long barId = foo.getBar().getId();

    System.out.println("--------------------------------------------------------");
    System.out.println(fooId);
    System.out.println(barId);
    System.out.println("--------------------------------------------------------");
    SpringContainerPool.dispose(SPRING_XML);
    transactionExecutor = SpringContainerPool.getInstance(SPRING_XML).get(TransactionExecutor.class);

    transactionExecutor.doInTransaction(new Callable<Void>() {

      public Void call() throws Exception {

        readAndUpdate(fooId);
        return null;
      }
    });

    transactionExecutor.doInTransaction(new Callable<Void>() {

      public Void call() throws Exception {

        readAgainAndDelete(fooId, barId);
        return null;
      }
    });

  }

  protected void readAndUpdate(Integer fooId) {

    Assert.assertNotNull(fooId);
    PersistenceManager persistenceManager = getPersistenceManager();
    DummyFooEntityDao fooManager = (DummyFooEntityDao) persistenceManager.getDao(DummyFooEntity.class);
    DummyFooEntity foo = fooManager.find(fooId);
    Assert.assertEquals(42, foo.getNumber());
    DummyBarEntity bar = foo.getBar();
    Assert.assertNotNull(bar);
    Assert.assertEquals("value42", bar.getValue());
    foo.setNumber(24);
    DummyBarEntityDao barManager = (DummyBarEntityDao) persistenceManager.getDao(DummyBarEntity.class);
    DummyBarEntity bar2 = barManager.create();
    bar2.setValue("24value");
    barManager.save(bar2);
    foo.setBar(bar2);
    fooManager.save(foo);
  }

  protected void readAgainAndDelete(Integer fooId, Long barId) {

    Assert.assertNotNull(fooId);
    PersistenceManager persistenceManager = getPersistenceManager();
    DummyFooEntityDao fooManager = (DummyFooEntityDao) persistenceManager.getDao(DummyFooEntity.class);
    DummyFooEntity foo = fooManager.find(fooId);
    Assert.assertEquals(24, foo.getNumber());
    DummyBarEntity bar2 = foo.getBar();
    Assert.assertNotNull(bar2);
    Assert.assertEquals("24value", bar2.getValue());
    DummyBarEntityDao barManager = (DummyBarEntityDao) persistenceManager.getDao(DummyBarEntity.class);
    Assert.assertNotNull(barId);
    DummyBarEntity bar = barManager.find(barId);
    Assert.assertEquals("value42", bar.getValue());
    barManager.delete(bar);
    fooManager.delete(foo);
  }

  protected DummyFooEntity createAndSave() {

    PersistenceManager persistenceManager = getPersistenceManager();
    GenericDao<Integer, DummyFooEntity> fooDao = persistenceManager.getDao(DummyFooEntity.class);
    Assert.assertTrue(fooDao instanceof DummyFooEntityDao);
    Assert.assertSame(DummyFooEntityImpl.class, fooDao.getEntityClass());
    Assert.assertSame(fooDao, persistenceManager.getDao(DummyFooEntityImpl.class));
    GenericDao<Long, DummyBarEntity> barDao = persistenceManager.getDao(DummyBarEntity.class);
    Assert.assertTrue(barDao instanceof DummyBarEntityDao);
    Assert.assertSame(DummyBarEntityImpl.class, barDao.getEntityClass());
    Assert.assertSame(barDao, persistenceManager.getDao(DummyBarEntityImpl.class));
    DummyBarEntity bar = barDao.create();
    bar.setValue("value42");
    barDao.save(bar);
    DummyFooEntity foo = fooDao.create();
    foo.setNumber(42);
    foo.setBar(bar);
    fooDao.save(foo);
    return foo;
  }
}
