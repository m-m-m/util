/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsFormatterPlugin;
import net.sf.mmm.util.nls.base.AbstractNlsFormatterPlugin;
import net.sf.mmm.util.nls.base.NlsFormatterMap;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterCurrency;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDateFull;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDateIso8601;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDateLong;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDateMedium;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDateShort;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDateTimeFull;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDateTimeIso8601;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDateTimeLong;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDateTimeMedium;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDateTimeShort;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDefault;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterInteger;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterNumber;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterPercent;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterTimeFull;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterTimeIso8601;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterTimeLong;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterTimeMedium;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterTimeShort;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterTypeFull;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterTypeLong;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterTypeMedium;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterTypeShort;

/**
 * This is a sub-class of {@link NlsFormatterMap} as a ready to use configurable
 * component. It contains all the defaults but can also be configured for your
 * custom needs.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named
public class ConfiguredNlsFormatterMap extends NlsFormatterMap {

  /** @see #setFormatters(List) */
  private List<? extends NlsFormatterPlugin<?>> formatters;

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
    if (this.formatters == null) {
      // number format
      List<AbstractNlsFormatterPlugin<?>> formatterList = new ArrayList<AbstractNlsFormatterPlugin<?>>();
      // default format
      formatterList.add(new NlsFormatterDefault());
      // number format
      formatterList.add(new NlsFormatterNumber());
      formatterList.add(new NlsFormatterCurrency());
      formatterList.add(new NlsFormatterInteger());
      formatterList.add(new NlsFormatterPercent());
      // date format
      formatterList.add(new NlsFormatterDateShort());
      formatterList.add(new NlsFormatterDateMedium());
      formatterList.add(new NlsFormatterDateLong());
      formatterList.add(new NlsFormatterDateFull());
      formatterList.add(new NlsFormatterDateIso8601());
      // time format
      formatterList.add(new NlsFormatterTimeShort());
      formatterList.add(new NlsFormatterTimeMedium());
      formatterList.add(new NlsFormatterTimeLong());
      formatterList.add(new NlsFormatterTimeFull());
      formatterList.add(new NlsFormatterTimeIso8601());
      // date-time format
      formatterList.add(new NlsFormatterDateTimeShort());
      formatterList.add(new NlsFormatterDateTimeMedium());
      formatterList.add(new NlsFormatterDateTimeLong());
      formatterList.add(new NlsFormatterDateTimeFull());
      formatterList.add(new NlsFormatterDateTimeIso8601());
      // type format
      formatterList.add(new NlsFormatterTypeShort());
      formatterList.add(new NlsFormatterTypeMedium());
      formatterList.add(new NlsFormatterTypeLong());
      formatterList.add(new NlsFormatterTypeFull());
      for (AbstractNlsFormatterPlugin<?> formatter : formatterList) {
        formatter.initialize();
      }
      this.formatters = formatterList;
    }
    for (NlsFormatterPlugin<?> formatter : this.formatters) {
      registerFormatter(formatter);
    }
  }

  /**
   * This method allows to inject the {@link NlsFormatterPlugin formatters} to
   * {@link #registerFormatter(net.sf.mmm.util.nls.api.NlsFormatter, String, String)
   * register}.
   * 
   * @param formatters is the {@link List} of formatters to set.
   */
  @Inject
  public void setFormatters(List<? extends NlsFormatterPlugin<?>> formatters) {

    getInitializationState().requireNotInitilized();
    this.formatters = formatters;
  }

}
