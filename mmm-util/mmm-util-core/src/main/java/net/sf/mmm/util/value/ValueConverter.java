/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value;

import java.util.Date;

import net.sf.mmm.util.NumericUtil;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.date.Iso8601Util;

/**
 * This is a utility class providing support for dealing with values (e.g. when
 * reading configurations).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueConverter {

  /** The singleton instance. */
  public static final ValueConverter INSTANCE = new ValueConverter();

  /**
   * The constructor.
   */
  public ValueConverter() {

    super();
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
  private static Number parseNumber(String numberValue, Object valueSource)
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
   * @param value is the value to convert. It may be <code>null</code>.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param type is the type the <code>value</code> should be converted to.
   * @param defaultValue is returned if the given <code>value</code> is
   *        <code>null</code>. It may also be <code>null</code>.
   * @param <V> is the type the <code>value</code> should be converted to.
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
  public final <V> V convertValue(String value, Object valueSource, Class<V> type, V defaultValue)
      throws WrongValueTypeException {

    if (value == null) {
      return defaultValue;
    } else {
      return convertValue(value, valueSource, type);
    }
  }

  /**
   * This method converts the given <code>value</code> to a numeric type and
   * also validates that it is in the given range from <code>minimum</code> to
   * <code>maximum</code>.
   * 
   * @param value is the value to convert.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param minimum is the minimum number allowed. Use MIN_VALUE (e.g.
   *        {@link Double#MIN_VALUE}) if unbound.
   * @param maximum is the maximum number allowed. Use MAX_VALUE (e.g.
   *        {@link Long#MAX_VALUE}) if unbound.
   * 
   * @param <T> is the templated numeric value type.
   * @return the requested value in the given range from <code>minimum</code>
   *         and <code>maximum</code>.
   * @throws ValueNotSetException if the given <code>value</code> is
   *         <code>null</code>.
   * @throws WrongValueTypeException if the value is NO number.
   * @throws ValueOutOfRangeException if the value is NOT in the given range
   *         from <code>minimum</code> to <code>maximum</code>.
   */
  @SuppressWarnings("unchecked")
  public <T extends Number> T convertValue(String value, Object valueSource, T minimum, T maximum)
      throws ValueNotSetException, WrongValueTypeException, ValueOutOfRangeException {

    T result = (T) convertValue(value, valueSource, minimum.getClass());
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
      throw new ValueOutOfRangeException(value, valueSource, minimum, maximum);
    }
  }

  /**
   * This method gets a numeric value and also validates that it is in the given
   * range from <code>minimum</code> to <code>maximum</code>.
   * 
   * @param <T> is the templated numeric value type.
   * @param value is the value to convert.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param minimum is the minimum number allowed. Use MIN_VALUE (e.g.
   *        {@link Double#MIN_VALUE}) if unbound.
   * @param maximum is the maximum number allowed. Use MAX_VALUE (e.g.
   *        {@link Long#MAX_VALUE}) if unbound.
   * @param defaultValue is the default returned if <code>value</code> is
   *        <code>null</code>. It may be <code>null</code>. Else it must
   *        be in the given range from <code>minimum</code> to
   *        <code>maximum</code>.
   * @return the given <code>value</code> converted to {@literal <T>} in the
   *         range from <code>minimum</code> to <code>maximum</code> or the
   *         <code>defaultValue</code> if <code>value</code> is
   *         <code>null</code>. Will only be <code>null</code> if both
   *         <code>value</code> and <code>defaultValue</code> are
   *         <code>null</code>.
   * @throws WrongValueTypeException if the value is NO number.
   * @throws ValueOutOfRangeException if the value is NOT in the given range
   *         from <code>minimum</code> to <code>maximum</code>.
   */
  public <T extends Number> T convertValue(String value, Object valueSource, T minimum, T maximum,
      T defaultValue) throws WrongValueTypeException, ValueOutOfRangeException {

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
   * This method converts the given {@link String}-<code>value</code> to the
   * given <code>type</code>.
   * 
   * @param value is the value to convert.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param type is the type the <code>value</code> should be converted to.
   * @param <V> is the type the <code>value</code> should be converted to.
   * @return the <code>value</code> converted to <code>type</code>.
   * @throws ValueNotSetException if the given <code>value</code> is
   *         <code>null</code>.
   * @throws WrongValueTypeException if the given <code>value</code> is NOT
   *         <code>null</code> but can NOT be converted to the given
   *         <code>type</code> (e.g. if <code>value</code> is "12x" and
   *         <code>type</code> is <code>Integer.class</code>).
   */
  @SuppressWarnings("unchecked")
  public final <V> V convertValue(String value, Object valueSource, Class<V> type)
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
          throw new WrongValueTypeException(value, valueSource, type);
        }
      } else if (type == Class.class) {
        result = Class.forName(value);
      } else {
        return convertUnknownValue(value, type, valueSource);
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

  /**
   * This method converts the given {@link String}-<code>value</code> to the
   * given <code>type</code>. It is called from
   * {@link #convertValue(String, Object, Class)} if the given <code>type</code>
   * is unknown. This default implementation simply throws a new
   * {@link WrongValueTypeException}. You can extend this class and override
   * this method in order to support the conversion for additional types. You
   * should first handle the conversion for all value types you like. Then for
   * all other types you should delegate to the <code>super</code> method
   * implementation.
   * 
   * @param value is the value to convert.
   * @param type is the type the <code>value</code> should be converted to.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * 
   * @param <V> is the type the <code>value</code> should be converted to.
   * @return the <code>value</code> converted to <code>type</code>.
   * @throws ValueNotSetException if the given <code>value</code> is
   *         <code>null</code>.
   * @throws WrongValueTypeException if the given <code>value</code> is NOT
   *         <code>null</code> but can NOT be converted to the given
   *         <code>type</code> (e.g. if <code>value</code> is "12x" and
   *         <code>type</code> is <code>Integer.class</code>).
   */
  protected <V> V convertUnknownValue(String value, Class<V> type, Object valueSource)
      throws ValueNotSetException, WrongValueTypeException {

    // throw new UnknownValueType();
    throw new WrongValueTypeException(value, valueSource, type);
  }

}
