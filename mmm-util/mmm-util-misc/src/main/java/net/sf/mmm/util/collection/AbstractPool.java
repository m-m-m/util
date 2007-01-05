/* $Id$ */
package net.sf.mmm.util.collection;

/**
 * This is the abstract base implementation of the {@link Pool} interface.
 * 
 * @param <E>
 *        is the templated type of the elements in the pool.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPool<E> implements Pool<E> {

  /**
   * The constructor.
   */
  public AbstractPool() {

    super();
  }

  /**
   * @see net.sf.mmm.util.collection.Pool#isEmpty()
   */
  public boolean isEmpty() {

    return (size() == 0);
  }

}
