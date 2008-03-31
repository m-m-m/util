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
 * {@link Object} to a {@link String}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueConverterToString extends AbstractValueConverter<Object, String> {

  /**
   * The constructor.
   */
  public ValueConverterToString() {

    super();
  }

  /**
   * This method gets the {@link Iso8601Util} to use.
   * 
   * @return the {@link Iso8601Util} instance.
   */
  protected Iso8601Util getIso8601Util() {

    return Iso8601Util.getInstance();
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
  public Class<String> getTargetType() {

    return String.class;
  }

  /**
   * {@inheritDoc}
   */
  public String convert(Object value, Object valueSource, Class<? extends String> targetClass,
      Type targetType) {

    if (value == null) {
      return null;
    }
    if (value instanceof Class) {
      return ((Class<?>) value).getName();
    } else if (value instanceof Date) {
      return getIso8601Util().formatDateTime((Date) value);
    } else if (value instanceof Calendar) {
      return getIso8601Util().formatDateTime((Calendar) value);
    }
    return value.toString();
  }

}
