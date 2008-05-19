/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.concurrent.BlockingQueue;

import net.sf.mmm.util.collection.base.LinkedBlockingQueueFactory;

/**
 * This is the interface for a {@link CollectionFactory} that
 * {@link #create() creates} instances of {@link BlockingQueue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public interface BlockingQueueFactory extends CollectionFactory<BlockingQueue> {

  /**
   * {@inheritDoc}
   */
  <E> BlockingQueue<E> create();

  /**
   * {@inheritDoc}
   */
  <E> BlockingQueue<E> create(int capacity);

  /**
   * The default instance creating a
   * {@link java.util.concurrent.LinkedBlockingQueue}.
   */
  BlockingQueueFactory INSTANCE_LINKED_BLOCKING_QUEUE = new LinkedBlockingQueueFactory();
}
