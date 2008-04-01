/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

import net.sf.mmm.util.date.Iso8601Util;
import net.sf.mmm.util.value.base.AbstractValueConverter;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to a {@link Calendar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueConverterToCalendar extends AbstractValueConverter<Object, Calendar> {

  /** @see #getIso8601Util() */
  private final Iso8601Util iso8601Util;

  /**
   * The constructor.
   */
  public ValueConverterToCalendar() {

    super();
    this.iso8601Util = Iso8601Util.getInstance();
  }

  /**
   * The constructor.
   * 
   * @param iso8601Util is the {@link Iso8601Util} to use.
   */
  public ValueConverterToCalendar(Iso8601Util iso8601Util) {

    super();
    this.iso8601Util = iso8601Util;
  }

  /**
   * This method gets the {@link Iso8601Util} to use.
   * 
   * @return the {@link Iso8601Util} instance.
   */
  protected Iso8601Util getIso8601Util() {

    return this.iso8601Util;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Object> getSourceType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Calendar> getTargetType() {

    return Calendar.class;
  }

  /**
   * {@inheritDoc}
   */
  public Calendar convert(Object value, Object valueSource, Class<? extends Calendar> targetClass,
      Type targetType) {

    if (value == null) {
      return null;
    }
    if (value instanceof Date) {
      // TODO
      Calendar calendar = Calendar.getInstance();
      calendar.setTime((Date) value);
      return calendar;
    }
    if (value instanceof String) {
      return getIso8601Util().parseCalendar((String) value);
    }
    if (value instanceof Long) {
      // TODO
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(((Long) value).longValue());
      return calendar;
    }
    return null;
  }
}
