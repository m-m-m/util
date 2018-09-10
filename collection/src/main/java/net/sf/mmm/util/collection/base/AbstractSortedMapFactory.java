/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.SortedMap;

import net.sf.mmm.util.collection.api.SortedMapFactory;

/**
 * This is the abstract base implementation of the {@link SortedMapFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractSortedMapFactory implements SortedMapFactory {

  /**
   * The constructor.
   */
  public AbstractSortedMapFactory() {

    super();
  }

  @Override
  public Class<SortedMap> getMapInterface() {

    return SortedMap.class;
  }

  @Override
  public SortedMap createGeneric() {

    return create();
  }

  @Override
  public SortedMap createGeneric(int capacity) {

    return create(capacity);
  }

}
