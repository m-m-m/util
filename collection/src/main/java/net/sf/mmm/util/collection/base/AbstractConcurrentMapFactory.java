/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.concurrent.ConcurrentMap;

import net.sf.mmm.util.collection.api.ConcurrentMapFactory;

/**
 * This is the abstract base implementation of the {@link ConcurrentMapFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractConcurrentMapFactory implements ConcurrentMapFactory {

  /**
   * The constructor.
   */
  public AbstractConcurrentMapFactory() {

    super();
  }

  @Override
  public Class<ConcurrentMap> getMapInterface() {

    return ConcurrentMap.class;
  }

  @Override
  public ConcurrentMap createGeneric() {

    return create();
  }

  @Override
  public ConcurrentMap createGeneric(int capacity) {

    return create(capacity);
  }

}
