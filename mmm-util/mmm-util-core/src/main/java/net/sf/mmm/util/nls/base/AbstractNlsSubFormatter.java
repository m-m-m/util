/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

/**
 * T import net.sf.mmm.util.nls.api.NlsTemplateResolver; his is the abstract
 * base-implementation of an {@link net.sf.mmm.util.nls.api.NlsFormatter} that
 * is no {@link net.sf.mmm.util.nls.api.NlsMessageFormatter} but a
 * {@link net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter(String, String)
 * sub-formatter} for a specific {@link #getType() type} and {@link #getStyle()
 * style}.
 * 
 * @param <O> is the generic type of the object to
 *        {@link #format(Object, java.util.Locale, java.util.Map, net.sf.mmm.util.nls.api.NlsTemplateResolver)
 *        format}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public abstract class AbstractNlsSubFormatter<O> extends AbstractNlsFormatter<O> {

  /**
   * The constructor.
   */
  public AbstractNlsSubFormatter() {

    super();
  }

  /**
   * This method
   * {@link NlsFormatterMap#registerFormatter(net.sf.mmm.util.nls.api.NlsFormatter, String, String)
   * registers} this formatter to the given {@link NlsFormatterMap}. The default
   * implementation registers this {@link net.sf.mmm.util.nls.api.NlsFormatter}
   * by its {@link #getType() type} and {@link #getStyle() style}.<br>
   * This method may be overridden to allow that a single formatter instance is
   * registered for multiple types and styles.
   * 
   * @param formatterMap is the {@link NlsFormatterMap} where to register.
   */
  public void register(NlsFormatterMap formatterMap) {

    formatterMap.registerFormatter(this, getType(), getStyle());
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter(String)
   * type} of this formatter. See <code>TYPE_*</code> constants of
   * {@link net.sf.mmm.util.nls.api.NlsFormatterManager} e.g.
   * {@link net.sf.mmm.util.nls.api.NlsFormatterManager#TYPE_NUMBER}.
   * 
   * @return the type or <code>null</code> for the
   *         {@link net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter()
   *         default formatter}. If type is <code>null</code> then also
   *         {@link #getStyle() style} needs to be <code>null</code>.
   */
  protected abstract String getType();

  /**
   * This method gets the
   * {@link net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter(String, String)
   * style} of this formatter. See <code>STYLE_*</code> constants of
   * {@link net.sf.mmm.util.nls.api.NlsFormatterManager} e.g.
   * {@link net.sf.mmm.util.nls.api.NlsFormatterManager#STYLE_LONG}.
   * 
   * @return the style or <code>null</code> for no style.
   */
  protected abstract String getStyle();

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return super.toString();
  }

}
