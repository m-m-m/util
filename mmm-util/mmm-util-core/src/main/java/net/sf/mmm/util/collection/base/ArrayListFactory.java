/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.collection.api.ListFactory} interface that creates
 * instances of {@link ArrayList}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ArrayListFactory extends AbstractListFactory {

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Class<ArrayList> getCollectionImplementation() {

    return ArrayList.class;
  }

  /**
   * {@inheritDoc}
   */
  public <E> List<E> create() {

    return new ArrayList<E>();
  }

  /**
   * {@inheritDoc}
   */
  public <E> List<E> create(int capacity) {

    return new ArrayList<E>(capacity);
  }
}
