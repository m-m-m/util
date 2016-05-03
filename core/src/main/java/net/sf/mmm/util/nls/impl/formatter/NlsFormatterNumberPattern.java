/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.util.Locale;

import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.base.SimpleNlsFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} for
 * {@link NlsFormatterManager#TYPE_NUMBER} using a custom pattern.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class NlsFormatterNumberPattern extends SimpleNlsFormatter<Object> {

  /** @see #createFormatter(Locale) */
  private final String pattern;

  /**
   * The constructor.
   * 
   * @param pattern is the custom number pattern.
   */
  public NlsFormatterNumberPattern(String pattern) {

    super();
    this.pattern = pattern;
  }

  @Override
  protected Formatter<Object> createFormatter(Locale locale) {

    return FormatterProvider.getNumberFormatter(locale, this.pattern);
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_NUMBER;
  }

  @Override
  public String getStyle() {

    return this.pattern;
  }

}
