/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link NlsNullPointerException} is analog to an
 * {@link NullPointerException} but with native language support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsNullPointerException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5746393435577207765L;

  /**
   * The constructor.
   * 
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   * 
   * @param argument is the argument that is illegal. May be <code>null</code>.
   */
  public NlsNullPointerException(String argument) {

    super(NlsBundleUtilCore.ERR_ARGUMENT_NULL, argument);
  }

  /**
   * The constructor.
   * 
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   * 
   * @param argument is the argument that is illegal. May be <code>null</code>.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public NlsNullPointerException(String argument, Throwable nested) {

    super(nested, NlsBundleUtilCore.ERR_ARGUMENT_NULL, argument);
  }

}
