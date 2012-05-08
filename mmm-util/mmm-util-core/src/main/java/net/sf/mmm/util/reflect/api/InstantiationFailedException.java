/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.Type;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * An {@link InstantiationFailedException} indicates that the {@link Class#newInstance() instantiation} of a
 * {@link Class} failed for arbitrary reasons. Unlike {@link InstantiationException} this is a
 * {@link RuntimeException} and has {@link net.sf.mmm.util.nls.api.NlsThrowable native-language-support}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class InstantiationFailedException extends ReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5506046383771496547L;

  /**
   * The constructor.
   * 
   * @param type is the {@link Class} that could NOT be {@link Class#newInstance() instantiated}.
   */
  public InstantiationFailedException(Type type) {

    super(NlsBundleUtilCore.ERR_INSTANTIATION_FAILED, toMap(KEY_TYPE, type));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param type is the {@link Class} that could NOT be {@link Class#newInstance() instantiated}.
   */
  public InstantiationFailedException(Throwable nested, Type type) {

    super(nested, NlsBundleUtilCore.ERR_INSTANTIATION_FAILED, toMap(KEY_TYPE, type));
  }

}
