/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import javax.annotation.Resource;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.base.MappedNlsFormatterManager;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsFormatterManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsFormatterManagerImpl extends MappedNlsFormatterManager {

  /** The {@link #getFormatter() default formatter}. */
  private NlsFormatter<Object> defaultFormatter;

  /** @see #setIso8601Util(Iso8601Util) */
  private Iso8601Util iso8601Util;

  /**
   * The constructor.
   */
  public NlsFormatterManagerImpl() {

    super();
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
    if (this.defaultFormatter == null) {
      this.defaultFormatter = new NlsFormatterDefault();
    }
    if (getFormatterMap() == null) {
      ConfiguredNlsFormatterMap formatterMap = new ConfiguredNlsFormatterMap();
      formatterMap.initialize();
      setFormatterMap(formatterMap);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NlsFormatter<?> getSubFormatter(String formatType, CharSequenceScanner scanner) {

    if (TYPE_CHOICE.equals(formatType)) {
      return new NlsFormatterChoice(scanner, this, this.iso8601Util);
    } else {
      return super.getSubFormatter(formatType, scanner);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NlsFormatter<Object> getSubFormatter(String formatType, String subformat) {

    if (TYPE_NUMBER.equals(formatType)) {
      return new NlsFormatterNumberPattern(subformat);
    } else if ((TYPE_DATE.equals(formatType)) || (TYPE_TIME.equals(formatType))
        || (TYPE_DATETIME.equals(formatType))) {
      return new NlsFormatterDatePattern(subformat);
    } else {
      return super.getSubFormatter(formatType, subformat);
    }
  }

  /**
   * {@inheritDoc}
   */
  public NlsFormatter<Object> getFormatter() {

    return this.defaultFormatter;
  }

  /**
   * @param defaultFormatter is the defaultFormatter to set
   */
  @Resource
  public void setDefaultFormatter(NlsFormatter<Object> defaultFormatter) {

    getInitializationState().requireNotInitilized();
    this.defaultFormatter = defaultFormatter;
  }

  /**
   * This method sets the {@link Iso8601Util} instance to use.
   * 
   * @param iso8601Util is the {@link Iso8601Util}.
   */
  @Resource
  public void setIso8601Util(Iso8601Util iso8601Util) {

    getInitializationState().requireNotInitilized();
    this.iso8601Util = iso8601Util;
  }

}
