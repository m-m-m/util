/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * An {@link InvocationFailedException} is thrown if a {@link java.lang.reflect
 * reflective} invocation of an {@link AccessibleObject} failed. Unlike
 * {@link InvocationTargetException} this is a {@link RuntimeException} and has
 * {@link net.sf.mmm.util.nls.api.NlsThrowable native-language-support}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class InvocationFailedException extends ReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5065495224197046745L;

  /**
   * The constructor.
   * 
   * @param nested is the exception to adapt.
   */
  public InvocationFailedException(InvocationTargetException nested) {

    super(nested.getCause(), NlsBundleUtilCore.ERR_INVOCATION_FAILED);
  }

  /**
   * The constructor.
   * 
   * @param nested is the exception to adapt.
   * @param accessible is the {@link AccessibleObject} (
   *        {@link java.lang.reflect.Method} or
   *        {@link java.lang.reflect.Constructor}) that was invoked and caused
   *        the exception.
   * @param instance is the instance on which the invocation took place and
   *        caused the exception. It may be <code>null</code> if the
   *        <code>accessible</code> is
   *        {@link java.lang.reflect.Modifier#isStatic(int) static}.
   */
  public InvocationFailedException(InvocationTargetException nested, AccessibleObject accessible,
      Object instance) {

    super(nested.getCause(), NlsBundleUtilCore.ERR_INVOCATION_FAILED_ON, toMap(KEY_ACCESSIBLE,
        accessible, KEY_OBJECT, instance));
  }

}
