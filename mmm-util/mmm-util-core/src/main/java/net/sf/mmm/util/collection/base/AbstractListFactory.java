/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.List;

import net.sf.mmm.util.collection.api.ListFactory;

/**
 * This is the abstract base implementation of the {@link ListFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("unchecked")
public abstract class AbstractListFactory implements ListFactory {

  /**
   * The constructor.
   */
  public AbstractListFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<List> getCollectionInterface() {

    return List.class;
  }

  /**
   * {@inheritDoc}
   */
  public List createGeneric() {

    return create();
  }

  /**
   * {@inheritDoc}
   */
  public List createGeneric(int capacity) {

    return create(capacity);
  }

}
