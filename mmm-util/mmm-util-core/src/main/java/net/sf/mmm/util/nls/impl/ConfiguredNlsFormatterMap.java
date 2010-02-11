/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.nls.base.AbstractNlsSubFormatter;
import net.sf.mmm.util.nls.base.NlsFormatterMap;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class ConfiguredNlsFormatterMap extends NlsFormatterMap {

  /** @see #setFormatters(List) */
  private List<AbstractNlsSubFormatter<?>> formatters;

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
      this.formatters.add(new NlsFormatterDateIso8601());
      // time format
      this.formatters.add(new NlsFormatterTime(DateFormat.SHORT));
      this.formatters.add(new NlsFormatterTime(DateFormat.MEDIUM));
      this.formatters.add(new NlsFormatterTime(DateFormat.LONG));
      this.formatters.add(new NlsFormatterTime(DateFormat.FULL));
      this.formatters.add(new NlsFormatterTimeIso8601());
      // date-time format
      this.formatters.add(new NlsFormatterDateTime(DateFormat.SHORT));
      this.formatters.add(new NlsFormatterDateTime(DateFormat.MEDIUM));
      this.formatters.add(new NlsFormatterDateTime(DateFormat.LONG));
      this.formatters.add(new NlsFormatterDateTime(DateFormat.FULL));
      this.formatters.add(new NlsFormatterDateTimeIso8601());
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

}
