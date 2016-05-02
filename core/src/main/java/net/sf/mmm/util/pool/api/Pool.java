/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pool.api;

/**
 * This is the interface for a simple pool. It allows to {@link #borrow() borrow} an object from the pool. If
 * that object is NOT needed anymore, it should be {@link #release(Object) released}. <br>
 * A typical pool implementation will have an internal buffer to cache {@link #release(Object) released}
 * objects so they can be reused for further {@link #borrow() requests}. Such buffer should be limited to a
 * maximum size (capacity). While that size is reached, {@link #release(Object) released} objects will NOT be
 * cached anymore (and the garbage collector should free them). <br>
 * <b>ATTENTION:</b><br>
 * Do NOT use this interface for a {@link Thread}-pool. There is already {@link java.util.concurrent.Executor}
 * for this use-case (see {@link java.util.concurrent.Executors}).
 * 
 * @param <E> is the templated type of the elements in the pool.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Pool<E> {

  /**
   * This method borrows an element from this pool. If you do NOT need the object anymore you should
   * {@link #release(Object) release} it. <br>
   * If this pool is {@link #isEmpty() empty}, a new instance is created for you. Otherwise a existing
   * instance (that has been {@link #release(Object) released} before) will be returned so it can be reused.
   * 
   * @return an element from the pool. Typically this method never returns {@code null}. This may only
   *         happen if explicitly documented by the chosen implementation.
   */
  E borrow();

  /**
   * This method releases the given {@code element}. It will be put back into the pool. <br>
   * <b>ATTENTION:</b><br>
   * Only call this method if you are sure that the given {@code element} is NOT in use anymore.
   * Therefore no reference should exist on the {@code element} and you should NOT have passed the
   * {@code element} to a third-party library that may keep it in some cache.
   * 
   * @see java.util.Collection#add(Object)
   * 
   * @param element is the element to add to the pool.
   */
  void release(E element);

  /**
   * This method determines if the pool is empty.
   * 
   * @see java.util.Collection#isEmpty()
   * 
   * @return {@code true} if the pool is empty.
   */
  boolean isEmpty();

}
