/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link NlsIllegalStateException} is analog to an {@link IllegalStateException} but with true native
 * language support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class NlsIllegalStateException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 8417511549993455852L;

  /**
   * The constructor.
   */
  public NlsIllegalStateException() {

    super(NlsBundleUtilCore.ERR_ILLEGAL_STATE);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public NlsIllegalStateException(Throwable nested) {

    super(nested, NlsBundleUtilCore.ERR_ILLEGAL_STATE);
  }

}
