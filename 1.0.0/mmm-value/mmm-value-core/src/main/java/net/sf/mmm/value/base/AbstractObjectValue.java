/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import java.util.Date;

import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.date.Iso8601Util;
import net.sf.mmm.util.value.WrongValueTypeException;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.value.api.MutableGenericValue} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractObjectValue extends AbstractTemplatedGenericValue<Object> {

  /**
   * The constructor.
   */
  public AbstractObjectValue() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object convertValue(Object value) throws WrongValueTypeException {

    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <T> T convertValue(Class<T> type, Object value) throws WrongValueTypeException {

    if (value == null) {
      return null;
    }
    Object result;
    Class<?> valueType = value.getClass();
    if (type.isAssignableFrom(valueType)) {
      result = value;
    } else if (type == String.class) {
      result = toString(value);
    } else if ((type == boolean.class) || (type == Boolean.class)) {
      result = toBoolean(value);
    } else if ((type == int.class) || (type == Integer.class)) {
      result = Integer.valueOf(toInteger(value));
    } else if ((type == long.class) || (type == Long.class)) {
      result = Long.valueOf(toLong(value));
    } else if ((type == double.class) || (type == Double.class)) {
      result = Double.valueOf(toDouble(value));
    } else if (type == Class.class) {
      result = toClass(value);
    } else if ((type == float.class) || (type == Float.class)) {
      result = Float.valueOf(toFloat(value));
    } else if ((type == short.class) || (type == Short.class)) {
      result = Short.valueOf(toShort(value));
    } else if ((type == byte.class) || (type == Byte.class)) {
      result = Byte.valueOf(toByte(value));
    } else if (type == Number.class) {
      result = parseNumber(value.toString());
    } else if (type == Date.class) {
      result = toDate(value);
    } else if (type == Character.class) {
      result = toCharacter(value);
    } else {
      result = toValue(type, value);
    }
    if (type.isPrimitive()) {
      return (T) result;
    }
    return type.cast(result);
  }

  /**
   * This method is called from {@link #convertValue(Class, Object)} if the
   * given <code>type</code> is NOT one of the following:
   * <ul>
   * <li>{@link Object}</li>
   * <li>{@link String}</li>
   * <li>{@link Class}</li>
   * <li>{@link Number}</li>
   * <li>{@link Boolean} or <code>boolean</code></li>
   * <li>{@link Integer} or <code>int</code></li>
   * <li>{@link Long} or <code>long</code></li>
   * <li>{@link Double} or <code>double</code></li>
   * <li>{@link Float} or <code>float</code></li>
   * <li>{@link Short} or <code>short</code></li>
   * <li>{@link Byte} or <code>byte</code></li>
   * </ul>
   * This implementation simply throws an {@link WrongValueTypeException}.
   * Override to add support for further types.
   * 
   * @param <T> is the templated type of the value.
   * @param type is the class reflecting the value.
   * @param value is the value to convert to the given <code>type</code>.
   * @return the converted value.
   * @throws WrongValueTypeException if the given <code>value</code> can NOT
   *         be converted to <code>type</code>.
   */
  protected <T> T toValue(Class<T> type, Object value) throws WrongValueTypeException {

    throw new WrongValueTypeException(this, type);
  }

  /**
   * This method gets the given value object as string.
   * 
   * @param value is the value to get as string.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         a string.
   */
  protected String toString(Object value) throws WrongValueTypeException {

    if (value == null) {
      return null;
    } else {
      return value.toString();
    }
  }

  /**
   * This method gets the given value object as character.
   * 
   * @param value is the value to get as character.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         a character.
   */
  protected Character toCharacter(Object value) throws WrongValueTypeException {

    if (value == null) {
      return null;
    } else {
      String string = value.toString();
      if ((string != null) && (string.length() == 1)) {
        return Character.valueOf(string.charAt(0));
      }
      throw new WrongValueTypeException(this, Character.class);
    }
  }

  /**
   * This method gets the given value object as boolean.
   * 
   * @param value is the value to get as boolean.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         a boolean.
   */
  protected Boolean toBoolean(Object value) throws WrongValueTypeException {

    try {
      return (Boolean) value;
    } catch (ClassCastException e) {
      Boolean result = StringUtil.getInstance().parseBoolean(value.toString());
      if (result == null) {
        throw new WrongValueTypeException(this, Boolean.class);
      } else {
        return result;
      }
    }
  }

  /**
   * This method gets the given value object as date.
   * 
   * @param value is the value to get as date.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         a date.
   */
  protected Date toDate(Object value) throws WrongValueTypeException {

    try {
      return (Date) value;
    } catch (ClassCastException e) {
      try {
        return Iso8601Util.getInstance().parseDate(value.toString());
      } catch (Exception e1) {
        throw new WrongValueTypeException(this, Date.class);
      }
    }
  }

  /**
   * This method gets the given value object as double.
   * 
   * @param value is the value to get as double.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         a double.
   */
  protected double toDouble(Object value) throws WrongValueTypeException {

    try {
      return ((Number) value).doubleValue();
    } catch (ClassCastException e) {
      try {
        return Double.parseDouble(value.toString());
      } catch (NumberFormatException e1) {
        throw new WrongValueTypeException(this, Double.class);
      }
    }
  }

  /**
   * This method gets the given value object as float.
   * 
   * @param value is the value to get as float.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         a float.
   */
  protected float toFloat(Object value) throws WrongValueTypeException {

    try {
      return ((Number) value).floatValue();
    } catch (ClassCastException e) {
      try {
        return Float.parseFloat(value.toString());
      } catch (NumberFormatException e1) {
        throw new WrongValueTypeException(this, Float.class);
      }
    }
  }

  /**
   * This method gets the given value object as short.
   * 
   * @param value is the value to get as short.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         a short.
   */
  protected short toShort(Object value) throws WrongValueTypeException {

    try {
      return ((Number) value).shortValue();
    } catch (ClassCastException e) {
      try {
        return Short.parseShort(value.toString());
      } catch (NumberFormatException e1) {
        throw new WrongValueTypeException(this, Short.class);
      }
    }
  }

  /**
   * This method gets the given value object as byte.
   * 
   * @param value is the value to get as byte.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         a byte.
   */
  protected byte toByte(Object value) throws WrongValueTypeException {

    try {
      return ((Number) value).byteValue();
    } catch (ClassCastException e) {
      try {
        return Byte.parseByte(value.toString());
      } catch (NumberFormatException e1) {
        throw new WrongValueTypeException(this, Byte.class);
      }
    }
  }

  /**
   * This method gets the given value object as integer.
   * 
   * @param value is the value to get as integer.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         an integer.
   */
  protected int toInteger(Object value) throws WrongValueTypeException {

    try {
      return ((Number) value).intValue();
    } catch (ClassCastException e) {
      try {
        return Integer.parseInt(value.toString());
      } catch (NumberFormatException e1) {
        throw new WrongValueTypeException(this, Integer.class);
      }
    }
  }

  /**
   * This method gets the given value object as number.
   * 
   * @param value is the value to get as integer.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         an integer.
   */
  protected Number toNumber(Object value) throws WrongValueTypeException {

    try {
      return (Number) value;
    } catch (ClassCastException e) {
      return parseNumber(value.toString());
    }
  }

  /**
   * This method gets the given value object as long.
   * 
   * @param value is the value to get as long.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         a long.
   */
  protected long toLong(Object value) throws WrongValueTypeException {

    try {
      return ((Number) value).longValue();
    } catch (ClassCastException e) {
      try {
        return Long.parseLong(value.toString());
      } catch (NumberFormatException e1) {
        throw new WrongValueTypeException(this, Long.class);
      }
    }
  }

  /**
   * This method gets the given value object as class.
   * 
   * @param value is the value to get as class.
   * @return the value to convert.
   * @throws WrongValueTypeException if the given object can NOT be converted to
   *         a class.
   */
  protected Class<?> toClass(Object value) throws WrongValueTypeException {

    try {
      return (Class<?>) value;
    } catch (ClassCastException e) {
      try {
        return Class.forName(value.toString());
      } catch (ClassNotFoundException e1) {
        throw new WrongValueTypeException(this, Class.class, e1);
      }
    }
  }

}
