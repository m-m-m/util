/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import java.lang.reflect.Type;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.value.api.GenericValueConverter;
import net.sf.mmm.util.value.api.ValueNotSetException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;
import net.sf.mmm.util.value.api.WrongValueTypeException;

/**
 * This is the abstract base implementation of the {@link GenericValueConverter} interface.
 *
 * @param <SOURCE> is the generic type of the values to convert.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public abstract class AbstractGenericValueConverter<SOURCE> extends AbstractLoggableComponent
    implements GenericValueConverter<SOURCE> {

  /**
   * The constructor.
   */
  public AbstractGenericValueConverter() {

    super();
  }

  @Override
  public <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass)
      throws ValueNotSetException, WrongValueTypeException {

    return convertValue(value, valueSource, targetClass, targetClass);
  }

  @Override
  public final <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> type, Type targetType,
      TARGET defaultValue) throws WrongValueTypeException {

    if (value == null) {
      return defaultValue;
    } else {
      return convertValue(value, valueSource, type, type);
    }
  }

  @Override
  public <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass,
      TARGET defaultValue) throws WrongValueTypeException {

    return convertValue(value, valueSource, targetClass, targetClass, defaultValue);
  }

  @Override
  public <TARGET extends Number> TARGET convertValue(SOURCE value, Object valueSource, TARGET minimum,
      TARGET maximum, TARGET defaultValue) throws WrongValueTypeException, ValueOutOfRangeException {

    if (defaultValue != null) {
      ValueOutOfRangeException.checkRange((Object) defaultValue, minimum, maximum, valueSource);
    }
    if (value == null) {
      return defaultValue;
    } else {
      return convertValue(value, valueSource, minimum, maximum);
    }
  }

  @Override
  public <TARGET extends Number> TARGET convertValue(SOURCE value, Object valueSource, TARGET minimum,
      TARGET maximum) throws ValueNotSetException, WrongValueTypeException, ValueOutOfRangeException {

    Class<? extends Number> targetClass = minimum.getClass();
    @SuppressWarnings("unchecked")
    TARGET result = (TARGET) convertValue(value, valueSource, targetClass, targetClass);
    ValueOutOfRangeException.checkRange((Object) result, minimum, maximum, valueSource);
    return result;
  }

}
