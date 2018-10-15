/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import net.sf.mmm.util.exception.NlsBundleUtilExceptionRoot;

/**
 * This exception is thrown if an operation or invocation failed due to security restrictions. This can be any form of
 * "permission denied" as well as a (potential) attack that has been detected. The idea of this exception is that the
 * end-user (or potential attacker) shall not receive further details that could help to complete his attack. A typical
 * problem is e.g. if an attacker can distinguish between "account does not exist" and "invalid credentials" he can
 * easily scan for valid accounts. A large list of valid accounts can be the basis for another account wit a standard
 * password and rotating logins what is hard to detect and prevent. <br>
 *
 * @see SecurityException
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.6.1
 */
public class SecurityErrorUserException extends NlsRuntimeException {

  /** @see #getCode() */
  public static final String CODE = "SecurityError";

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public SecurityErrorUserException() {

    this((Throwable) null);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception. E.g. a {@link SecurityException}.
   */
  public SecurityErrorUserException(Throwable nested) {

    super(nested, createBundle(NlsBundleUtilExceptionRoot.class).errorSecurityRestriction());
  }

  @Override
  public String getCode() {

    return CODE;
  }

  @Override
  public boolean isForUser() {

    return true;
  }

  @Override
  public boolean isTechnical() {

    return false;
  }

}
