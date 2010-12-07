/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.util.collection.api.SetFactory;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.collection.api.SetFactory} interface that creates
 * instances of {@link HashSet}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class HashSetFactory extends AbstractSetFactory {

  /** The singleton instance. */
  public static final SetFactory INSTANCE = new HashSetFactory();

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  public Class<HashSet> getCollectionImplementation() {

    return HashSet.class;
  }

  /**
   * {@inheritDoc}
   */
  public <E> Set<E> create() {

    return new HashSet<E>();
  }

  /**
   * {@inheritDoc}
   */
  public <E> Set<E> create(int capacity) {

    return new HashSet<E>(capacity);
  }
}
