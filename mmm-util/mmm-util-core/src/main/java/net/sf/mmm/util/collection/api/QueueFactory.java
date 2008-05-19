/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.Queue;

import net.sf.mmm.util.collection.base.ConcurrentLinkedQueueFactory;
import net.sf.mmm.util.collection.base.LinkedListQueueFactory;

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

  /** The default instance creating a {@link java.util.LinkedList}. */
  QueueFactory INSTANCE_LINKED_LIST = new LinkedListQueueFactory();

  /** An instance creating a {@link java.util.concurrent.ConcurrentLinkedQueue}. */
  QueueFactory INSTANCE_CONCURRENT_LINKED_QUEUE = new ConcurrentLinkedQueueFactory();

}
