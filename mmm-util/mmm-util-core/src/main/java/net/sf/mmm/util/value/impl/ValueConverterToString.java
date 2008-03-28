/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.lang.reflect.Type;

import net.sf.mmm.util.value.api.ValueConverter;

/**
 * This is an implementation of the {@link ValueConverter} interface that
 * converts an {@link Object} to a {@link String}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueConverterToString implements ValueConverter<Object, String> {

  /**
   * The constructor.
   */
  public ValueConverterToString() {

    super();
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
    }
    return value.toString();
  }

}
