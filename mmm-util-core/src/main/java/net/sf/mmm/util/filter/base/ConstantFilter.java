/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.base;

import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.filter.api.Filter;

/**
 * This is an implementation of {@link Filter} that either accepts or rejects all objects to filter.
 *
 * @param <V> is the generic type of the filtered objects.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public final class ConstantFilter<V> implements Filter<V> {

  /** @see #getInstance(boolean) */
  @SuppressWarnings("rawtypes")
  private static final ConstantFilter ACCEPT_ALL_FILTER = new ConstantFilter<>();

  /** @see #getInstance(boolean) */
  @SuppressWarnings("rawtypes")
  private static final ConstantFilter REJECT_ALL_FILTER = new ConstantFilter<>();

  /**
   * The constructor.
   */
  private ConstantFilter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public boolean accept(V value) {

    if (this == ACCEPT_ALL_FILTER) {
      return true;
    } else if (this == REJECT_ALL_FILTER) {
      return false;
    } else {
      throw new NlsIllegalStateException();
    }
  }

  /**
   * This method gets the an instance of this class.
   *
   * @param <V> is the generic type of the filtered object.
   * @param accept - <code>true</code> if the returned {@link Filter} should accept all objects, <code>false</code> to
   *        reject all objects.
   * @return the requested filter.
   */
  public static <V> Filter<V> getInstance(boolean accept) {

    if (accept) {
      return ACCEPT_ALL_FILTER;
    } else {
      return REJECT_ALL_FILTER;
    }
  }

}
