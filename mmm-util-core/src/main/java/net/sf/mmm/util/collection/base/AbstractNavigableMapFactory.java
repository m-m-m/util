/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.NavigableMap;

import net.sf.mmm.util.collection.api.NavigableMapFactory;

/**
 * This is the abstract base implementation of the {@link NavigableMapFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractNavigableMapFactory implements NavigableMapFactory {

  /**
   * The constructor.
   */
  public AbstractNavigableMapFactory() {

    super();
  }

  @Override
  public Class<NavigableMap> getMapInterface() {

    return NavigableMap.class;
  }

  @Override
  public NavigableMap createGeneric() {

    return create();
  }

  @Override
  public NavigableMap createGeneric(int capacity) {

    return create(capacity);
  }

}
