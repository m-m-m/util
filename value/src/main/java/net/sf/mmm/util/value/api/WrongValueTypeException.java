/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.lang.reflect.Type;

import net.sf.mmm.util.exception.NlsBundleUtilExceptionRoot;

/**
 * This exception is thrown if a value has the wrong type (a different value type was expected).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @deprecated use {@link net.sf.mmm.util.exception.api.WrongValueTypeException}
 */
@Deprecated
public class WrongValueTypeException extends ValueException {

  private static final long serialVersionUID = 1L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "WrongValueType";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected WrongValueTypeException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param value is the wrong value.
   * @param expectedType is the expected type of the value.
   */
  public WrongValueTypeException(Object value, Type expectedType) {

    this((Throwable) null, value, expectedType);
  }

  /**
   * The constructor.
   *
   * @param nested is the throwable that caused this exception.
   * @param value is the wrong value.
   * @param expectedType is the expected type of the value.
   */
  public WrongValueTypeException(Throwable nested, Object value, Type expectedType) {

    this(nested, value, null, expectedType);
  }

  /**
   * The constructor.
   *
   * @param value is the wrong value.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   * @param expectedType is the expected type of the value.
   */
  public WrongValueTypeException(Object value, Object valueSource, Type expectedType) {

    this((Throwable) null, value, valueSource, expectedType);
  }

  /**
   * The constructor.
   *
   * @param nested is the throwable that caused this exception.
   * @param value is the wrong value.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   * @param expectedType is the expected type of the value.
   */
  public WrongValueTypeException(Throwable nested, Object value, Object valueSource, Type expectedType) {

    super(nested, createBundle(NlsBundleUtilExceptionRoot.class).errorValueWrongType(value, getType(value),
        expectedType, valueSource));
  }

  /**
   * This method gets the type reflecting the actual value.
   *
   * @param value is the value for which the type is requested.
   * @return the type of the given {@code value}.
   */
  private static Type getType(Object value) {

    if (value == null) {
      return null;
    } else {
      return value.getClass();
    }
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
