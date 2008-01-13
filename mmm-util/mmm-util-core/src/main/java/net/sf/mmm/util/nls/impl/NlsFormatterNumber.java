/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import net.sf.mmm.util.nls.base.SimpleNlsFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter}
 * using {@link NumberFormat#getInstance(Locale)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class NlsFormatterNumber extends SimpleNlsFormatter<Object> {

  /** The singleton instance of this {@link Format}. */
  public static final NlsFormatterNumber INSTANCE = new NlsFormatterNumber();

  /**
   * The constructor.
   */
  private NlsFormatterNumber() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Format createFormat(Locale locale) {

    return NumberFormat.getInstance(locale);
  }

}
