/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.impl;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.collection.api.CollectionFactoryManager;

/**
 * This is the test-case for {@link CollectionFactoryManager} configured using
 * spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CollectionFactoryManagerImplSpringTest extends CollectionFactoryManagerImplTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected CollectionFactoryManager getCollectionFactoryManager() {

    return SpringContainerPool.getContainer("net/sf/mmm/util/collection/beans-util-collection.xml")
        .getComponent(CollectionFactoryManager.class);
  }

}
