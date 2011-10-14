/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import java.util.concurrent.Callable;

import net.sf.mmm.persistence.api.PersistenceManager;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyBarEntity;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyBarEntityManager;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyFooEntityManager;
import net.sf.mmm.persistence.impl.jpa.test.impl.DummyBarEntityImpl;
import net.sf.mmm.persistence.impl.jpa.test.impl.DummyFooEntityImpl;
import net.sf.mmm.transaction.api.TransactionExecutor;
import net.sf.mmm.util.component.impl.SpringContainerPool;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for the persistence.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PersistenceTest {

  private static final String SPRING_XML = "/net/sf/mmm/persistence/impl/jpa/beans-test-persistence-jpa.xml";

  protected PersistenceManager getPersistenceManager() {

    return SpringContainerPool.getInstance(SPRING_XML).getComponent(PersistenceManager.class);
  }

  @Test
  public void testPersistence() throws Exception {

    TransactionExecutor transactionExecutor = SpringContainerPool.getInstance(SPRING_XML)
        .getComponent(TransactionExecutor.class);

    DummyFooEntityImpl foo = transactionExecutor.doInTransaction(new Callable<DummyFooEntityImpl>() {

      public DummyFooEntityImpl call() throws Exception {

        return createAndSave();
      }
    });
    final Integer fooId = foo.getId();
    final Integer barId = foo.getBar().getId();
    System.out.println("--------------------------------------------------------");
    System.out.println(fooId);
    System.out.println(barId);
    System.out.println("--------------------------------------------------------");
    SpringContainerPool.dispose(SPRING_XML);
    transactionExecutor = SpringContainerPool.getInstance(SPRING_XML).getComponent(
        TransactionExecutor.class);

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
    DummyFooEntityManager fooManager = (DummyFooEntityManager) persistenceManager
        .getManager(DummyFooEntityImpl.class);
    DummyFooEntityImpl foo = fooManager.load(fooId);
    Assert.assertEquals(42, foo.getNumber());
    DummyBarEntity bar = foo.getBar();
    Assert.assertNotNull(bar);
    Assert.assertEquals("value42", bar.getValue());
    foo.setNumber(24);
    DummyBarEntityImpl bar2 = new DummyBarEntityImpl();
    bar2.setValue("24value");
    DummyBarEntityManager barManager = (DummyBarEntityManager) persistenceManager
        .getManager(DummyBarEntityImpl.class);
    barManager.save(bar2);
    foo.setBar(bar2);
    fooManager.save(foo);
  }

  protected void readAgainAndDelete(Integer fooId, Integer barId) {

    Assert.assertNotNull(fooId);
    PersistenceManager persistenceManager = getPersistenceManager();
    DummyFooEntityManager fooManager = (DummyFooEntityManager) persistenceManager
        .getManager(DummyFooEntityImpl.class);
    DummyFooEntityImpl foo = fooManager.load(fooId);
    Assert.assertEquals(24, foo.getNumber());
    DummyBarEntity bar2 = foo.getBar();
    Assert.assertNotNull(bar2);
    Assert.assertEquals("24value", bar2.getValue());
    DummyBarEntityManager barManager = (DummyBarEntityManager) persistenceManager
        .getManager(DummyBarEntityImpl.class);
    Assert.assertNotNull(barId);
    DummyBarEntity bar = barManager.load(barId);
    Assert.assertEquals("value42", bar.getValue());
    barManager.delete(bar);
    fooManager.delete(foo);
  }

  protected DummyFooEntityImpl createAndSave() {

    PersistenceManager persistenceManager = getPersistenceManager();
    DummyFooEntityManager fooManager = (DummyFooEntityManager) persistenceManager
        .getManager(DummyFooEntityImpl.class);
    Class<? super DummyFooEntityImpl> entityClassReadOnly = fooManager.getEntityClassReadOnly();
    Assert.assertSame(DummyFooEntityImpl.class, fooManager.getEntityClassImplementation());
    Assert.assertTrue(fooManager instanceof DummyFooEntityManager);
    DummyBarEntityManager barManager = (DummyBarEntityManager) persistenceManager
        .getManager(DummyBarEntityImpl.class);
    Assert.assertSame(DummyBarEntityImpl.class, barManager.getEntityClassImplementation());
    Assert.assertTrue(barManager instanceof DummyBarEntityManager);
    DummyBarEntityImpl bar = new DummyBarEntityImpl();
    bar.setValue("value42");
    barManager.save(bar);
    DummyFooEntityImpl foo = new DummyFooEntityImpl();
    foo.setNumber(42);
    foo.setBar(bar);
    fooManager.save(foo);
    return foo;
  }

}
