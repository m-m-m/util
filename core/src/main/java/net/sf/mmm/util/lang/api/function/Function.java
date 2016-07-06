/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.function;

/**
 * This is the back-port for the Java 1.8+ interface {@code java.util.function.Supplier}.
 *
 * @see net.sf.mmm.util.lang.api.function
 *
 * @param <T> is the generic type of the argument to {@link #apply(Object)}.
 * @param <R> is the generic type of the result of {@link #apply(Object)}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 * @deprecated use {@link java.util.function.Function} directly.
 */
@Deprecated
public interface Function<T, R> extends java.util.function.Function<T, R> {

  /**
   * Applies this function to the given argument.
   *
   * @param t the function argument
   * @return the function result
   */
  R apply(T t);
}
