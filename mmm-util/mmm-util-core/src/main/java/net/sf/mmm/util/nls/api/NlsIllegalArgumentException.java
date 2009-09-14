/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link NlsIllegalArgumentException} is analog to an
 * {@link IllegalArgumentException} but with true native language support.
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
   * @param argument is the argument that is illegal. May be <code>null</code>.
   */
  public NlsIllegalArgumentException(Object argument) {

    super(NlsBundleUtilCore.ERR_ILLEGAL_ARGUMENT, toMap(KEY_OBJECT, argument));
  }

  /**
   * The constructor.
   * 
   * @param argument is the argument that is illegal. May be <code>null</code>.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public NlsIllegalArgumentException(Object argument, Throwable nested) {

    super(nested, NlsBundleUtilCore.ERR_ILLEGAL_ARGUMENT, toMap(KEY_OBJECT, argument));
  }

}
