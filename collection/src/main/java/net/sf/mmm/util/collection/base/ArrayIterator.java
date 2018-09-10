/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class implements both {@link Iterator} and {@link Enumeration} to iterate/enumerate a given array. For
 * performance the array is NOT copied so please note that modifications of the array given at construction
 * will directly be reflected by this {@link ArrayIterator}.
 *
 * @param <E> is the generic type of the elements to iterate/enumerate.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class ArrayIterator<E> implements Enumeration<E>, Iterator<E> {

  /** The array to iterate/enumerate. */
  private final E[] array;

  /** The index where iteration stops. The item with this index will NOT be iterated. */
  private final int stopIndex;

  /** The current index in {@code array}. */
  private int index;

  /**
   * The constructor.
   *
   * @param array is the array to iterate/enumerate.
   */
  public ArrayIterator(E[] array) {

    this(array, 0, (array == null) ? 0 : array.length - 1);
  }

  /**
   * The constructor.
   *
   * @param array is the array to iterate/enumerate.
   * @param start is the index of the first item to iterate/enumerate in {@code array}.
   * @param end is the index of the last item to iterate/enumerate in {@code array}.
   */
  public ArrayIterator(E[] array, int start, int end) {

    super();
    int max;
    if (array == null) {
      this.array = null;
      this.index = 0;
      this.stopIndex = 0;
      max = 0;
    } else {
      this.array = array;
      this.index = start;
      this.stopIndex = end + 1;
      max = array.length - 1;
    }
    if ((start > 0) || (end > -1)) {
      if (start > end) {
        throw new IllegalArgumentException("Start (" + start + ") must not exceed end (" + end + ").");
      }
      if (end < max) {
        throw new IllegalArgumentException("End (" + start + ") must not exceed max (" + max + ").");
      }
    }
  }

  @Override
  public boolean hasMoreElements() {

    return hasNext();
  }

  @Override
  public boolean hasNext() {

    return this.index < this.stopIndex;
  }

  @Override
  public E nextElement() {

    return next();
  }

  @Override
  public E next() {

    if (this.index >= this.stopIndex) {
      throw new NoSuchElementException(Integer.toString(this.index));
    }
    return this.array[this.index++];
  }

  @Override
  public void remove() {

    throw new UnsupportedOperationException();
  }

}
