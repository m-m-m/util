/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.List;

/**
 * This is the interface for a {@link CollectionFactory} that {@link #create() creates} instances of
 * {@link List}.
 * 
 * @see net.sf.mmm.util.collection.base.ArrayListFactory#INSTANCE
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public interface ListFactory extends CollectionFactory<List> {

  /**
   * {@inheritDoc}
   */
  <E> List<E> create();

  /**
   * {@inheritDoc}
   */
  <E> List<E> create(int capacity);

}
