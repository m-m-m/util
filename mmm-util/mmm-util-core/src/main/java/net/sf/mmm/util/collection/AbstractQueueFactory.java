/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.Queue;

/**
 * This is the abstract base implementation of the {@link QueueFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
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
