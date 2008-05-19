/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.Deque;

import net.sf.mmm.util.collection.base.LinkedListDequeFactory;

/**
 * This is the interface for a {@link CollectionFactory} that
 * {@link #create() creates} instances of {@link Deque}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public interface DequeFactory extends CollectionFactory<Deque> {

  /**
   * {@inheritDoc}
   */
  <E> Deque<E> create();

  /**
   * {@inheritDoc}
   */
  <E> Deque<E> create(int capacity);

  /** The default instance creating a {@link java.util.LinkedList}. */
  DequeFactory INSTANCE_LINKED_LIST = new LinkedListDequeFactory();
}
