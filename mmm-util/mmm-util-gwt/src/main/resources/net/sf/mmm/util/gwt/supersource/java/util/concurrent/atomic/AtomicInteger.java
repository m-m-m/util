/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package java.util.concurrent.atomic;

/**
 * This is a very simple variant of {@link java.util.concurrent.atomic.AtomicInteger} to allow access in GWT
 * clients. As a web-client does not yet really supports concurrency this trivial implementation is
 * sufficient. This may change with web-workers. If you ever realize any problems, let us know.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public class AtomicInteger extends Number {

  /** UID for serialization. */
  private static final long serialVersionUID = 6214790243416807050L;

  /** @see #get() */
  private volatile int value;

  /**
   * The constructor.
   */
  public AtomicInteger() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value - see {@link #intValue()}.
   */
  public AtomicInteger(int value) {

    super();
    this.value = value;
  }

  /**
   * @return see {@link java.util.concurrent.atomic.AtomicInteger#get()}.
   */
  public int get() {

    return this.value;
  }

  /**
   * @param newValue - see {@link java.util.concurrent.atomic.AtomicInteger#getAndSet(int)}.
   * @return see {@link java.util.concurrent.atomic.AtomicInteger#getAndSet(int)}.
   */
  public int getAndSet(int newValue) {

    int current = this.value;
    this.value = newValue;
    return current;
  }

  /**
   * @param delta - see {@link java.util.concurrent.atomic.AtomicInteger#getAndAdd(int)}.
   * @return see {@link java.util.concurrent.atomic.AtomicInteger#getAndAdd(int)}.
   */
  public int getAndAdd(int delta) {

    int current = this.value;
    this.value = this.value + delta;
    return current;
  }

  /**
   * @return see {@link java.util.concurrent.atomic.AtomicInteger#decrementAndGet()}.
   */
  public final int decrementAndGet() {

    this.value--;
    return this.value;
  }

  /**
   * @return see {@link java.util.concurrent.atomic.AtomicInteger#incrementAndGet()}.
   */
  public final int incrementAndGet() {

    this.value++;
    return this.value;
  }

  /**
   * @param newValue - see {@link java.util.concurrent.atomic.AtomicInteger#set(int)}.
   */
  public void set(int newValue) {

    this.value = newValue;
  }

  /**
   * @param newValue - see {@link java.util.concurrent.atomic.AtomicInteger#lazySet(int)}.
   */
  public void lazySet(int newValue) {

    this.value = newValue;
  }

  /**
   * @param expect - see {@link java.util.concurrent.atomic.AtomicInteger#compareAndSet(int, int)}.
   * @param update - see {@link java.util.concurrent.atomic.AtomicInteger#compareAndSet(int, int)}.
   * @return see {@link java.util.concurrent.atomic.AtomicInteger#compareAndSet(int, int)}.
   */
  public final boolean compareAndSet(int expect, int update) {

    boolean result = (this.value == expect);
    if (result) {
      this.value = update;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int intValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long longValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public float floatValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double doubleValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return Integer.toString(get());
  }

}
