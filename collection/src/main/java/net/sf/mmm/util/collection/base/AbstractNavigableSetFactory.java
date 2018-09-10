/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.NavigableSet;

import net.sf.mmm.util.collection.api.NavigableSetFactory;

/**
 * This is the abstract base implementation of the {@link NavigableSetFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.7.0
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractNavigableSetFactory implements NavigableSetFactory {

  /**
   * The constructor.
   */
  public AbstractNavigableSetFactory() {

    super();
  }

  @Override
  public Class<NavigableSet> getCollectionInterface() {

    return NavigableSet.class;
  }

  @Override
  public NavigableSet createGeneric() {

    return create();
  }

  @Override
  public NavigableSet createGeneric(int capacity) {

    return create(capacity);
  }

}
