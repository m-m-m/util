/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.impl;

import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.component.impl.SpringContainerPool;

/**
 * This is the test-case for {@link CollectionFactoryManager} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CollectionFactoryManagerImplSpringTest extends CollectionFactoryManagerImplTest {

  @Override
  protected CollectionFactoryManager getCollectionFactoryManager() {

    return SpringContainerPool.getInstance().get(CollectionFactoryManager.class);
  }

}
