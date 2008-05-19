/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.HashSet;
import java.util.Set;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.collection.api.SetFactory} interface that creates
 * instances of {@link HashSet}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class HashSetFactory extends AbstractSetFactory {

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
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
