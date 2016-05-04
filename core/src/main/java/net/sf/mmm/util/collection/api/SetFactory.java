/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.Set;

/**
 * This is the interface for a {@link CollectionFactory} that {@link #create() creates} instances of {@link Set}.
 *
 * @see net.sf.mmm.util.collection.base.HashSetFactory#INSTANCE
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public interface SetFactory extends CollectionFactory<Set> {

  @Override
  <E> Set<E> create();

  @Override
  <E> Set<E> create(int capacity);

}
