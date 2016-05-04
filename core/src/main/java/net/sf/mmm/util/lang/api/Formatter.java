/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This is the interface for a formatter that allows to {@link #format(Object) format} a given object of a generic type.
 * <br>
 * Unlike {@link java.util.Formatter} this variant concentrates on an API rather than an implementation.
 *
 * @param <V> is the generic type of the actual value to {@link #format(Object) format}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface Formatter<V> {

  /**
   * This method formats the given {@code value}. <br>
   * This is a shorthand of the following code:
   *
   * <pre>
   * {@link StringBuilder} buffer = new StringBuilder();
   * {@link #format(Object, Appendable) format}(value, buffer);
   * return buffer.toString();
   * </pre>
   *
   * @param value is the value to format. May be {@code null}.
   * @return the formatted value. If the given {@code value} is {@code null}, a legal implementation will return "null"
   *         or "".
   */
  String format(V value);

  /**
   * This method formats the given {@code value} and directly {@link Appendable#append(CharSequence) appends} to the
   * given {@code buffer}.
   *
   * @param value is the value to format. May be {@code null}.
   * @param buffer is the {@link Appendable} to {@link Appendable#append(CharSequence) append} the formatted value to.
   * @throws RuntimeIoException if {@link Appendable#append(CharSequence) append} caused a {@link java.io.IOException}.
   */
  void format(V value, Appendable buffer) throws RuntimeIoException;

}
