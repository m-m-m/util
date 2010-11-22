/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Queue;

import net.sf.mmm.util.collection.api.QueueFactory;

/**
 * This is the abstract base implementation of the {@link QueueFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractQueueFactory implements QueueFactory {

  /**
   * The constructor.
   */
  public AbstractQueueFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<Queue> getCollectionInterface() {

    return Queue.class;
  }

  /**
   * {@inheritDoc}
   */
  public Queue createGeneric() {

    return create();
  }

  /**
   * {@inheritDoc}
   */
  public Queue createGeneric(int capacity) {

    return create(capacity);
  }

}
