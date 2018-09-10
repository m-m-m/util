/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import java.io.IOException;

import net.sf.mmm.util.lang.base.AbstractFormatter;
import net.sf.mmm.util.version.api.VersionIdentifier;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.util.lang.api.Formatter} for a numeric part
 * of the {@link VersionIdentifier}.
 *
 * @see ComposedVersionIdentifierFormatter
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractVersionIdentifierFormatterNumber extends AbstractFormatter<VersionIdentifier> {

  /** The static prefix. */
  private final String prefix;

  /** The padding for the number. */
  private final int padding;

  /**
   * The constructor.
   *
   * @param prefix is the static prefix to append before the number. Will be omitted if the number is
   *        {@code null}.
   * @param padding is the padding (minimum number of digits) for the number to format. The default is
   *        {@code 0}.
   */
  public AbstractVersionIdentifierFormatterNumber(String prefix, int padding) {

    super();
    this.prefix = prefix;
    this.padding = padding;
  }

  /**
   * This method gets the {@link Number} to format.
   *
   * @param value is the {@link VersionIdentifier}.
   * @return the {@link Number} to format.
   */
  protected abstract Number getNumber(VersionIdentifier value);

  @Override
  protected void doFormat(VersionIdentifier value, Appendable buffer) throws IOException {

    Number number = getNumber(value);
    if (number != null) {
      if (this.prefix != null) {
        buffer.append(this.prefix);
      }
      buffer.append(padNumber(number.longValue(), this.padding, 10));
    }
  }

  static String padNumber(long number, int digits, int radix) {

    String result = Long.toString(number, radix);
    int leadingZeros = digits - result.length();
    if (leadingZeros > 0) {
      int capacity = result.length() + leadingZeros;
      StringBuilder buffer = new StringBuilder(capacity);
      while (leadingZeros > 0) {
        buffer.append('0');
        leadingZeros--;
      }
      buffer.append(result);
      result = buffer.toString();
    }
    return result;
  }
}
