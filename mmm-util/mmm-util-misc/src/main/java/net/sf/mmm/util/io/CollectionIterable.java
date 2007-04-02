/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * This is an implementation of the {@link SizedIterable} interface that adapts
 * a {@link Collection}. The {@link #iterator() iterator} will be read-only.
 * 
 * @param <E>
 *        is the templated element type that can be {@link #iterator() iterated}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CollectionIterable<E> implements SizedIterable<E> {

  /** @see #iterator() */
  private final Collection<E> collection;

  /**
   * The constructor
   * 
   * @param collection
   *        the collection to encapsulate. An
   *        {@link Collections#unmodifiableCollection(Collection) unmodifiable}
   *        view on this collection is created to prevent modifications via
   *        {@link #iterator()}.
   */
  public CollectionIterable(Collection<E> collection) {

    this(collection, true);
  }

  /**
   * The constructor
   * 
   * @param collection
   *        the collection to encapsulate.
   * @param makeUnmodifiable
   *        if <code>true</code> an
   *        {@link Collections#unmodifiableCollection(Collection) unmodifiable}
   *        view on this collection is created to prevent modifications via
   *        {@link #iterator()}, else if <code>false</code> the collection is
   *        used as is.
   */
  public CollectionIterable(Collection<E> collection, boolean makeUnmodifiable) {

    super();
    if (makeUnmodifiable) {
      this.collection = Collections.unmodifiableCollection(collection);
    } else {
      this.collection = collection;
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getSize() {

    return this.collection.size();
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<E> iterator() {

    return this.collection.iterator();
  }

}
