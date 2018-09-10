/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.concurrent.ConcurrentNavigableMap;

import net.sf.mmm.util.collection.api.ConcurrentNavigableMapFactory;

/**
 * This is the abstract base implementation of the {@link ConcurrentNavigableMapFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.7.0
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractConcurrentNavigableMapFactory implements ConcurrentNavigableMapFactory {

  /**
   * The constructor.
   */
  public AbstractConcurrentNavigableMapFactory() {

    super();
  }

  @Override
  public Class<ConcurrentNavigableMap> getMapInterface() {

    return ConcurrentNavigableMap.class;
  }

  @Override
  public ConcurrentNavigableMap createGeneric() {

    return create();
  }

  @Override
  public ConcurrentNavigableMap createGeneric(int capacity) {

    return create(capacity);
  }

}
