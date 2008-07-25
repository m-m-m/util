/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.Calendar;
import java.util.Date;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to a {@link String}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueConverterToString extends AbstractSimpleValueConverter<Object, String> {

  /** @see #getIso8601Util() */
  private final Iso8601Util iso8601Util;

  /** @see #getStringUtil() */
  private final StringUtil stringUtil;

  /**
   * The constructor.
   */
  public ValueConverterToString() {

    this(Iso8601UtilImpl.getInstance(), StringUtilImpl.getInstance());
  }

  /**
   * The constructor.
   * 
   * @param iso8601Util is the {@link Iso8601Util} to use.
   * @param stringUtil is the {@link StringUtilImpl} to use.
   */
  public ValueConverterToString(Iso8601Util iso8601Util, StringUtil stringUtil) {

    super();
    this.iso8601Util = iso8601Util;
    this.stringUtil = stringUtil;
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
   * This method gets the {@link StringUtilImpl} to use.
   * 
   * @return the {@link StringUtilImpl} instance.
   */
  protected StringUtil getStringUtil() {

    return this.stringUtil;
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
  public String convert(Object value, Object valueSource, Class<? extends String> targetClass) {

    if (value == null) {
      return null;
    }
    if (value instanceof Class) {
      return ((Class<?>) value).getName();
    } else if (value instanceof Date) {
      return getIso8601Util().formatDateTime((Date) value);
    } else if (value instanceof Calendar) {
      return getIso8601Util().formatDateTime((Calendar) value);
    } else if (value instanceof Enum) {
      String name = ((Enum<?>) value).name();
      if (name.length() > 1) {
        name = name.charAt(0) + name.substring(1).toLowerCase();
      }
      return this.stringUtil.toCamlCase(name, '_');
    }
    return value.toString();
  }

}
