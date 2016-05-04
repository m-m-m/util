/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;

import net.sf.mmm.util.reflect.NlsBundleUtilReflectRoot;

/**
 * An {@link InvocationFailedException} is thrown if an invocation failed. Typically invocation means a
 * {@link java.lang.reflect reflective} call of an {@link AccessibleObject}. Unlike {@link InvocationTargetException}
 * this is a {@link RuntimeException} and has {@link net.sf.mmm.util.exception.api.NlsThrowable native-language-support}
 * .
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class InvocationFailedException extends ReflectionException {

  private static final long serialVersionUID = -5065495224197046745L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "InvocationFailed";

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public InvocationFailedException(Throwable nested) {

    this(nested, null, null);
  }

  /**
   * The constructor.
   *
   * @param nested is the exception to adapt.
   * @param accessible is the {@link AccessibleObject} ({@link java.lang.reflect.Method} or
   *        {@link java.lang.reflect.Constructor}) that was invoked and caused the exception.
   * @since 6.0.0
   */
  public InvocationFailedException(Throwable nested, AccessibleObject accessible) {

    this(nested, accessible, null);
  }

  /**
   * The constructor.
   *
   * @param nested is the exception to adapt. Typically {@link InvocationTargetException}.
   * @param accessible is the {@link AccessibleObject} ({@link java.lang.reflect.Method} or
   *        {@link java.lang.reflect.Constructor}) that was invoked and caused the exception.
   * @param instance is the instance on which the invocation took place and caused the exception. It may be {@code null}
   *        if the {@code accessible} is {@link java.lang.reflect.Modifier#isStatic(int) static}.
   */
  public InvocationFailedException(Throwable nested, AccessibleObject accessible, Object instance) {

    super(getInvocationTargetCause(nested),
        createBundle(NlsBundleUtilReflectRoot.class).errorInvocationFailed(accessible, instance));
  }

  private static Throwable getInvocationTargetCause(Throwable error) {

    if (error instanceof InvocationTargetException) {
      return error.getCause();
    }
    return error;
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }
}
