/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.List;

import net.sf.mmm.util.collection.base.ArrayListFactory;

/**
 * This is the interface for a {@link CollectionFactory} that
 * {@link #create() creates} instances of {@link List}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public interface ListFactory extends CollectionFactory<List> {

  /**
   * {@inheritDoc}
   */
  <E> List<E> create();

  /**
   * {@inheritDoc}
   */
  <E> List<E> create(int capacity);

  /** The default instance creating an {@link java.util.ArrayList}. */
  ListFactory INSTANCE_ARRAY_LIST = new ArrayListFactory();

}
