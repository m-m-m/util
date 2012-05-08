/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.io.IOException;

/**
 * This is an implementation of {@link net.sf.mmm.util.lang.api.Formatter} that always formats to a static
 * string. This may be useful for {@link ComposedFormatter}.
 * 
 * @param <V> is the generic type of the actual value to {@link #format(Object) format}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public class StaticFormatter<V> extends AbstractFormatter<V> {

  /** @see #doFormat(Object, Appendable) */
  private final String string;

  /**
   * The constructor.
   * 
   * @param string is the static {@link String} to use.
   */
  public StaticFormatter(String string) {

    super();
    this.string = string;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFormat(V value, Appendable buffer) throws IOException {

    buffer.append(this.string);
  }

}
