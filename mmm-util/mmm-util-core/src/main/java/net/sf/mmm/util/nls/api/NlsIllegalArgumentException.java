/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link NlsIllegalArgumentException} is analog to an {@link IllegalArgumentException} but with true native
 * language support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class NlsIllegalArgumentException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -1537683835966488723L;

  /**
   * The constructor.
   * 
   * @param value is the illegal argument-value. May be <code>null</code>.
   */
  public NlsIllegalArgumentException(Object value) {

    super(NlsBundleUtilCore.ERR_ILLEGAL_ARGUMENT, toMap(KEY_VALUE, value));
  }

  /**
   * The constructor.
   * 
   * @param value is the illegal argument-value. May be <code>null</code>.
   * @param name is the name of the argument (name of parameter).
   * @since 2.0.0
   */
  public NlsIllegalArgumentException(Object value, String name) {

    super(NlsBundleUtilCore.ERR_ILLEGAL_ARGUMENT_VALUE, toMap(KEY_VALUE, value, KEY_NAME, name));
  }

  /**
   * The constructor.
   * 
   * @param value is the illegal argument-value. May be <code>null</code>.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public NlsIllegalArgumentException(Object value, Throwable nested) {

    super(nested, NlsBundleUtilCore.ERR_ILLEGAL_ARGUMENT, toMap(KEY_VALUE, value));
  }

  /**
   * The constructor.
   * 
   * @param value is the illegal argument-value. May be <code>null</code>.
   * @param name is the name of the argument (name of parameter).
   * @param nested is the {@link #getCause() cause} of this exception.
   * @since 2.0.0
   */
  public NlsIllegalArgumentException(Object value, String name, Throwable nested) {

    super(nested, NlsBundleUtilCore.ERR_ILLEGAL_ARGUMENT_VALUE, toMap(KEY_VALUE, value, KEY_NAME, name));
  }

}
