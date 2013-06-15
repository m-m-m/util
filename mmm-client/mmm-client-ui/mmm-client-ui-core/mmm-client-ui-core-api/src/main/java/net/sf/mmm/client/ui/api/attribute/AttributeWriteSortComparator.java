/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import java.util.Comparator;

/**
 * This interface gives read and write access to the {@link #getSortComparator() sort-comparator} attribute of
 * an object.
 * 
 * @param <VALUE> is the generic type of the values to {@link #getSortComparator() sort}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteSortComparator<VALUE> extends AttributeReadSortComparator<VALUE> {

  /**
   * This method sets the {@link #getSortComparator() sort-comparator} attribute. If {@literal <VALUE>}
   * implements {@link Comparable} you may simple provide
   * {@link net.sf.mmm.util.lang.base.ComparableComparator#getInstance()} as argument (unless a more specific
   * sorting is required here).
   * 
   * @param sortComparator is the new value of {@link #getSortComparator()}. May be <code>null</code> to
   *        disable sorting.
   */
  void setSortComparator(Comparator<VALUE> sortComparator);

}
