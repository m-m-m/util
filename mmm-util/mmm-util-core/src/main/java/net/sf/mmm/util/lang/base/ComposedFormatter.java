/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.io.IOException;

import net.sf.mmm.util.lang.api.Formatter;

/**
 * This is the implementation of a {@link Formatter} that is composed out of other {@link Formatter}s. It
 * implements the divide and conquer strategy by delegating to a given sequence of sub-{@link Formatter
 * formatters} in a given order. Each sub-formatter appends a particular part to the result according to his
 * responsibility.
 *
 * @param <V> is the generic type of the actual value to {@link #format(Object) format}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ComposedFormatter<V> extends AbstractFormatter<V> {

  /** @see #doFormat(Object, Appendable) */
  private final Formatter<V>[] subFormatters;

  /**
   * The constructor.
   *
   * @param subFormatters are the {@link Formatter}s to delegate to in the given order.
   */
  @SafeVarargs
  public ComposedFormatter(Formatter<V>... subFormatters) {

    super();
    this.subFormatters = subFormatters;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFormat(V value, Appendable buffer) throws IOException {

    for (Formatter<V> f : this.subFormatters) {
      f.format(value, buffer);
    }
  }

}
