/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.base.SimpleNlsFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} using
 * {@link NumberFormat#getCurrencyInstance(Locale)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public final class NlsFormatterCurrency extends SimpleNlsFormatter<Object> {

  /**
   * The constructor.
   */
  public NlsFormatterCurrency() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Format createFormat(Locale locale) {

    return NumberFormat.getCurrencyInstance(locale);
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

    return NlsFormatterManager.STYLE_CURRENCY;
  }

}
