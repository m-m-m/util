/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.io.IOException;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.api.StringUtil;

/**
 * This is the abstract base implementation of the {@link Formatter} interface.
 * 
 * @param <V> is the generic type of the actual value to {@link #format(Object) format}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractFormatter<V> implements Formatter<V> {

  /**
   * The constructor.
   */
  public AbstractFormatter() {

    super();
  }

  @Override
  public String format(V value) {

    StringBuilder buffer = new StringBuilder();
    format(value, buffer);
    return buffer.toString();
  }

  @Override
  public void format(V value, Appendable buffer) throws RuntimeIoException {

    try {
      if (value == null) {
        buffer.append(formatNull());
      } else {
        doFormat(value, buffer);
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * Returns the (static) string representation for the undefined value {@code null}.
   * 
   * @return the null string.
   */
  protected String formatNull() {

    return StringUtil.NULL;
  }

  /**
   * @see #format(Object, Appendable)
   * 
   * @param value is the value to format (not {@code null}).
   * @param buffer is the {@link Appendable} to {@link Appendable#append(CharSequence) append} the formatted
   *        value to.
   * @throws IOException if caused by {@link Appendable#append(CharSequence) append}.
   */
  protected abstract void doFormat(V value, Appendable buffer) throws IOException;

}
