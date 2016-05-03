/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.util.Locale;

import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.nls.base.SimpleNlsFormatter;

/**
 * This is the abstract base implementation for a {@link SimpleNlsFormatter} for {@link java.util.Date}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractSimpleNlsFormatterDate extends SimpleNlsFormatter<Object> {

  /**
   * The constructor.
   */
  public AbstractSimpleNlsFormatterDate() {

    super();
  }

  @Override
  protected Formatter<Object> createFormatter(Locale locale) {

    return FormatterProvider.getDateFormatter(locale, getType(), getStyle());
  }

}
