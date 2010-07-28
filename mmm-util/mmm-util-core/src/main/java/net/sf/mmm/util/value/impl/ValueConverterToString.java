/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.inject.Singleton;

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
 * @since 1.0.1
 */
@Singleton
@Named
public class ValueConverterToString extends AbstractSimpleValueConverter<Object, String> {

  /** @see #getIso8601Util() */
  private Iso8601Util iso8601Util;

  /** @see #getStringUtil() */
  private StringUtil stringUtil;

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

    return this.iso8601Util;
  }

  /**
   * This method sets the {@link Iso8601Util} to use.
   * 
   * @param iso8601Util is the {@link Iso8601Util} instance.
   */
  @Resource
  public void setIso8601Util(Iso8601Util iso8601Util) {

    this.iso8601Util = iso8601Util;
  }

  /**
   * This method gets the {@link StringUtil} to use.
   * 
   * @return the {@link StringUtil} instance.
   */
  protected StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * This method sets the {@link StringUtil} to use.
   * 
   * @param stringUtil is the {@link StringUtil} instance.
   */
  @Resource
  public void setStringUtil(StringUtil stringUtil) {

    this.stringUtil = stringUtil;
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
    if (this.stringUtil == null) {
      this.stringUtil = StringUtilImpl.getInstance();
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
    if (value instanceof Class<?>) {
      return ((Class<?>) value).getName();
    } else if (value instanceof Date) {
      return getIso8601Util().formatDateTime((Date) value);
    } else if (value instanceof Calendar) {
      return getIso8601Util().formatDateTime((Calendar) value);
    } else if (value instanceof Enum<?>) {
      String name = ((Enum<?>) value).name();
      if (name.length() > 1) {
        name = name.charAt(0) + name.substring(1).toLowerCase();
      }
      return this.stringUtil.toCamlCase(name, '_');
    }
    return value.toString();
  }

}
