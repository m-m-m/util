/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.AccessibleObject;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * An {@link AccessFailedException} is thrown if a {@link java.lang.reflect
 * reflective} call failed because the executing code does NOT have access to
 * the according definition. Unlike {@link IllegalAccessException} this is a
 * {@link RuntimeException} and has {@link net.sf.mmm.util.nls.api.NlsThrowable
 * native-language-support}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class AccessFailedException extends ReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5506046383771496547L;

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param accessible is the {@link AccessibleObject} (
   *        {@link java.lang.reflect.Field}, {@link java.lang.reflect.Method} or
   *        {@link java.lang.reflect.Constructor}) that could NOT be accessed.
   */
  public AccessFailedException(IllegalAccessException nested, AccessibleObject accessible) {

    super(nested, NlsBundleUtilCore.ERR_ACCESS_FAILED, accessible);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param clazz is the {@link Class} that is NOT accessible.
   */
  public AccessFailedException(IllegalAccessException nested, Class<?> clazz) {

    super(nested, NlsBundleUtilCore.ERR_ACCESS_FAILED, clazz);
  }

}
