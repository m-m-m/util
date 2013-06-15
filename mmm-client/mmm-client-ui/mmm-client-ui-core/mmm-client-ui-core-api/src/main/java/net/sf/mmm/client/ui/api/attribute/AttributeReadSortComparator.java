/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import java.util.Comparator;

/**
 * This interface gives read access to the {@link #getSortComparator() sort-comparator} attribute of an
 * object.
 * 
 * @see AttributeWriteSortComparator
 * 
 * @param <VALUE> is the generic type of the values to {@link #getSortComparator() sort}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadSortComparator<VALUE> {

  /**
   * This method gets the {@link Comparator} used to {@link Comparator#compare(Object, Object) compare} and
   * sort the values.
   * 
   * @return the {@link Comparator} used to sort the values. May be <code>null</code> if NOT set what will
   *         prevent sorting.
   */
  Comparator<VALUE> getSortComparator();

}
