/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.util.Locale;

import javax.inject.Inject;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.base.SimpleNlsFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} for {@link java.util.Date}s using
 * {@link Iso8601Util}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsFormatterDateIso8601 extends SimpleNlsFormatter<Object> {

  /** @see #createFormatter(Locale) */
  private Iso8601Util iso8601Util;

  /**
   * The constructor.
   */
  public AbstractNlsFormatterDateIso8601() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param iso8601Util is the {@link Iso8601Util} instance to use.
   */
  public AbstractNlsFormatterDateIso8601(Iso8601Util iso8601Util) {

    super();
    NlsNullPointerException.checkNotNull(Iso8601Util.class, iso8601Util);
    this.iso8601Util = iso8601Util;
    initialize();
  }

  /**
   * @param iso8601Util is the iso8601Util to set
   */
  @Inject
  public void setIso8601Util(Iso8601Util iso8601Util) {

    getInitializationState().requireNotInitilized();
    this.iso8601Util = iso8601Util;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.iso8601Util == null) {
      this.iso8601Util = Iso8601UtilImpl.getInstance();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Formatter<Object> createFormatter(Locale locale) {

    return FormatterProvider.getDateFormatter(locale, getType(), this.iso8601Util);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStyle() {

    return NlsFormatterManager.STYLE_ISO_8601;
  }

}
