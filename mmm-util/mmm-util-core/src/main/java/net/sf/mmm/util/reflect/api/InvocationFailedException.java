/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * An {@link InvocationFailedException} is thrown if an invocation failed. Typically invocation means a
 * {@link java.lang.reflect reflective} call of an {@link AccessibleObject}. Unlike
 * {@link InvocationTargetException} this is a {@link RuntimeException} and has
 * {@link net.sf.mmm.util.exception.api.NlsThrowable native-language-support}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class InvocationFailedException extends ReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5065495224197046745L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "InvocationFailed";

  /**
   * The constructor.
   *
   * @param nested is the exception to adapt.
   */
  public InvocationFailedException(InvocationTargetException nested) {

    super(nested.getCause(), createBundle(NlsBundleUtilCoreRoot.class).errorInvocationFailed());
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public InvocationFailedException(Exception nested) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorInvocationFailed());
  }

  /**
   * The constructor.
   *
   * @param nested is the exception to adapt.
   * @param accessible is the {@link AccessibleObject} ({@link java.lang.reflect.Method} or
   *        {@link java.lang.reflect.Constructor}) that was invoked and caused the exception.
   * @since 6.0.0
   */
  public InvocationFailedException(InvocationTargetException nested, AccessibleObject accessible) {

    super(nested.getCause(), createBundle(NlsBundleUtilCoreRoot.class).errorInvocationFailedOf(accessible));
  }

  /**
   * The constructor.
   *
   * @param nested is the exception to adapt.
   * @param accessible is the {@link AccessibleObject} ({@link java.lang.reflect.Method} or
   *        {@link java.lang.reflect.Constructor}) that was invoked and caused the exception.
   * @param instance is the instance on which the invocation took place and caused the exception. It may be
   *        <code>null</code> if the <code>accessible</code> is
   *        {@link java.lang.reflect.Modifier#isStatic(int) static}.
   */
  public InvocationFailedException(InvocationTargetException nested, AccessibleObject accessible, Object instance) {

    super(nested.getCause(), createBundle(NlsBundleUtilCoreRoot.class).errorInvocationFailedOn(instance, accessible));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }
}
