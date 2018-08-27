/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.concurrent.BlockingQueue;

import net.sf.mmm.util.collection.api.BlockingQueueFactory;

/**
 * This is the abstract base implementation of the {@link BlockingQueueFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractBlockingQueueFactory implements BlockingQueueFactory {

  /**
   * The constructor.
   */
  public AbstractBlockingQueueFactory() {

    super();
  }

  @Override
  public Class<BlockingQueue> getCollectionInterface() {

    return BlockingQueue.class;
  }

  @Override
  public BlockingQueue createGeneric() {

    return create();
  }

  @Override
  public BlockingQueue createGeneric(int capacity) {

    return create(capacity);
  }

}
