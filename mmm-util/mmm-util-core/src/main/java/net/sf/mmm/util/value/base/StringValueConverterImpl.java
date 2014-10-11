/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.EnumProvider;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.SimpleEnumProvider;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.math.base.MathUtilImpl;
import net.sf.mmm.util.value.api.StringValueConverter;
import net.sf.mmm.util.value.api.ValueNotSetException;
import net.sf.mmm.util.value.api.WrongValueTypeException;

/**
 * This is the implementation of {@link StringValueConverter}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named(StringValueConverter.CDI_NAME)
public class StringValueConverterImpl extends AbstractGenericValueConverter<String> implements StringValueConverter {

  /** @see #getInstance() */
  private static StringValueConverterImpl instance;

  /** @see #getIso8601Util() */
  private Iso8601Util iso8601Util;

  /** @see #getStringUtil() */
  private StringUtil stringUtil;

  /** @see #getEnumProvider() */
  private EnumProvider enumProvider;

  /**
   * The constructor.
   */
  public StringValueConverterImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link StringValueConverterImpl}.<br/>
   * <b>ATTENTION:</b><br/>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
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

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.iso8601Util == null) {
      this.iso8601Util = Iso8601UtilImpl.getInstance();
    }
    if (this.stringUtil == null) {
      this.stringUtil = StringUtilImpl.getInstance();
    }
    if (this.enumProvider == null) {
      SimpleEnumProvider impl = new SimpleEnumProvider();
      impl.initialize();
      this.enumProvider = impl;
    }
  }

  /**
   * This method gets the {@link Iso8601Util} used to parse and format date and time according to the standard
   * <code>ISO-8601</code>.
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
   * This method gets the {@link StringUtil} used to deal with strings.
   *
   * @return the stringUtil
   */
  protected StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * @param stringUtil is the stringUtil to set
   */
  @Inject
  public void setStringUtil(StringUtil stringUtil) {

    getInitializationState().requireNotInitilized();
    this.stringUtil = stringUtil;
  }

  /**
   * @return the {@link EnumProvider} used to deal with {@link Enum}s.
   */
  protected EnumProvider getEnumProvider() {

    return this.enumProvider;
  }

  /**
   * @param enumProvider is the {@link EnumProvider} to {@link Inject}.
   */
  @Inject
  public void setEnumProvider(EnumProvider enumProvider) {

    this.enumProvider = enumProvider;
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
  private static Number parseNumber(String numberValue, Object valueSource) throws WrongValueTypeException {

    try {
      Double d = Double.valueOf(numberValue);
      return MathUtilImpl.getInstance().toSimplestNumber(d);
    } catch (NumberFormatException e) {
      throw new WrongValueTypeException(e, numberValue, valueSource, Number.class);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings({ "unchecked" })
  public final <TARGET> TARGET convertValue(String value, Object valueSource, Class<TARGET> type, Type targetType)
      throws ValueNotSetException, WrongValueTypeException {

    if (value == null) {
      throw new ValueNotSetException(valueSource);
    }
    Object result;
    try {
      if (type.isEnum()) {
        EnumDefinition<TARGET, ?> enumDefinition = this.enumProvider.getEnumDefinition(type);
        result = this.enumProvider.getEnumValue(enumDefinition, value, true);
      } else if (type.isAssignableFrom(String.class)) {
        result = value;
      } else if ((type == boolean.class) || (type == Boolean.class)) {
        result = StringUtilImpl.getInstance().parseBoolean(value);
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
   * This method converts the given {@link String}-<code>value</code> to the given <code>type</code>. It is
   * called from {@link #convertValue(String, Object, Class, Type)} if the given <code>type</code> is unknown.
   * This default implementation simply throws a new {@link WrongValueTypeException}. You can extend this
   * class and override this method in order to support the conversion for additional types. You should first
   * handle the conversion for all value types you like. Then for all other types you should delegate to the
   * <code>super</code> method implementation.
   *
   * @param value is the value to convert.
   * @param type is the type the <code>value</code> should be converted to.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   *
   * @param <V> is the type the <code>value</code> should be converted to.
   * @return the <code>value</code> converted to <code>type</code>.
   * @throws ValueNotSetException if the given <code>value</code> is <code>null</code>.
   * @throws WrongValueTypeException if the given <code>value</code> is NOT <code>null</code> but can NOT be
   *         converted to the given <code>type</code> (e.g. if <code>value</code> is "12x" and
   *         <code>type</code> is <code>Integer.class</code>).
   */
  protected <V> V convertUnknownValue(String value, Class<V> type, Object valueSource) throws ValueNotSetException,
      WrongValueTypeException {

    // throw new UnknownValueType();
    throw new WrongValueTypeException(value, valueSource, type);
  }

}
