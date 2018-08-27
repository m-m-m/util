/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.function;

/**
 * This is the back-port for the Java 1.8+ interface {@code java.util.function.Consumer}.
 *
 * @see net.sf.mmm.util.lang.api.function
 *
 * @param <T> is the generic type of the input object to {@link #accept(Object) accept}.
 *
 * @deprecated use {{@code java.util.function.Consumer}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@Deprecated
public interface Consumer<T/* > extends java.util.function.Consumer<T */> {

  /**
   * Accept an input value.
   *
   * @param value the input object to consume.
   */
  void accept(T value);

}
