/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.Deque;
import java.util.LinkedList;

/**
 * This is the interface for a {@link CollectionFactory} that
 * {@link #create() creates} instances of {@link Deque}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DequeFactory extends CollectionFactory<Deque> {

  /**
   * {@inheritDoc}
   */
  <E> Deque<E> create();

  /**
   * {@inheritDoc}
   */
  <E> Deque<E> create(int capacity);

  /** The default instance creating a {@link LinkedList}. */
  DequeFactory INSTANCE_LINKED_LIST = new AbstractDequeFactory() {

    public Class<? extends Deque> getCollectionImplementation() {

      return LinkedList.class;
    }

    public <E> Deque<E> create() {

      return new LinkedList<E>();
    }

    public <E> Deque<E> create(int capacity) {

      // capacity does NOT make sense here...
      return new LinkedList<E>();
    }
  };
}
