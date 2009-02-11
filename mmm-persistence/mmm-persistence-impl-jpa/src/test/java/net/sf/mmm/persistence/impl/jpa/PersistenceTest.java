/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import org.junit.Assert;
import org.junit.Test;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.persistence.api.PersistenceEntityManager;
import net.sf.mmm.persistence.api.PersistenceManager;

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
  public void testPersistence() {

    PersistenceManager pm = getPersistenceManager();
    PersistenceEntityManager<TestFooEntity> fooManager = pm.getManager(TestFooEntity.class);
    Assert.assertSame(TestFooEntity.class, fooManager.getEntityClass());
    Assert.assertTrue(fooManager instanceof TestFooEntityManager);
    PersistenceEntityManager<TestBarEntity> barManager = pm.getManager(TestBarEntity.class);
    Assert.assertSame(TestBarEntity.class, barManager.getEntityClass());
    Assert.assertTrue(barManager instanceof TestBarEntityManager);
  }

}
