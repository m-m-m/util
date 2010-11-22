/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.SortedSet;

import net.sf.mmm.util.collection.api.SortedSetFactory;

/**
 * This is the abstract base implementation of the {@link SortedSetFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractSortedSetFactory implements SortedSetFactory {

  /**
   * The constructor.
   */
  public AbstractSortedSetFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<SortedSet> getCollectionInterface() {

    return SortedSet.class;
  }

  /**
   * {@inheritDoc}
   */
  public SortedSet createGeneric() {

    return create();
  }

  /**
   * {@inheritDoc}
   */
  public SortedSet createGeneric(int capacity) {

    return create(capacity);
  }

}
