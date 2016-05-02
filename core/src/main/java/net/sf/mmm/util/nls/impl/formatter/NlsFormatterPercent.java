/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.base.SimpleNlsFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} for
 * {@link NlsFormatterManager#STYLE_PERCENT}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public final class NlsFormatterPercent extends SimpleNlsFormatter<Object> {

  /**
   * The constructor.
   */
  public NlsFormatterPercent() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Formatter<Object> createFormatter(Locale locale) {

    return FormatterProvider.getPercentFormatter(locale);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_NUMBER;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStyle() {

    return NlsFormatterManager.STYLE_PERCENT;
  }

}
