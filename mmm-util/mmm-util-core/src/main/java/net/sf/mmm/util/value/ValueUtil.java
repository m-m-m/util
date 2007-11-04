/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value;

import java.util.Date;

import net.sf.mmm.util.Iso8601Util;
import net.sf.mmm.util.NumericUtil;
import net.sf.mmm.util.StringUtil;

/**
 * This is a utility class providing support for dealing with values (e.g. when
 * reading configurations).
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
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
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

  /**
   * This method converts the given {@link String}-<code>value</code> to the
   * given <code>type</code>.
   * 
   * @param <V> is the type the <code>value</code> should be converted to.
   * @param value is the value to convert. It may be <code>null</code>.
   * @param type is the type the <code>value</code> should be converted to.
   * @param defaultValue is returned if the given <code>value</code> is
   *        <code>null</code>. It may also be <code>null</code>.
   * @return the <code>value</code> converted to <code>type</code> or the
   *         <code>defaultValue</code> if <code>value</code> was
   *         <code>null</code>. It will only return <code>null</code> if
   *         both <code>value</code> and <code>defaultValue</code> are
   *         <code>null</code>.
   * @throws WrongValueTypeException if the given <code>value</code> is NOT
   *         <code>null</code> but can NOT be converted to the given
   *         <code>type</code> (e.g. if <code>value</code> is "12x" and
   *         <code>type</code> is <code>Integer.class</code>).
   */
  public static <V> V convertValue(String value, Class<V> type, V defaultValue)
      throws WrongValueTypeException {

    if (value == null) {
      return defaultValue;
    } else {
      return convertValue(UNKNOWN_SOURCE, value, type);
    }
  }

  /**
   * This method converts the given {@link String}-<code>value</code> to the
   * given <code>type</code>.
   * 
   * @param <V> is the type the <code>value</code> should be converted to.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param value is the value to convert. It may be <code>null</code>.
   * @param type is the type the <code>value</code> should be converted to.
   * @param defaultValue is returned if the given <code>value</code> is
   *        <code>null</code>. It may also be <code>null</code>.
   * @return the <code>value</code> converted to <code>type</code> or the
   *         <code>defaultValue</code> if <code>value</code> was
   *         <code>null</code>. It will only return <code>null</code> if
   *         both <code>value</code> and <code>defaultValue</code> are
   *         <code>null</code>.
   * @throws WrongValueTypeException if the given <code>value</code> is NOT
   *         <code>null</code> but can NOT be converted to the given
   *         <code>type</code> (e.g. if <code>value</code> is "12x" and
   *         <code>type</code> is <code>Integer.class</code>).
   */
  public static <V> V convertValue(String valueSource, String value, Class<V> type, V defaultValue)
      throws WrongValueTypeException {

    if (value == null) {
      return defaultValue;
    } else {
      return convertValue(valueSource, value, type);
    }
  }

  /**
   * This method converts the given {@link String}-<code>value</code> to the
   * given <code>type</code>.
   * 
   * @param <V> is the type the <code>value</code> should be converted to.
   * @param value is the value to convert.
   * @param type is the type the <code>value</code> should be converted to.
   * @return the <code>value</code> converted to <code>type</code>.
   * @throws ValueNotSetException if the given <code>value</code> is
   *         <code>null</code>.
   * @throws WrongValueTypeException if the given <code>value</code> is NOT
   *         <code>null</code> but can NOT be converted to the given
   *         <code>type</code> (e.g. if <code>value</code> is "12x" and
   *         <code>type</code> is <code>Integer.class</code>).
   */
  public static <V> V convertValue(String value, Class<V> type) throws ValueNotSetException,
      WrongValueTypeException {

    return convertValue(UNKNOWN_SOURCE, value, type);
  }

  /**
   * This method converts the given {@link String}-<code>value</code> to the
   * given <code>type</code>.
   * 
   * @param <V> is the type the <code>value</code> should be converted to.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param value is the value to convert.
   * @param type is the type the <code>value</code> should be converted to.
   * @return the <code>value</code> converted to <code>type</code>.
   * @throws ValueNotSetException if the given <code>value</code> is
   *         <code>null</code>.
   * @throws WrongValueTypeException if the given <code>value</code> is NOT
   *         <code>null</code> but can NOT be converted to the given
   *         <code>type</code> (e.g. if <code>value</code> is "12x" and
   *         <code>type</code> is <code>Integer.class</code>).
   */
  @SuppressWarnings("unchecked")
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
    // ATTENTION: cast does NOT work if type is primitive
    // return type.cast(result);
    return (V) result;
  }
}
