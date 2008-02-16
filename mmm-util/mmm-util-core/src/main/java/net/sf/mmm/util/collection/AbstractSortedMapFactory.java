/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.SortedMap;

/**
 * This is the abstract base implementation of the {@link SortedMapFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public abstract class AbstractSortedMapFactory implements SortedMapFactory {

  /**
   * The constructor.
   */
  public AbstractSortedMapFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<SortedMap> getMapInterface() {

    return SortedMap.class;
  }

  /**
   * {@inheritDoc}
   */
  public SortedMap createGeneric() {

    return create();
  }

  /**
   * {@inheritDoc}
   */
  public SortedMap createGeneric(int capacity) {

    return create(capacity);
  }

}
