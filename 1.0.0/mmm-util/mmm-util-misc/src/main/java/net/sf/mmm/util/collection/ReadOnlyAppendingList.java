/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.AbstractList;
import java.util.List;

/**
 * This is an implementation of the {@link List} interface that gives a
 * read-only view on two other lists that appear as if they were concatenated.
 * <br>
 * <b>ATTENTION:</b><br>
 * This implementation is NOT thread-safe.
 * 
 * @param <E> is the generic type of the list elements.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ReadOnlyAppendingList<E> extends AbstractList<E> {

  /** the first list. */
  private final List<? extends E> list1;

  /** the second list. */
  private final List<? extends E> list2;

  /**
   * The constructor.
   * 
   * @param list1 is the first list.
   * @param list2 is the second list. The first element of this list will appear
   *        after the last element of <code>list1</code>.
   */
  public ReadOnlyAppendingList(final List<? extends E> list1, final List<? extends E> list2) {

    super();
    this.list1 = list1;
    this.list2 = list2;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E get(int index) {

    int size = this.list1.size();
    int index2 = index - size;
    if (index2 < 0) {
      return this.list1.get(index);
    } else {
      return this.list2.get(index2);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {

    return this.list1.size() + this.list2.size();
  }

}
