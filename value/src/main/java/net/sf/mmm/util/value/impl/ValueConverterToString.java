/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts
 * an {@link Object} to a {@link String}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
public class ValueConverterToString extends AbstractSimpleValueConverter<Object, String> {

  private Iso8601Util iso8601Util;

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
  @Inject
  public void setIso8601Util(Iso8601Util iso8601Util) {

    this.iso8601Util = iso8601Util;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.iso8601Util == null) {
      this.iso8601Util = Iso8601UtilImpl.getInstance();
    }
  }

  @Override
  public Class<Object> getSourceType() {

    return Object.class;
  }

  @Override
  public Class<String> getTargetType() {

    return String.class;
  }

  @Override
  @SuppressWarnings("all")
  public <T extends String> T convert(Object value, Object valueSource, Class<T> targetClass) {

    if (value == null) {
      return null;
    }
    String result;
    if (value instanceof Class<?>) {
      result = ((Class<?>) value).getName();
    } else if (value instanceof Date) {
      result = getIso8601Util().formatDateTime((Date) value);
    } else if (value instanceof Calendar) {
      result = getIso8601Util().formatDateTime((Calendar) value);
    } else if (value instanceof Enum<?>) {
      String name = ((Enum<?>) value).name();
      result = name.replace('_', '-').toLowerCase(Locale.US);
    } else {
      result = value.toString();
    }
    return (T) result;
  }

}
