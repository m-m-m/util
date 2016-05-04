/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.api;

/**
 * This is the interface for a filter that {@link #accept(Object) decides} if a given value is acceptable or should be
 * filtered. <br>
 * See also {@code java.util.function.Predicate}.
 *
 * @param <V> is the generic type of the value to check.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Filter<V> {

  /**
   * This method determines if the given {@code value} should be accepted.
   *
   * @param value is the value to check.
   * @return {@code true} if the given {@code value} is acceptable, {@code false} if it should be filtered.
   */
  boolean accept(V value);

}
