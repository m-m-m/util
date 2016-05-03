/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.mmm.util.collection.api.SortedSetFactory;

/**
 * This is an implementation of the {@link net.sf.mmm.util.collection.api.SortedSetFactory} interface that creates
 * instances of {@link TreeSet}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class TreeSetFactory extends AbstractSortedSetFactory {

  /** The singleton instance. */
  public static final SortedSetFactory INSTANCE = new TreeSetFactory();

  @SuppressWarnings("rawtypes")
  public Class<TreeSet> getCollectionImplementation() {

    return TreeSet.class;
  }

  @Override
  public <E> SortedSet<E> create() {

    return new TreeSet<>();
  }

  @Override
  public <E> SortedSet<E> create(int capacity) {

    // capacity does NOT make sense here...
    return new TreeSet<>();
  }
}
