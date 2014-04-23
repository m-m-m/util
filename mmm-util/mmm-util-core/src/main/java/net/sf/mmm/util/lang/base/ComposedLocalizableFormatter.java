/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.io.IOException;
import java.util.Locale;

import net.sf.mmm.util.lang.api.LocalizableFormatter;

/**
 * This is the implementation of a {@link LocalizableFormatter} that is composed out of other
 * {@link LocalizableFormatter}s. See {@link ComposedFormatter} for further details.
 *
 * @param <V> is the generic type of the actual value to {@link #format(Object) format}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ComposedLocalizableFormatter<V> extends AbstractLocalizableFormatter<V> {

  /** @see #doFormat(Object, Appendable) */
  private final LocalizableFormatter<V>[] subFormatters;

  /**
   * The constructor.
   *
   * @param subFormatters are the {@link LocalizableFormatter}s to delegate to in the given order.
   */
  @SafeVarargs
  public ComposedLocalizableFormatter(LocalizableFormatter<V>... subFormatters) {

    super();
    this.subFormatters = subFormatters;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFormat(V value, Appendable buffer, Locale locale) throws IOException {

    for (LocalizableFormatter<V> f : this.subFormatters) {
      f.format(value, buffer, locale);
    }
  }

}
