/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;

/**
 * This is the test-case for {@link CollectionFactoryManagerImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class CollectionFactoryManagerImplTest {

  protected CollectionFactoryManager getCollectionFactoryManager() {

    return CollectionFactoryManagerImpl.getInstance();
  }

  protected <C extends Collection> void checkCollection(Class<C> collectionInterface) {

    CollectionFactory<C> factory = getCollectionFactoryManager().getFactory(collectionInterface);
    assertNotNull(factory);
    assertTrue(collectionInterface.isAssignableFrom(factory.getCollectionInterface()));
    C collection = factory.createGeneric();
    assertTrue(collectionInterface.isInstance(collection));
    assertTrue(collection.isEmpty());
  }

  @Test
  public void testCollectionTypes() {

    checkCollection(Collection.class);
    checkCollection(List.class);
    checkCollection(Set.class);
    checkCollection(SortedSet.class);
    checkCollection(Queue.class);
    checkCollection(BlockingQueue.class);
    checkCollection(Deque.class);
  }

}
