/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessage;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;

/**
 * A {@link ReflectionException} is thrown if an operation based on {@link java.lang.reflect reflection}
 * failed.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public abstract class ReflectionException extends RuntimeException implements AttributeReadMessage, AttributeReadMessageCode {

  private static final long serialVersionUID = 1L;

  /** Key for the NLS message. */
  protected static final String KEY_ACCESSIBLE = "accessible";

  /**
   * The constructor.
   *
   * @param message - see {@link #getMessage()}.
   * @param cause - see {@link #getCause()}.
   */
  public ReflectionException(String message, Throwable cause) {

    super(message, cause);
  }

  /**
   * The constructor.
   *
   * @param cause - see {@link #getCause()}.
   */
  public ReflectionException(Throwable cause) {

    super(cause);
  }

}
