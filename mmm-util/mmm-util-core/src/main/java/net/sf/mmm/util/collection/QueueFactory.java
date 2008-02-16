/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This is the interface for a {@link CollectionFactory} that
 * {@link #create() creates} instances of {@link Queue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public interface QueueFactory extends CollectionFactory<Queue> {

  /**
   * {@inheritDoc}
   */
  <E> Queue<E> create();

  /**
   * {@inheritDoc}
   */
  <E> Queue<E> create(int capacity);

  /** The default instance creating a {@link LinkedList}. */
  QueueFactory INSTANCE_LINKED_LIST = new AbstractQueueFactory() {

    public Class<? extends Queue> getCollectionImplementation() {

      return LinkedList.class;
    }

    public <E> Queue<E> create() {

      return new LinkedList<E>();
    }

    public <E> Queue<E> create(int capacity) {

      // capacity does NOT make sense here...
      return new LinkedList<E>();
    }
  };
}
