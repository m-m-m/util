/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.concurrent.BlockingQueue;

/**
 * This is the abstract base implementation of the {@link BlockingQueueFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBlockingQueueFactory implements BlockingQueueFactory {

  /**
   * The constructor.
   */
  public AbstractBlockingQueueFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<BlockingQueue> getCollectionInterface() {

    return BlockingQueue.class;
  }

  /**
   * {@inheritDoc}
   */
  public BlockingQueue createGeneric() {

    return create();
  }

  /**
   * {@inheritDoc}
   */
  public BlockingQueue createGeneric(int capacity) {

    return create(capacity);
  }

}
