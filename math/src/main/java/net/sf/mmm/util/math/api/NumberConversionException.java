/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessage;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;

/**
 * This is the exception thrown if a numeric value can NOT converted to a specific number-type.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class NumberConversionException extends RuntimeException implements AttributeReadMessage, AttributeReadMessageCode {

  private static final long serialVersionUID = 1L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "NumberConv";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected NumberConversionException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param value is the value that could NOT be converted.
   * @param targetType represents the type the {@code value} should have been converted to. This will
   *        typically be a {@link Class} object.
   */
  public NumberConversionException(Object value, Object targetType) {

    this(null, value, targetType);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be converted.
   * @param targetType represents the type the {@code value} should have been converted to. This will
   *        typically be a {@link Class} object.
   */
  public NumberConversionException(Throwable nested, Object value, Object targetType) {

    super("Can not convert number '" + value + "' to '" + targetType + "'!", nested);
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
