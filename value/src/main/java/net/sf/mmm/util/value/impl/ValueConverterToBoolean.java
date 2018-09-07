/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts
 * an {@link Object} to a {@link Boolean}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
public class ValueConverterToBoolean extends AbstractSimpleValueConverter<Object, Boolean> {

  /**
   * The constructor.
   */
  public ValueConverterToBoolean() {

    super();
  }

  @Override
  public Class<Object> getSourceType() {

    return Object.class;
  }

  @Override
  public Class<Boolean> getTargetType() {

    return Boolean.class;
  }

  @Override
  @SuppressWarnings("all")
  public <T extends Boolean> T convert(Object value, Object valueSource, Class<T> targetClass) {

    if (value == null) {
      return null;
    }
    String valueAsString = value.toString();
    return (T) Boolean.valueOf(valueAsString);
  }

}
