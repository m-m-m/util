/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessage;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;

/**
 * This is the exception thrown if a date given as string was illegal.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class IllegalDateFormatException extends RuntimeException implements AttributeReadMessage, AttributeReadMessageCode {

  private static final long serialVersionUID = -5008989969842672695L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "IllegalDateFormat";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected IllegalDateFormatException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param dateString is the string that was supposed to be a valid date but is NOT.
   */
  public IllegalDateFormatException(String dateString) {

    this(dateString, null);
  }

  /**
   * The constructor.
   *
   * @param dateString is the string that was supposed to be a valid date but is NOT.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public IllegalDateFormatException(String dateString, Exception nested) {

    super("Illegal date '" + dateString + "'!", nested);
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
