/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.NavigableSet;

/**
 * This is the interface for a {@link CollectionFactory} that {@link #create() creates} instances of
 * {@link NavigableSet}.
 *
 * @see net.sf.mmm.util.collection.base.NavigableTreeMapFactory#INSTANCE
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.7.0
 */
@SuppressWarnings("rawtypes")
public interface NavigableSetFactory extends CollectionFactory<NavigableSet> {

  @Override
  <E> NavigableSet<E> create();

  @Override
  <E> NavigableSet<E> create(int capacity);

}
