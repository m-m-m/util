/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.text.DateFormat;
import java.text.Format;
import java.util.Locale;

import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.base.NlsFormatterMap;
import net.sf.mmm.util.nls.base.SimpleNlsFormatter;

/**
 * This is the abstract base implementation for a {@link SimpleNlsFormatter}
 * using {@link DateFormat}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public abstract class AbstractSimpleNlsFormatterDate extends SimpleNlsFormatter<Object> {

  /** @see #createFormat(Locale) */
  private final int style;

  /**
   * The constructor.
   * 
   * @param style is the style used for formatting dates (e.g.
   *        {@link DateFormat#SHORT}).
   */
  public AbstractSimpleNlsFormatterDate(int style) {

    super();
    this.style = style;
  }

  /**
   * This method converts the given {@link DateFormat}-style constant (e.g.
   * {@link DateFormat#LONG}) to the according {@link #getStyle() NLS-style
   * name}.
   * 
   * @param style is the {@link DateFormat}-style constant (e.g.
   *        {@link DateFormat#MEDIUM}).
   * @return the according {@link #getStyle() NLS-style name} or
   *         <code>null</code> if unknown.
   */
  static String convertStyle(int style) {

    if (style == DateFormat.SHORT) {
      return NlsFormatterManager.STYLE_SHORT;
    } else if (style == DateFormat.LONG) {
      return NlsFormatterManager.STYLE_LONG;
    } else if (style == DateFormat.MEDIUM) {
      return NlsFormatterManager.STYLE_MEDIUM;
    } else if (style == DateFormat.FULL) {
      return NlsFormatterManager.STYLE_FULL;
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Format createFormat(Locale locale) {

    String type = getType();
    if (NlsFormatterManager.TYPE_DATE.equals(type)) {
      return DateFormat.getDateInstance(this.style, locale);
    } else if (NlsFormatterManager.TYPE_TIME.equals(type)) {
      return DateFormat.getTimeInstance(this.style, locale);
    } else if (NlsFormatterManager.TYPE_DATETIME.equals(type)) {
      return DateFormat.getDateTimeInstance(this.style, this.style, locale);
    } else {
      throw new IllegalCaseException(type);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getStyle() {

    return convertStyle(this.style);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void register(NlsFormatterMap formatterMap) {

    super.register(formatterMap);
    if (this.style == DateFormat.MEDIUM) {
      formatterMap.registerFormatter(this, getType(), null);
    }
  }

}
