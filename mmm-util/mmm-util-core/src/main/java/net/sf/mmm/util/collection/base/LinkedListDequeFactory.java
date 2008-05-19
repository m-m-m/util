/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Deque;
import java.util.LinkedList;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.collection.api.DequeFactory} interface that creates
 * instances of {@link LinkedList}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LinkedListDequeFactory extends AbstractDequeFactory {

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Class<LinkedList> getCollectionImplementation() {

    return LinkedList.class;
  }

  /**
   * {@inheritDoc}
   */
  public <E> Deque<E> create() {

    return new LinkedList<E>();
  }

  /**
   * {@inheritDoc}
   */
  public <E> Deque<E> create(int capacity) {

    // capacity does NOT make sense here...
    return new LinkedList<E>();
  }
}
