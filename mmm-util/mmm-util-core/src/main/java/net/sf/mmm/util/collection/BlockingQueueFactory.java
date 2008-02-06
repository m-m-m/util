/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This is the interface for a {@link CollectionFactory} that
 * {@link #create() creates} instances of {@link BlockingQueue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface BlockingQueueFactory extends CollectionFactory<BlockingQueue> {

  /**
   * {@inheritDoc}
   */
  <E> BlockingQueue<E> create();

  /**
   * {@inheritDoc}
   */
  <E> BlockingQueue<E> create(int capacity);

  /** The default instance creating a {@link LinkedBlockingQueue}. */
  BlockingQueueFactory INSTANCE_LINKED_BLOCKING_QUEUE = new AbstractBlockingQueueFactory() {

    public Class<? extends BlockingQueue> getCollectionImplementation() {

      return LinkedBlockingQueue.class;
    }

    public <E> BlockingQueue<E> create() {

      return new LinkedBlockingQueue<E>();
    }

    public <E> BlockingQueue<E> create(int capacity) {

      // ATTENTION: capacity is the fixed capacity here, so does NOT make sense
      return new LinkedBlockingQueue<E>();
    }
  };
}
