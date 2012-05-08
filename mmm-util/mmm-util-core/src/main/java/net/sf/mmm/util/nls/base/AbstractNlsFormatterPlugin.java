/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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

  //
  // /**
  // * This method
  // * {@link
  // NlsFormatterMap#registerFormatter(net.sf.mmm.util.nls.api.NlsFormatter,
  // String, String)
  // * registers} this formatter to the given {@link NlsFormatterMap}. The
  // default
  // * implementation registers this {@link
  // net.sf.mmm.util.nls.api.NlsFormatter}
  // * by its {@link #getType() type} and {@link #getStyle() style}.<br>
  // * This method may be overridden to allow that a single formatter instance
  // is
  // * registered for multiple types and styles.
  // *
  // * @param formatterMap is the {@link NlsFormatterMap} where to register.
  // */
  // public void register(NlsFormatterMap formatterMap) {
  //
  // formatterMap.registerFormatter(this, getType(), getStyle());
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder(getType());
    String style = getStyle();
    if (style != null) {
      sb.append(",");
      sb.append(style);
    }
    return sb.toString();
  }

}
