/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.NavigableSet;
import java.util.TreeSet;

import net.sf.mmm.util.collection.api.NavigableSetFactory;

/**
 * This is an implementation of the {@link net.sf.mmm.util.collection.api.NavigableSetFactory} interface that creates
 * instances of {@link TreeSet}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.7.0
 */
public class NavigableTreeSetFactory extends AbstractNavigableSetFactory {

  /** The singleton instance. */
  public static final NavigableSetFactory INSTANCE = new NavigableTreeSetFactory();

  @Override
  @SuppressWarnings("rawtypes")
  public Class<TreeSet> getCollectionImplementation() {

    return TreeSet.class;
  }

  @Override
  public <E> NavigableSet<E> create() {

    return new TreeSet<>();
  }

  @Override
  public <E> NavigableSet<E> create(int capacity) {

    // capacity does NOT make sense here...
    return new TreeSet<>();
  }
}
