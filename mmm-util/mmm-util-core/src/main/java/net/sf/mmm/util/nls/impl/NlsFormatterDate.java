/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.text.DateFormat;
import java.text.Format;
import java.util.Locale;

import net.sf.mmm.util.nls.base.SimpleNlsFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter}
 * using {@link DateFormat#getDateInstance(int, Locale)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class NlsFormatterDate extends SimpleNlsFormatter<Object> {

  /** @see #createFormat(Locale) */
  private final int style;

  /**
   * The constructor.
   * 
   * @param style is the style used for formatting dates (e.g.
   *        {@link DateFormat#SHORT}).
   */
  public NlsFormatterDate(int style) {

    super();
    this.style = style;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Format createFormat(Locale locale) {

    return DateFormat.getDateInstance(this.style, locale);
  }

}
