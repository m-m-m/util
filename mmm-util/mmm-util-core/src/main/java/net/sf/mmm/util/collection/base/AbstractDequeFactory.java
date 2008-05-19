/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Deque;

import net.sf.mmm.util.collection.api.DequeFactory;

/**
 * This is the abstract base implementation of the {@link DequeFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public abstract class AbstractDequeFactory implements DequeFactory {

  /**
   * The constructor.
   */
  public AbstractDequeFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<Deque> getCollectionInterface() {

    return Deque.class;
  }

  /**
   * {@inheritDoc}
   */
  public Deque createGeneric() {

    return create();
  }

  /**
   * {@inheritDoc}
   */
  public Deque createGeneric(int capacity) {

    return create(capacity);
  }

}
