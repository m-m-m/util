/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.util.Locale;

import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.base.SimpleNlsFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} using a custom date pattern
 * (typically using {@link java.text.SimpleDateFormat}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class NlsFormatterDatePattern extends SimpleNlsFormatter<Object> {

  /** @see #createFormatter(Locale) */
  private final String pattern;

  /**
   * The constructor.
   * 
   * @param pattern is the pattern for the {@link FormatterProvider#getDateFormatter(Locale, String) date
   *        formatter}.
   */
  public NlsFormatterDatePattern(String pattern) {

    super();
    this.pattern = pattern;
    // trigger pattern validation if assertions are one
    // assert (new SimpleDateFormat(this.pattern, Locale.ROOT) != null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Formatter<Object> createFormatter(Locale locale) {

    return FormatterProvider.getDateFormatter(locale, this.pattern);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_DATE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStyle() {

    return this.pattern;
  }

}
