/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is an implementation of {@link net.sf.mmm.util.collection.api.ConcurrentMapFactory} that creates
 * instances of a <code>ConcurrentHashSet</code>. Since no such class exists in JDK, we provide it via
 * {@link ConcurrentHashMap}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public class ConcurrentHashSetFactory extends AbstractSetFactory {

  /** The singleton instance. */
  public static final ConcurrentHashSetFactory INSTANCE = new ConcurrentHashSetFactory();

  /**
   * The constructor.
   */
  public ConcurrentHashSetFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> Set<E> create() {

    return create(16);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> Set<E> create(int capacity) {

    return Collections.newSetFromMap(new ConcurrentHashMap<E, Boolean>(capacity));
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  @Override
  public Class<? extends Set> getCollectionImplementation() {

    // we do not know the implementation that is a secret of Collections implementation...
    return Set.class;
  }

}
