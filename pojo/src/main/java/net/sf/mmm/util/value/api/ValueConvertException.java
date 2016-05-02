/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.lang.reflect.Type;

import net.sf.mmm.util.value.NlsBundleUtilValueRoot;

/**
 * The {@link ValueConvertException} is thrown if the {@link ValueConverter#convert(Object, Object, Class) conversion}
 * of some value failed.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class ValueConvertException extends ValueException {

  /** UID for serialization */
  private static final long serialVersionUID = 9211949231445626445L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ValueConvert";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ValueConvertException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param value is the value that could NOT be converted.
   * @param targetType is the (generic) type the value should be converted to.
   */
  public ValueConvertException(Object value, Type targetType) {

    this(null, value, targetType);
  }

  /**
   * The constructor.
   *
   * @param value is the value that could NOT be converted.
   * @param targetType is the (generic) type the value should be converted to.
   * @param valueSource describes the source of the value. This may be the filename where the value was read from, an
   *        XPath where the value was located in an XML document, etc. It is used in in the exception message. This will
   *        help to find the problem easier.
   */
  public ValueConvertException(Object value, Type targetType, Object valueSource) {

    this(null, value, targetType, valueSource);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be converted.
   * @param targetType is the (generic) type the value should be converted to.
   */
  public ValueConvertException(Throwable nested, Object value, Type targetType) {

    this(nested, value, targetType, null);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be converted.
   * @param targetType is the (generic) type the value should be converted to.
   * @param valueSource describes the source of the value. This may be the filename where the value was read from, an
   *        XPath where the value was located in an XML document, etc. It is used in in the exception message. This will
   *        help to find the problem easier.
   */
  public ValueConvertException(Throwable nested, Object value, Type targetType, Object valueSource) {

    super(nested, createBundle(NlsBundleUtilValueRoot.class).errorValueConvert(value, targetType, valueSource));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
