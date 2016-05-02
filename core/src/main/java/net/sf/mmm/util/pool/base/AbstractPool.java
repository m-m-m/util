/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pool.base;

import net.sf.mmm.util.pool.api.Pool;

/**
 * This is the abstract base implementation of the {@link Pool} interface.
 * 
 * @param <E> is the generic type of the pooled elements.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractPool<E> implements Pool<E> {

  /** The default capacity used. */
  public static final int DEFAULT_CAPACITY = 16;

  /** The buffer representing the pool. */
  private final Object[] pool;

  /** The synchronization lock. */
  private final Object lock;

  /** The actual size (number of elements in {@link #pool}). */
  private int size;

  /**
   * The constructor.
   * 
   * <b>ATTENTION:</b><br>
   * This constructor uses a default capacity of {@value #DEFAULT_CAPACITY}. A suitable default capacity
   * depends on the type of objects that are pooled ({@literal <E>}).
   */
  public AbstractPool() {

    this(false, DEFAULT_CAPACITY, null);
  }

  /**
   * The constructor.
   * 
   * @param capacity is the {@link #getCapacity() capacity}.
   * @param threadsafe if {@code true} the pool needs to be accessed by multiple {@link Thread}s
   *        concurrently, {@code false} otherwise.
   */
  public AbstractPool(int capacity, boolean threadsafe) {

    this(threadsafe, DEFAULT_CAPACITY, null);
  }

  /**
   * The constructor for a thread-safe pool with an externally given {@code lock} for synchronization.
   * 
   * @param capacity is the {@link #getCapacity() capacity}.
   * @param lock is an object used for synchronization.
   */
  public AbstractPool(int capacity, Object lock) {

    this(true, DEFAULT_CAPACITY, lock);
    assert (lock != null);
  }

  /**
   * The constructor.
   * 
   * @param threadsafe if {@code true} the pool needs to be accessed by multiple {@link Thread}s
   *        concurrently, {@code false} otherwise.
   * @param capacity the capacity of this pool.
   * @param lock is an explicit synchronization-lock object. It may be {@code null}. If it is NOT
   *        {@code null}, {@code threadsafe} has to be {@code true}.
   */
  private AbstractPool(boolean threadsafe, int capacity, Object lock) {

    super();
    if (threadsafe) {
      if (lock == null) {
        this.lock = this;
      } else {
        this.lock = lock;
      }
    } else {
      assert (lock == null);
      this.lock = null;
    }
    this.pool = new Object[capacity];
  }

  /**
   * {@inheritDoc}
   */
  public E borrow() {

    if (this.lock == null) {
      return borrowInternal();
    } else {
      synchronized (this.lock) {
        return borrowInternal();
      }
    }
  }

  /**
   * @see #borrow()
   * 
   * @return the borrowed object.
   */
  @SuppressWarnings("unchecked")
  private E borrowInternal() {

    if (this.size > 0) {
      E element = (E) this.pool[this.size];
      this.pool[this.size--] = null;
      return element;
    } else {
      return create();
    }
  }

  /**
   * This method creates a new element. It is used if there is no instance left in the pool.
   * 
   * @return the new instance.
   */
  protected abstract E create();

  /**
   * This method resets the given {@code element} so it can be reused. It is called if an element is
   * {@link #release(Object) released} and will be stored in the pool. The implementation depends on the type
   * of element. Some types may become inconsistent if they are directly reused. Further this method may clear
   * data from the element for security reasons, because the same instance may be given to some other
   * component that is NOT trusted enough (because it may be vulnerably).
   * 
   * @param element the element to reset.
   * @return {@code true} if the given {@code element} can be reused and should be added to the
   *         pool, {@code false} otherwise.
   */
  protected boolean reset(E element) {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEmpty() {

    return this.size == 0;
  }

  /**
   * This method gets the size of the pool.
   * 
   * @return the size
   */
  public int getSize() {

    return this.size;
  }

  /**
   * This method gets the capacity of this pool. This value is the maximum {@link #getSize() size} of this
   * pool. If that size is reached further {@link #release(Object) released} objects are NOT pooled and can be
   * freed by the garbage-collector. <br>
   * <b>ATTENTION:</b><br>
   * The optimal capacity of a {@link Pool} depends heavily on the type of objects that are pooled. For some
   * lightweight objects a very high capacity may suite. However for big buffers (e.g.
   * {@code char[65536]}) the capacity should be very low because otherwise your heap might get crowded.
   * 
   * @return the capacity of the pool.
   */
  public int getCapacity() {

    return this.pool.length;
  }

  /**
   * {@inheritDoc}
   */
  public void release(E element) {

    if (this.size < this.pool.length) {
      boolean reuse = reset(element);
      if (!reuse) {
        return;
      }
    }
    if (this.lock == null) {
      this.pool[this.size++] = element;
    } else {
      synchronized (this.lock) {
        if (this.size < this.pool.length) {
          this.pool[this.size++] = element;
        }
      }
    }
  }

  /**
   * This method clears the complete pool. After the call of this method, the pool will be {@link #isEmpty()
   * empty}.
   */
  public void clear() {

    if (this.lock == null) {
      clearInternal();
    } else {
      synchronized (this.lock) {
        clearInternal();
      }
    }
  }

  /**
   * @see #clear()
   */
  private void clearInternal() {

    while (this.size > 0) {
      this.size--;
      this.pool[this.size] = null;
    }
  }

}
