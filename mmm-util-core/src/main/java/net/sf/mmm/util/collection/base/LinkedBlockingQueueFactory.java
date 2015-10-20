/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.sf.mmm.util.collection.api.BlockingQueueFactory;

/**
 * This is an implementation of the {@link net.sf.mmm.util.collection.api.BlockingQueueFactory} interface that creates
 * instances of {@link java.util.concurrent.LinkedBlockingQueue}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class LinkedBlockingQueueFactory extends AbstractBlockingQueueFactory {

  /** The singleton instance. */
  public static final BlockingQueueFactory INSTANCE = new LinkedBlockingQueueFactory();

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  public Class<LinkedBlockingQueue> getCollectionImplementation() {

    return LinkedBlockingQueue.class;
  }

  /**
   * {@inheritDoc}
   */
  public <E> BlockingQueue<E> create() {

    return new LinkedBlockingQueue<>();
  }

  /**
   * {@inheritDoc}
   */
  public <E> BlockingQueue<E> create(int capacity) {

    // ATTENTION: capacity is the fixed capacity here, so does NOT make sense
    return new LinkedBlockingQueue<>();
  }
}
