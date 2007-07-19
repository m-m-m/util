/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value;

import java.util.Date;

import net.sf.mmm.util.Iso8601Util;
import net.sf.mmm.util.NumericUtil;
import net.sf.mmm.util.StringUtil;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class ValueUtil {

  /** TODO: i18n */
  private static final String UNKNOWN_SOURCE = "?";
  
  /**
   * The constructor.
   */
  private ValueUtil() {

  }

  /**
   * This method parses a numeric value.
   * 
   * @param numberValue is the number value as string.
   * @return the value as number.
   * @throws WrongValueTypeException if the given string is no number.
   */
  private static Number parseNumber(String numberValue, String valueSource)
      throws WrongValueTypeException {

    try {
      Double d = Double.valueOf(numberValue);
      return NumericUtil.toSimplestNumber(d);
    } catch (NumberFormatException e) {
      // TODO: valueSource as first arg, booleanValue as additional arg!
      throw new WrongValueTypeException(numberValue, valueSource, Number.class, e);
    }
  }

  public static <V> V convertValue(String value, Class<V> type, V defaultValue)
      throws WrongValueTypeException {

    if (value == null) {
      return defaultValue;
    } else {
      return convertValue(UNKNOWN_SOURCE, value, type);
    }
  }

  public static <V> V convertValue(String valueSource, String value, Class<V> type, V defaultValue)
      throws WrongValueTypeException {

    if (value == null) {
      return defaultValue;
    } else {
      return convertValue(valueSource, value, type);
    }
  }

  public static <V> V convertValue(String value, Class<V> type) throws ValueNotSetException,
      WrongValueTypeException {

    return convertValue(UNKNOWN_SOURCE, value, type);
  }

  public static <V> V convertValue(String valueSource, String value, Class<V> type)
      throws ValueNotSetException, WrongValueTypeException {

    if (value == null) {
      throw new ValueNotSetException(valueSource);
    }
    Object result;
    try {
      if (type.isAssignableFrom(String.class)) {
        result = value;
      } else if ((type == boolean.class) || (type == Boolean.class)) {
        result = StringUtil.parseBoolean(value);
        if (result == null) {
          // ...
          throw new WrongValueTypeException(value, valueSource, type);
        }
      } else if ((type == int.class) || (type == Integer.class)) {
        result = Integer.valueOf(value);
      } else if ((type == long.class) || (type == Long.class)) {
        result = Long.valueOf(value);
      } else if ((type == double.class) || (type == Double.class)) {
        result = Double.valueOf(value);
      } else if (type == Class.class) {
        result = Class.forName(value);
      } else if ((type == float.class) || (type == Float.class)) {
        result = Float.valueOf(value);
      } else if ((type == short.class) || (type == Short.class)) {
        result = Short.valueOf(value);
      } else if ((type == byte.class) || (type == Byte.class)) {
        result = Byte.valueOf(value);
      } else if (type == Number.class) {
        result = parseNumber(value, valueSource);
      } else if (type == Date.class) {
        result = Iso8601Util.parseDate(value);
      } else if (type == Character.class) {
        if (value.length() == 1) {
          result = Character.valueOf(value.charAt(0));
        } else {
          // ...
          throw new WrongValueTypeException(value, valueSource, type);
        }
      } else if (type == Class.class) {
        result = Class.forName(value);
      } else {
        // throw new UnknownValueType();
        throw new WrongValueTypeException(value, valueSource, type);
      }
    } catch (NumberFormatException e) {
      throw new WrongValueTypeException(value, valueSource, type, e);
    } catch (ClassNotFoundException e) {
      throw new WrongValueTypeException(value, valueSource, type, e);
    }
    return (V) result;
  }

}
