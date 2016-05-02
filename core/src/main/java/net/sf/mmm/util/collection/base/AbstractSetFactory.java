/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Set;

import net.sf.mmm.util.collection.api.SetFactory;

/**
 * This is the abstract base implementation of the {@link SetFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractSetFactory implements SetFactory {

  /**
   * The constructor.
   */
  public AbstractSetFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<Set> getCollectionInterface() {

    return Set.class;
  }

  /**
   * {@inheritDoc}
   */
  public Set createGeneric() {

    return create();
  }

  /**
   * {@inheritDoc}
   */
  public Set createGeneric(int capacity) {

    return create(capacity);
  }

}
