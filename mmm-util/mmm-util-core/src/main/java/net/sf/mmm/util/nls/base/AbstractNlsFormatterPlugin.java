/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.api.NlsFormatterPlugin;

/**
 * T import net.sf.mmm.util.nls.api.NlsTemplateResolver; his is the abstract base-implementation of an
 * {@link net.sf.mmm.util.nls.api.NlsFormatter} that is no {@link net.sf.mmm.util.nls.api.NlsMessageFormatter}
 * but a {@link net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter(String, String) sub-formatter} for a
 * specific {@link #getType() type} and {@link #getStyle() style}.
 * 
 * @param <O> is the generic type of the object to
 *        {@link #format(Object, java.util.Locale, java.util.Map, net.sf.mmm.util.nls.api.NlsTemplateResolver)
 *        format}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractNlsFormatterPlugin<O> extends AbstractNlsFormatter<O> implements NlsFormatterPlugin<O> {

  /**
   * The constructor.
   */
  public AbstractNlsFormatterPlugin() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(getType());
    String style = getStyle();
    if (style != null) {
      sb.append(",");
      sb.append(style);
    }
    return sb.toString();
  }

}
