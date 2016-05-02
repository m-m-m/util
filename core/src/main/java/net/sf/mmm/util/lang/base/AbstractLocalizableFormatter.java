/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.io.IOException;
import java.util.Locale;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.LocalizableFormatter;

/**
 * This is the abstract base implementation of the {@link LocalizableFormatter} interface.
 * 
 * @param <V> is the generic type of the actual value to {@link #format(Object) format}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractLocalizableFormatter<V> extends AbstractFormatter<V> implements LocalizableFormatter<V> {

  /**
   * The constructor.
   */
  public AbstractLocalizableFormatter() {

    super();
  }

  /**
   * This method gets the default locale used if none is provided. Will return {@link Locale#getDefault()}.
   * May be overridden to change.
   * 
   * @return the default locale.
   */
  protected Locale getDefaultLocale() {

    return Locale.getDefault();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String format(V value) {

    return format(value, getDefaultLocale());
  }

  /**
   * {@inheritDoc}
   */
  public String format(V value, Locale locale) {

    StringBuilder buffer = new StringBuilder();
    format(value, buffer, locale);
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void format(V value, Appendable buffer) {

    format(value, buffer, getDefaultLocale());
  }

  /**
   * {@inheritDoc}
   */
  public void format(V value, Appendable buffer, Locale locale) throws RuntimeIoException {

    try {
      if (value == null) {
        buffer.append(formatNull());
      } else {
        doFormat(value, buffer, locale);
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * @see #format(Object, Appendable, Locale)
   * 
   * @param value is the value to format (not <code>null</code>).
   * @param buffer is the {@link Appendable} to {@link Appendable#append(CharSequence) append} the formatted
   *        value to.
   * @param locale is the {@link Locale}.
   * @throws IOException if caused by {@link Appendable#append(CharSequence) append}.
   */
  protected abstract void doFormat(V value, Appendable buffer, Locale locale) throws IOException;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFormat(V value, Appendable buffer) throws IOException {

    doFormat(value, buffer, getDefaultLocale());
  }

}
