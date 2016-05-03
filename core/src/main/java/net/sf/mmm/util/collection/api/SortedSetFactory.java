/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.SortedSet;

/**
 * This is the interface for a {@link CollectionFactory} that {@link #create() creates} instances of
 * {@link SortedSet}.
 * 
 * @see net.sf.mmm.util.collection.base.TreeSetFactory#INSTANCE
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public interface SortedSetFactory extends CollectionFactory<SortedSet> {

  <E> SortedSet<E> create();

  <E> SortedSet<E> create(int capacity);

}
