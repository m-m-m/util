/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.concurrent.BlockingQueue;

/**
 * This is the interface for a {@link CollectionFactory} that {@link #create() creates} instances of
 * {@link BlockingQueue}.
 * 
 * @see net.sf.mmm.util.collection.base.LinkedBlockingQueueFactory#INSTANCE
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public interface BlockingQueueFactory extends CollectionFactory<BlockingQueue> {

  /**
   * {@inheritDoc}
   */
  <E> BlockingQueue<E> create();

  /**
   * {@inheritDoc}
   */
  <E> BlockingQueue<E> create(int capacity);
}
