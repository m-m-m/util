/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * POJO to a POJO with the same properties. E.g. this can be useful when you
 * have generated transport-objects (maybe from some strange
 * web-service-framework) and want to convert those from or to your nice
 * equivalent handwritten POJOs.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueConverterToCompatiblePojo extends AbstractSimpleValueConverter<Object, Object> {

  /**
   * The constructor.
   */
  public ValueConverterToCompatiblePojo() {

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
  public Class<Object> getTargetType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public Object convert(Object value, Object valueSource, Class<?> targetClass) {

    if (value == null) {
      return null;
    }
    Class<?> valueClass = value.getClass();
    if (valueClass.isEnum()) {
      if (targetClass.isEnum()) {
        Enum<?> enumValue = (Enum<?>) value;
        return Enum.valueOf((Class<? extends Enum>) targetClass, enumValue.name());
      }
    } else if (valueClass.isArray()) {
      if (targetClass.isArray()) {

      } else {

      }
    }
    return null;
  }
}
