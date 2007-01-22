/* $ Id: $ */
package net.sf.mmm.value.base;

import java.util.Date;

import net.sf.mmm.value.api.WrongValueTypeException;
import net.sf.mmm.util.Iso8601Util;
import net.sf.mmm.value.api.MutableGenericValue;

/**
 * This is the abstract base implementation of the {@link MutableGenericValue}
 * interface. It is based on a {@link #getPlainValue() "string value"}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractStringValue extends AbstractTemplatedGenericValue<String> {

  /**
   * The constructor.
   */
  public AbstractStringValue() {

    super();
  }

  /**
   * @see net.sf.mmm.value.base.AbstractTemplatedGenericValue#convertValue(java.lang.Class,
   *      java.lang.Object)
   */
  @Override
  protected <T> T convertValue(Class<T> type, String value) throws WrongValueTypeException {

    Object result;
    if (value == null) {
      result = null;
    } else {
      try {
        if (type.isAssignableFrom(String.class)) {
          result = value;
        } else if ((type == boolean.class) || (type == Boolean.class)) {
          result = parseBoolean(value);
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
          result = parseNumber(value);
        } else if (type == Date.class) {
          result = parseDate(value);
        } else {
          result = toValue(type, value);
        }
      } catch (NumberFormatException e) {
        throw new WrongValueTypeException(this, type, e);
      } catch (ClassNotFoundException e) {
        throw new WrongValueTypeException(this, type, e);
      }
    }
    return (T) result;
  }

  /**
   * This method is called from {@link #convertValue(Class, String)} if the
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
   * @param <T>
   * @param type
   * @param value
   *        is the value to convert.
   * @return the converted value.
   * @throws WrongValueTypeException
   */
  protected <T> T toValue(Class<T> type, Object value) throws WrongValueTypeException {

    throw new WrongValueTypeException(this, type);
  }

  /**
   * @see net.sf.mmm.value.base.AbstractTemplatedGenericValue#convertValue(java.lang.Object)
   */
  @Override
  protected String convertValue(Object value) throws WrongValueTypeException {

    String result;
    if (value == null) {
      result = null;
    } else {
      if (value instanceof Date) {
        result = Iso8601Util.formatDateTime((Date) value);
      } else if (value instanceof Class) {
        result = ((Class<?>) value).getName();
      } else {
        result = value.toString();
      }
    }
    return result;
  }

  /**
   * This method parses a boolean value.
   * 
   * @param booleanValue
   *        is the boolean value as string.
   * @return the value as boolean.
   * @throws WrongValueTypeException
   *         if the given string is no boolean.
   */
  protected Boolean parseBoolean(String booleanValue) throws WrongValueTypeException {

    if (Boolean.TRUE.toString().equalsIgnoreCase(booleanValue)) {
      return Boolean.TRUE;
    } else if (Boolean.FALSE.toString().equalsIgnoreCase(booleanValue)) {
      return Boolean.FALSE;
    } else {
      throw new WrongValueTypeException(this, Boolean.class);
    }
  }

  /**
   * This method parses a date value.
   * 
   * @param dateValue
   *        is the date value as string.
   * @return the value as date.
   * @throws WrongValueTypeException
   *         if the given string is no date.
   */
  protected Date parseDate(String dateValue) throws WrongValueTypeException {

    try {
      return Iso8601Util.parseDate(dateValue);
    } catch (Exception e) {
      throw new WrongValueTypeException(this, Date.class, e);
    }
  }

}
