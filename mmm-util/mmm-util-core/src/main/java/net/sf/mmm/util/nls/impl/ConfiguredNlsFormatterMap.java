/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.base.AbstractNlsSubFormatter;
import net.sf.mmm.util.nls.base.NlsFormatterMap;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This is a sub-class of {@link NlsFormatterMap} as a ready to use configurable
 * component. It contains all the defaults but can also be configured for your
 * custom needs.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class ConfiguredNlsFormatterMap extends NlsFormatterMap {

  /** @see #setFormatters(List) */
  private List<AbstractNlsSubFormatter<?>> formatters;

  /** @see #setIso8601Util(Iso8601Util) */
  private Iso8601Util iso8601Util;

  /** @see #setReflectionUtil(ReflectionUtil) */
  private ReflectionUtil reflectionUtil;

  /**
   * The constructor.
   */
  public ConfiguredNlsFormatterMap() {

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
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
    if (this.formatters == null) {
      // number format
      this.formatters = new ArrayList<AbstractNlsSubFormatter<?>>();
      this.formatters.add(new NlsFormatterNumber());
      this.formatters.add(new NlsFormatterCurrency());
      this.formatters.add(new NlsFormatterInteger());
      this.formatters.add(new NlsFormatterPercent());
      // date format
      this.formatters.add(new NlsFormatterDate(DateFormat.SHORT));
      this.formatters.add(new NlsFormatterDate(DateFormat.MEDIUM));
      this.formatters.add(new NlsFormatterDate(DateFormat.LONG));
      this.formatters.add(new NlsFormatterDate(DateFormat.FULL));
      this.formatters.add(new NlsFormatterDateIso8601(this.iso8601Util));
      // time format
      this.formatters.add(new NlsFormatterTime(DateFormat.SHORT));
      this.formatters.add(new NlsFormatterTime(DateFormat.MEDIUM));
      this.formatters.add(new NlsFormatterTime(DateFormat.LONG));
      this.formatters.add(new NlsFormatterTime(DateFormat.FULL));
      this.formatters.add(new NlsFormatterTimeIso8601(this.iso8601Util));
      // date-time format
      this.formatters.add(new NlsFormatterDateTime(DateFormat.SHORT));
      this.formatters.add(new NlsFormatterDateTime(DateFormat.MEDIUM));
      this.formatters.add(new NlsFormatterDateTime(DateFormat.LONG));
      this.formatters.add(new NlsFormatterDateTime(DateFormat.FULL));
      this.formatters.add(new NlsFormatterDateTimeIso8601(this.iso8601Util));
      // type format
      this.formatters
          .add(new NlsFormatterType(NlsFormatterManager.STYLE_SHORT, this.reflectionUtil));
      this.formatters.add(new NlsFormatterType(NlsFormatterManager.STYLE_MEDIUM,
          this.reflectionUtil));
      this.formatters
          .add(new NlsFormatterType(NlsFormatterManager.STYLE_LONG, this.reflectionUtil));
      this.formatters
          .add(new NlsFormatterType(NlsFormatterManager.STYLE_FULL, this.reflectionUtil));
    }
    for (AbstractNlsSubFormatter<?> formatter : this.formatters) {
      formatter.register(this);
    }
  }

  /**
   * This method allows to inject the {@link AbstractNlsSubFormatter formatters}
   * to
   * {@link #registerFormatter(net.sf.mmm.util.nls.api.NlsFormatter, String, String)
   * register}.
   * 
   * @param formatters is the {@link List} of formatters to set.
   */
  public void setFormatters(List<AbstractNlsSubFormatter<?>> formatters) {

    this.formatters = formatters;
  }

  /**
   * @param iso8601Util is the iso8601Util to set
   */
  @Resource
  public void setIso8601Util(Iso8601Util iso8601Util) {

    getInitializationState().requireNotInitilized();
    this.iso8601Util = iso8601Util;
  }

  /**
   * @param reflectionUtil is the reflectionUtil to set
   */
  @Resource
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

}
