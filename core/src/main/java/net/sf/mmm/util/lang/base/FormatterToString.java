/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.io.IOException;

import net.sf.mmm.util.lang.api.StringUtil;

/**
 * This is an implementation of {@link net.sf.mmm.util.lang.api.Formatter} that simply delegates to
 * {@link Object#toString()}.
 * 
 * @param <V> is the generic type of the actual value to {@link #format(Object) format}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class FormatterToString<V> extends AbstractFormatter<V> {

  @SuppressWarnings("rawtypes")
  private static final FormatterToString INSTANCE = new FormatterToString();

  /**
   * The constructor.
   */
  public FormatterToString() {

    super();
  }

  @Override
  protected void doFormat(V value, Appendable buffer) throws IOException {

    if (value == null) {
      buffer.append(StringUtil.NULL);
    } else {
      buffer.append(value.toString());
    }
  }

  /**
   * @param <V> is the generic type of the actual value to {@link #format(Object) format}.
   * @return the (singleton) instance.
   */
  public static <V> FormatterToString<V> getInstance() {

    return INSTANCE;
  }

}
