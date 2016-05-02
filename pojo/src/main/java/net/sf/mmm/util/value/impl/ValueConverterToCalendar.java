/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts
 * an {@link Object} to a {@link Calendar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
public class ValueConverterToCalendar extends AbstractSimpleValueConverter<Object, Calendar> {

  /** @see #getIso8601Util() */
  private Iso8601Util iso8601Util;

  /**
   * The constructor.
   */
  public ValueConverterToCalendar() {

    super();
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
   * This method sets the {@link Iso8601Util} to use.
   * 
   * @param iso8601Util is the {@link Iso8601Util} to use.
   */
  @Inject
  public void setIso8601Util(Iso8601Util iso8601Util) {

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
  @SuppressWarnings("all")
  public <T extends Calendar> T convert(Object value, Object valueSource, Class<T> targetClass) {

    if ((value == null) || (targetClass != Calendar.class)) {
      return null;
    }
    Calendar calendar = null;
    if (value instanceof Date) {
      calendar = Calendar.getInstance();
      calendar.setTime((Date) value);
    } else if (value instanceof String) {
      calendar = getIso8601Util().parseCalendar((String) value);
    } else if (value instanceof Long) {
      calendar = Calendar.getInstance();
      calendar.setTimeInMillis(((Long) value).longValue());
    }
    return (T) calendar;
  }
}
