/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

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

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "IllStat";

  /**
   * The constructor.
   */
  public NlsIllegalStateException() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public NlsIllegalStateException(Throwable nested) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorIllegalState());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
