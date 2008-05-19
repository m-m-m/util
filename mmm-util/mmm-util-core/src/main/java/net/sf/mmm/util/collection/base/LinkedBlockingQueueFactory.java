/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.collection.api.BlockingQueueFactory} interface that
 * creates instances of {@link java.util.concurrent.LinkedBlockingQueue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LinkedBlockingQueueFactory extends AbstractBlockingQueueFactory {

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Class<LinkedBlockingQueue> getCollectionImplementation() {

    return LinkedBlockingQueue.class;
  }

  /**
   * {@inheritDoc}
   */
  public <E> BlockingQueue<E> create() {

    return new LinkedBlockingQueue<E>();
  }

  /**
   * {@inheritDoc}
   */
  public <E> BlockingQueue<E> create(int capacity) {

    // ATTENTION: capacity is the fixed capacity here, so does NOT make sense
    return new LinkedBlockingQueue<E>();
  }
}
