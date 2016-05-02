/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.Locale;

import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This is the interface for a {@link Formatter} that is {@link Locale localizable}. Methods inherited from
 * {@link Formatter} will use {@link Locale#getDefault()}.
 * 
 * @param <V> is the generic type of the actual value to {@link #format(Object) format}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface LocalizableFormatter<V> extends Formatter<V> {

  /**
   * This method formats the given {@code value} for the given {@code locale}. <br>
   * This is a shorthand of the following code:
   * 
   * <pre>
   * {@link StringBuilder} buffer = new StringBuilder();
   * {@link #format(Object, Appendable) format}(value, buffer, locale);
   * return buffer.toString();
   * </pre>
   * 
   * @param value is the value to format. May be {@code null}.
   * @param locale is the {@link Locale}.
   * @return the formatted value. If the given {@code value} is {@code null}, a legal implementation
   *         will return "null" or "".
   */
  String format(V value, Locale locale);

  /**
   * This method formats the given {@code value} for the given {@code locale} and directly
   * {@link Appendable#append(CharSequence) appends} to the given {@code buffer}.
   * 
   * @param value is the value to format. May be {@code null}.
   * @param buffer is the {@link Appendable} to {@link Appendable#append(CharSequence) append} the formatted
   *        value to.
   * @param locale is the {@link Locale} to use.
   * @throws RuntimeIoException if {@link Appendable#append(CharSequence) append} caused a
   *         {@link java.io.IOException}.
   */
  void format(V value, Appendable buffer, Locale locale) throws RuntimeIoException;

}
