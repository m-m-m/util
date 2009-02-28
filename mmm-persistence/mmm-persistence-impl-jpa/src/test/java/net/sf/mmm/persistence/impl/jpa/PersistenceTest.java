/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.Test;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.persistence.api.PersistenceEntityManager;
import net.sf.mmm.persistence.api.PersistenceManager;
import net.sf.mmm.transaction.api.TransactionExecutor;

/**
 * This is the test-case for the persistence.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PersistenceTest {

  protected PersistenceManager getPersistenceManager() {

    return SpringContainerPool
        .getContainer("net/sf/mmm/persistence/impl/jpa/beans-persistence.xml").getComponent(
            PersistenceManager.class);
  }

  @Test
  public void testPersistence() throws Exception {

    TransactionExecutor transactionExecutor = SpringContainerPool.getContainer(
        "net/sf/mmm/persistence/impl/jpa/beans-persistence.xml").getComponent(
        TransactionExecutor.class);

    TestFooEntity foo = transactionExecutor.doInTransaction(new Callable<TestFooEntity>() {

      public TestFooEntity call() throws Exception {

        return doTestEntities();
      }
    });
    System.out.println(foo.getId());
  }

  protected TestFooEntity doTestEntities() {

    PersistenceManager persistenceManager = getPersistenceManager();
    PersistenceEntityManager<TestFooEntity> fooManager = persistenceManager
        .getManager(TestFooEntity.class);
    Assert.assertSame(TestFooEntity.class, fooManager.getEntityClass());
    Assert.assertTrue(fooManager instanceof TestFooEntityManager);
    PersistenceEntityManager<TestBarEntity> barManager = persistenceManager
        .getManager(TestBarEntity.class);
    Assert.assertSame(TestBarEntity.class, barManager.getEntityClass());
    Assert.assertTrue(barManager instanceof TestBarEntityManager);
    TestBarEntity bar = new TestBarEntity();
    bar.setValue("value42");
    barManager.save(bar);
    TestFooEntity foo = new TestFooEntity();
    foo.setNumber(42);
    foo.setBar(bar);
    fooManager.save(foo);
    return foo;
  }

}
