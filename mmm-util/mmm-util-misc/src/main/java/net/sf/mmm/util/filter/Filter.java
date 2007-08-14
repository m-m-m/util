/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

/**
 * This is the interface for a filter that {@link #accept(Object) decides} if a
 * given value is acceptable or should be filtered.
 * 
 * @param <V> is the generic type of the value to check.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Filter<V> {

  /**
   * This method determines if the given <code>value</code> should be
   * accepted.
   * 
   * @param value is the value to check.
   * @return <code>true</code> if the given <code>value</code> is
   *         acceptable, <code>false</code> if it should be filtered.
   */
  boolean accept(V value);

  /**
   * A filter instance that {@link #accept(Object) accepts} every object. To be
   * reusable it is NOT generic but may be casted.
   */
  Filter ACCEPT_ALL = new Filter() {

    public boolean accept(Object value) {

      return true;
    }
  };

}
