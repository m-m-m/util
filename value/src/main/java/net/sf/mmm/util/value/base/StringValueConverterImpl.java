/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;
import java.util.Date;

import javax.inject.Inject;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.ValueNotSetException;
import net.sf.mmm.util.exception.api.WrongValueTypeException;
import net.sf.mmm.util.math.api.MathUtil;
import net.sf.mmm.util.math.base.MathUtilImpl;
import net.sf.mmm.util.reflect.base.EnumHelper;
import net.sf.mmm.util.value.api.StringValueConverter;

/**
 * This is the implementation of {@link StringValueConverter}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class StringValueConverterImpl extends AbstractGenericValueConverter<String> implements StringValueConverter {

  private static StringValueConverterImpl instance;

  private Iso8601Util iso8601Util;

  private MathUtil mathUtil;

  /**
   * The constructor.
   */
  public StringValueConverterImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link StringValueConverterImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please prefer dependency-injection instead of using this method.
   *
   * @return the singleton instance.
   */
  public static StringValueConverterImpl getInstance() {

    if (instance == null) {
      synchronized (StringValueConverterImpl.class) {
        if (instance == null) {
          StringValueConverterImpl converter = new StringValueConverterImpl();
          converter.initialize();
          instance = converter;
        }
      }
    }
    return instance;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.iso8601Util == null) {
      this.iso8601Util = Iso8601UtilImpl.getInstance();
    }
    if (this.mathUtil == null) {
      this.mathUtil = MathUtilImpl.getInstance();
    }
  }

  /**
   * This method gets the {@link Iso8601Util} used to parse and format date and time according to the standard
   * {@code ISO-8601}.
   *
   * @return the iso8601Util
   */
  protected Iso8601Util getIso8601Util() {

    return this.iso8601Util;
  }

  /**
   * This method sets the {@link #getIso8601Util() Iso8601Util}.
   *
   * @param iso8601Util the iso8601Util to set
   */
  @Inject
  public void setIso8601Util(Iso8601Util iso8601Util) {

    getInitializationState().requireNotInitilized();
    this.iso8601Util = iso8601Util;
  }

  /**
   * This method parses a numeric value.
   *
   * @param numberValue is the number value as string.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   * @return the value as number.
   * @throws WrongValueTypeException if the given string is no number.
   */
  private Number parseNumber(String numberValue, Object valueSource) throws WrongValueTypeException {

    try {
      Double d = Double.valueOf(numberValue);
      return this.mathUtil.toSimplestNumber(d);
    } catch (NumberFormatException e) {
      throw new WrongValueTypeException(e, numberValue, valueSource, Number.class);
    }
  }

  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final <TARGET> TARGET convertValue(String value, Object valueSource, Class<TARGET> type, Type targetType)
      throws ValueNotSetException, WrongValueTypeException {

    if (value == null) {
      throw new ValueNotSetException(valueSource);
    }
    Object result;
    try {
      if (type.isEnum()) {
        result = EnumHelper.fromString(value, (Class<Enum>) type);
      } else if (type.isAssignableFrom(String.class)) {
        result = value;
      } else if ((type == boolean.class) || (type == Boolean.class)) {
        result = Boolean.valueOf(value);
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
      } else if (type == BigDecimal.class) {
        result = new BigDecimal(value);
      } else if (type == BigInteger.class) {
        result = new BigInteger(value);
      } else if (type == Number.class) {
        result = parseNumber(value, valueSource);
      } else if (type == Date.class) {
        result = this.iso8601Util.parseDate(value);
      } else if ((type == Character.class) || ((type == char.class))) {
        if (value.length() == 1) {
          result = Character.valueOf(value.charAt(0));
        } else {
          throw new WrongValueTypeException(value, valueSource, type);
        }
      } else if (type == Currency.class) {
        result = Currency.getInstance(value);
      } else {
        return convertUnknownValue(value, type, valueSource);
      }
    } catch (IllegalCaseException | NumberFormatException | ClassNotFoundException e) {
      throw new WrongValueTypeException(e, value, valueSource, type);
    }
    // ATTENTION: cast does NOT work if type is primitive
    // return type.cast(result);
    return (TARGET) result;
  }

  /**
   * This method converts the given {@link String}-{@code value} to the given {@code type}. It is called from
   * {@link #convertValue(String, Object, Class, Type)} if the given {@code type} is unknown. This default
   * implementation simply throws a new {@link WrongValueTypeException}. You can extend this class and
   * override this method in order to support the conversion for additional types. You should first handle the
   * conversion for all value types you like. Then for all other types you should delegate to the
   * {@code super} method implementation.
   *
   * @param value is the value to convert.
   * @param type is the type the {@code value} should be converted to.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   *
   * @param <V> is the type the {@code value} should be converted to.
   * @return the {@code value} converted to {@code type}.
   * @throws ValueNotSetException if the given {@code value} is {@code null}.
   * @throws WrongValueTypeException if the given {@code value} is NOT {@code null} but can NOT be converted
   *         to the given {@code type} (e.g. if {@code value} is "12x" and {@code type} is
   *         {@code Integer.class}).
   */
  protected <V> V convertUnknownValue(String value, Class<V> type, Object valueSource) throws ValueNotSetException, WrongValueTypeException {

    // throw new UnknownValueType();
    throw new WrongValueTypeException(value, valueSource, type);
  }

}
