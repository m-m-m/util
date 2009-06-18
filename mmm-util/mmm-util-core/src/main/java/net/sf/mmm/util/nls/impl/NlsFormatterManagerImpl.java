/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.text.DateFormat;

import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.base.MappedNlsFormatterManager;
import net.sf.mmm.util.nls.base.NlsFormatterMap;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsFormatterManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsFormatterManagerImpl extends MappedNlsFormatterManager {

  // ATTENTION: reordering static final fields breaks code!

  /** The map with the static formatters. */
  private static final NlsFormatterMap FORMATTER_MAP = createFormatterMap();

  /** The singleton instance. */
  public static final NlsFormatterManagerImpl INSTANCE = new NlsFormatterManagerImpl();

  /** The {@link #getFormatter() default formatter}. */
  private final NlsFormatter<Object> defaultFormatter;

  /**
   * The constructor.
   */
  public NlsFormatterManagerImpl() {

    this(NlsFormatterDefault.INSTANCE);
  }

  /**
   * The constructor.
   * 
   * @param defaultFormatter is the {@link #getFormatter() default formatter}.
   */
  public NlsFormatterManagerImpl(NlsFormatter<Object> defaultFormatter) {

    this(defaultFormatter, FORMATTER_MAP);
  }

  /**
   * The constructor.
   * 
   * @param defaultFormatter is the {@link #getFormatter() default formatter}.
   * @param formatterMap is the map with the registered formatters.
   */
  protected NlsFormatterManagerImpl(NlsFormatter<Object> defaultFormatter,
      NlsFormatterMap formatterMap) {

    super(formatterMap);
    this.defaultFormatter = defaultFormatter;
  }

  /**
   * This method creates and initializes the {@link NlsFormatterMap} that
   * contains the static {@link NlsFormatter}s managed by this class.
   * 
   * @return the {@link NlsFormatterMap} instance.
   */
  protected static NlsFormatterMap createFormatterMap() {

    NlsFormatterMap map = new NlsFormatterMap();
    // number format
    map.registerFormatter(NlsFormatterNumber.INSTANCE, TYPE_NUMBER, null);
    map.registerFormatter(NlsFormatterCurrency.INSTANCE, TYPE_NUMBER, STYLE_CURRENCY);
    map.registerFormatter(NlsFormatterInteger.INSTANCE, TYPE_NUMBER, STYLE_INTEGER);
    map.registerFormatter(NlsFormatterPercent.INSTANCE, TYPE_NUMBER, STYLE_PERCENT);
    // date format
    NlsFormatterDate dateShort = new NlsFormatterDate(DateFormat.SHORT);
    map.registerFormatter(dateShort, TYPE_DATE, null);
    map.registerFormatter(dateShort, TYPE_DATE, STYLE_SHORT);
    map.registerFormatter(new NlsFormatterDate(DateFormat.MEDIUM), TYPE_DATE, STYLE_MEDIUM);
    map.registerFormatter(new NlsFormatterDate(DateFormat.LONG), TYPE_DATE, STYLE_LONG);
    map.registerFormatter(new NlsFormatterDate(DateFormat.FULL), TYPE_DATE, STYLE_FULL);
    map.registerFormatter(NlsFormatterDateIso8601.INSTANCE, TYPE_DATE, STYLE_ISO_8601);
    // time format
    NlsFormatterTime timeShort = new NlsFormatterTime(DateFormat.SHORT);
    map.registerFormatter(timeShort, TYPE_TIME, null);
    map.registerFormatter(timeShort, TYPE_TIME, STYLE_SHORT);
    map.registerFormatter(new NlsFormatterTime(DateFormat.MEDIUM), TYPE_TIME, STYLE_MEDIUM);
    map.registerFormatter(new NlsFormatterTime(DateFormat.LONG), TYPE_TIME, STYLE_LONG);
    map.registerFormatter(new NlsFormatterTime(DateFormat.FULL), TYPE_TIME, STYLE_FULL);
    map.registerFormatter(NlsFormatterTimeIso8601.INSTANCE, TYPE_TIME, STYLE_ISO_8601);
    // date-time format
    NlsFormatterDateTime dateTimeShort = new NlsFormatterDateTime(DateFormat.SHORT);
    map.registerFormatter(dateTimeShort, TYPE_DATETIME, null);
    map.registerFormatter(dateTimeShort, TYPE_DATETIME, STYLE_SHORT);
    map.registerFormatter(new NlsFormatterDateTime(DateFormat.MEDIUM), TYPE_DATETIME, STYLE_MEDIUM);
    map.registerFormatter(new NlsFormatterDateTime(DateFormat.LONG), TYPE_DATETIME, STYLE_LONG);
    map.registerFormatter(new NlsFormatterDateTime(DateFormat.FULL), TYPE_DATETIME, STYLE_FULL);
    map.registerFormatter(NlsFormatterDateTimeIso8601.INSTANCE, TYPE_DATETIME, STYLE_ISO_8601);
    return map;
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
  @Override
  public NlsFormatter<Object> getFormatter() {

    return this.defaultFormatter;
  }

}
