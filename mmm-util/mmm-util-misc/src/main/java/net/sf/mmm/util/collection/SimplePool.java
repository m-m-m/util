/* $Id$ */
package net.sf.mmm.util.collection;

import java.lang.reflect.Array;

/**
 * This is the default implementation of the {@link Pool} interface.
 * 
 * @param <E>
 *        is the templated type of the elements in the pool.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimplePool<E> extends AbstractPool<E> {

  /** the pool */
  private final E[] pool;

  /** @see #size() */
  private int size;

  /**
   * The constructor.
   * 
   * @param type
   *        is the type of the element.
   * @param maximumSize
   *        is the {@link #getMaximumSize() maximum size} of the pool.
   */
  public SimplePool(Class<E> type, int maximumSize) {

    super();
    this.pool = (E[]) Array.newInstance(type, maximumSize);
    this.size = 0;
  }

  /**
   * @see net.sf.mmm.util.collection.Pool#add(java.lang.Object)
   */
  public void add(E element) {

    if (this.size < this.pool.length) {
      this.pool[this.size++] = element;
    }
  }

  /**
   * @see net.sf.mmm.util.collection.Pool#getMaximumSize()
   */
  public int getMaximumSize() {

    return this.pool.length;
  }

  /**
   * @see net.sf.mmm.util.collection.Pool#size()
   */
  public int size() {

    return this.size;
  }

  /**
   * @see net.sf.mmm.util.collection.Pool#take()
   */
  public E take() {

    if (this.size > 0) {
      return this.pool[--this.size];
    }
    return null;
  }

}
