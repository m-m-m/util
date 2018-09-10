/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;

/**
 * An {@link AccessFailedException} is thrown if a {@link java.lang.reflect reflective} call failed because
 * the executing code does NOT have access to the according definition. Unlike {@link IllegalAccessException}
 * this is a {@link RuntimeException}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class AccessFailedException extends ReflectionException {

  private static final long serialVersionUID = 1L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "AccessFailed";

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param accessible is the {@link AccessibleObject} ( {@link java.lang.reflect.Field},
   *        {@link java.lang.reflect.Method} or {@link java.lang.reflect.Constructor}) that could NOT be
   *        accessed.
   */
  public AccessFailedException(Throwable nested, AccessibleObject accessible) {

    this(accessible, nested);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param type is the {@link Class} that is NOT accessible.
   */
  public AccessFailedException(Throwable nested, Type type) {

    this(type, nested);
  }

  private AccessFailedException(Object object, Throwable nested) {

    super("Reflective access for '" + object + "' failed!", nested);
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
