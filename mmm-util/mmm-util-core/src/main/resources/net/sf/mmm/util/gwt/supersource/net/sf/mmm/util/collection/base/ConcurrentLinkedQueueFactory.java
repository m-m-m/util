/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Queue;
import java.util.LinkedList;

import net.sf.mmm.util.collection.api.QueueFactory;

/**
 * This is an implementation of the {@link net.sf.mmm.util.collection.api.QueueFactory} interface that creates
 * instances of {@link ConcurrentLinkedQueue}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class ConcurrentLinkedQueueFactory extends AbstractQueueFactory {

  /** The singleton instance. */
  public static final QueueFactory INSTANCE = new ConcurrentLinkedQueueFactory();

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  public Class<LinkedList> getCollectionImplementation() {

    return LinkedList.class;
  }

  /**
   * {@inheritDoc}
   */
  public <E> Queue<E> create() {

    return new LinkedList<E>();
  }

  /**
   * {@inheritDoc}
   */
  public <E> Queue<E> create(int capacity) {

    return new LinkedList<E>(capacity);
  }
}
