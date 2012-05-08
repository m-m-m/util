/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.Queue;

/**
 * This is the interface for a {@link CollectionFactory} that {@link #create() creates} instances of
 * {@link Queue}.
 * 
 * @see net.sf.mmm.util.collection.base.LinkedListQueueFactory#INSTANCE
 * @see net.sf.mmm.util.collection.base.ConcurrentLinkedQueueFactory#INSTANCE
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public interface QueueFactory extends CollectionFactory<Queue> {

  /**
   * {@inheritDoc}
   */
  <E> Queue<E> create();

  /**
   * {@inheritDoc}
   */
  <E> Queue<E> create(int capacity);

}
