/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import java.lang.reflect.Type;

import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.value.api.GenericValueConverter;
import net.sf.mmm.util.value.api.ValueNotSetException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;
import net.sf.mmm.util.value.api.WrongValueTypeException;

/**
 * This is the abstract base implementation of the {@link GenericValueConverter}
 * interface.
 * 
 * @param <SOURCE> is the generic type of the values to convert.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractGenericValueConverter<SOURCE> extends AbstractLoggable implements
    GenericValueConverter<SOURCE> {

  /**
   * The constructor.
   */
  public AbstractGenericValueConverter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass)
      throws ValueNotSetException, WrongValueTypeException {

    return convertValue(value, valueSource, targetClass, targetClass);
  }

  /**
   * {@inheritDoc}
   */
  public final <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> type,
      Type targetType, TARGET defaultValue) throws WrongValueTypeException {

    if (value == null) {
      return defaultValue;
    } else {
      return convertValue(value, valueSource, type, type);
    }
  }

  /**
   * {@inheritDoc}
   */
  public <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass,
      TARGET defaultValue) throws WrongValueTypeException {

    return convertValue(value, valueSource, targetClass, targetClass, defaultValue);
  }

  /**
   * {@inheritDoc}
   */
  public <TARGET extends Number> TARGET convertValue(SOURCE value, Object valueSource,
      TARGET minimum, TARGET maximum, TARGET defaultValue) throws WrongValueTypeException,
      ValueOutOfRangeException {

    if (defaultValue != null) {
      checkRange(defaultValue, valueSource, minimum, maximum);
    }
    if (value == null) {
      return defaultValue;
    } else {
      return convertValue(value, valueSource, minimum, maximum);
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <TARGET extends Number> TARGET convertValue(SOURCE value, Object valueSource,
      TARGET minimum, TARGET maximum) throws ValueNotSetException, WrongValueTypeException,
      ValueOutOfRangeException {

    Class<? extends Number> targetClass = minimum.getClass();
    TARGET result = (TARGET) convertValue(value, valueSource, targetClass, targetClass);
    checkRange(result, valueSource, minimum, maximum);
    return result;
  }

  /**
   * This method checks that the given <code>value</code> is in the inclusive
   * range from <code>minimum</code> to <code>maximum</code>.
   * 
   * @param value is the value to check.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param minimum is the minimum number allowed.
   * @param maximum is the maximum number allowed.
   */
  private void checkRange(Number value, Object valueSource, Number minimum, Number maximum) {

    double d = value.doubleValue();
    if ((d < minimum.doubleValue()) || (d > maximum.doubleValue())) {
      throw new ValueOutOfRangeException(value, minimum, maximum, valueSource);
    }
  }

}
