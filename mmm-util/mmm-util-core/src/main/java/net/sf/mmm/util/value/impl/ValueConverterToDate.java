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
 * an {@link Object} to a {@link Date}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
public class ValueConverterToDate extends AbstractSimpleValueConverter<Object, Date> {

  /** @see #getIso8601Util() */
  private Iso8601Util iso8601Util;

  /**
   * The constructor.
   */
  public ValueConverterToDate() {

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
  public Class<Object> getSourceType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Date> getTargetType() {

    return Date.class;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <T extends Date> T convert(Object value, Object valueSource, Class<T> targetClass) {

    if ((value == null) || (targetClass != Date.class)) {
      return null;
    }
    Date result = null;
    if (value instanceof Calendar) {
      result = ((Calendar) value).getTime();
    } else if (value instanceof String) {
      result = getIso8601Util().parseDate((String) value);
    } else if (value instanceof Long) {
      result = new Date(((Long) value).longValue());
    }
    return (T) result;
  }
}
