/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.collection.api.QueueFactory} interface that creates
 * instances of {@link ConcurrentLinkedQueue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConcurrentLinkedQueueFactory extends AbstractQueueFactory {

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Class<ConcurrentLinkedQueue> getCollectionImplementation() {

    return ConcurrentLinkedQueue.class;
  }

  /**
   * {@inheritDoc}
   */
  public <E> Queue<E> create() {

    return new ConcurrentLinkedQueue<E>();
  }

  /**
   * {@inheritDoc}
   */
  public <E> Queue<E> create(int capacity) {

    // capacity does NOT make sense here...
    return new ConcurrentLinkedQueue<E>();
  }
}
