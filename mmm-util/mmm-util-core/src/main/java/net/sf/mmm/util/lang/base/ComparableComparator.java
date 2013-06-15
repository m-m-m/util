/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Comparator;

/**
 * This is the implementation of {@link Comparator} for {@link Comparable} objects. It simply delegates to
 * {@link Comparable#compareTo(Object)}.
 * 
 * @param <T> the type of objects that may be {@link #compare(Comparable, Comparable) compared} by this
 *        {@link Comparator}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ComparableComparator<T extends Comparable<T>> implements Comparator<T> {

  /** @see #getInstance() */
  @SuppressWarnings("rawtypes")
  private static final ComparableComparator INSTANCE = new ComparableComparator();

  /**
   * The constructor.
   */
  public ComparableComparator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compare(T o1, T o2) {

    return 0;
  }

  /**
   * @param <T> the type of objects that may be {@link #compare(Comparable, Comparable) compared} by this
   *        {@link Comparator}.
   * @return the singleton instance of {@link ComparableComparator}.
   */
  public static <T extends Comparable<T>> Comparator<T> getInstance() {

    return INSTANCE;
  }

}
