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
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} using
 * {@link NlsFormatterManager#TYPE_NUMBER}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public final class NlsFormatterNumber extends SimpleNlsFormatter<Object> {

  /**
   * The constructor.
   */
  public NlsFormatterNumber() {

    super();
  }

  @Override
  protected Formatter<Object> createFormatter(Locale locale) {

    return FormatterProvider.getNumberFormatter(locale);
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_NUMBER;
  }

  @Override
  public String getStyle() {

    return null;
  }

}
