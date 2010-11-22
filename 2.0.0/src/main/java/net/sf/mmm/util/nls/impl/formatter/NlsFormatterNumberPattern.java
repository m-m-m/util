/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.base.SimpleNlsFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter}
 * using {@link DecimalFormat}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class NlsFormatterNumberPattern extends SimpleNlsFormatter<Object> {

  /** @see #createFormat(Locale) */
  private final String pattern;

  /**
   * The constructor.
   * 
   * @param pattern is the pattern for the {@link DecimalFormat}.
   */
  public NlsFormatterNumberPattern(String pattern) {

    super();
    this.pattern = pattern;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Format createFormat(Locale locale) {

    // only available since java 6
    // DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale)
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
    return new DecimalFormat(this.pattern, symbols);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return NlsFormatterManager.TYPE_NUMBER;
  }

  /**
   * {@inheritDoc}
   */
  public String getStyle() {

    return this.pattern;
  }

}
