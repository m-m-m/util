/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.AbstractList;
import java.util.List;

/**
 * This is an implementation of the {@link List} interface that gives a read-only view on other lists that
 * appear as if they were concatenated. <br>
 * <b>ATTENTION:</b><br>
 * This implementation is NOT thread-safe. It may cause problems if a method is called while one of the
 * concatenated sub-lists is modified.
 * 
 * @param <E> is the generic type of the list elements.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AppendingList<E> extends AbstractList<E> {

  /** the lists. */
  private final List<? extends E>[] lists;

  /**
   * The constructor.
   * 
   * @param lists is the array of lists to "concatenate" in the order of the array.
   */
  public AppendingList(List<? extends E>... lists) {

    super();
    this.lists = lists;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E get(int index) {

    int subIndex = index;
    for (List<? extends E> list : this.lists) {
      int size = list.size();
      if (subIndex < size) {
        return list.get(subIndex);
      } else {
        subIndex = subIndex - size;
      }
    }
    throw new IndexOutOfBoundsException(Integer.toString(index));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {

    int size = 0;
    for (List<? extends E> list : this.lists) {
      size = size + list.size();
    }
    return size;
  }

}
