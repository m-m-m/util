/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.HashSet;
import java.util.Set;

/**
 * This is the interface for a {@link CollectionFactory} that guarantees to
 * {@link #create() create} a {@link Set}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SetFactory extends CollectionFactory {

  /**
   * {@inheritDoc}
   */
  <E> Set<E> create();

  /**
   * {@inheritDoc}
   */
  <E> Set<E> create(int capacity);

  /** The default instance creating a {@link HashSet}. */
  SetFactory INSTANCE_HASH_SET = new SetFactory() {

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
  };

}
