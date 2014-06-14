/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.security.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.exception.api.ExceptionTruncation;
import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.lang.api.concern.Security;

/**
 * This exception is thrown if an operation or invocation failed due to security restrictions. This can be any
 * form of "permission denied" as well as a (potential) attack that has been detected. The idea of this
 * exception is that the end-user (or potential attacker) shall not receive further details that could help to
 * complete his attack. A typical problem is e.g. if an attacker can distinguish between
 * "account does not exist" and "invalid credentials" he can easily scan for valid accounts. A large list of
 * valid accounts can be the basis for another account wit a standard password and rotating logins what is
 * hard to detect and prevent.<br/>
 * <b>ATTENTION:</b><br/>
 * When sending security exceptions as response to a remote request you have to ensure
 * {@link net.sf.mmm.util.exception.api.ExceptionTruncation}. All this is handled by
 * {@link net.sf.mmm.util.exception.api.ExceptionUtil#convertForClient(Throwable)}.
 *
 * @see SecurityException
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class SecurityErrorUserException extends NlsRuntimeException implements Security {

  /** @see #getCode() */
  public static final String CODE = "SecurityError";

  /** UID for serialization. */
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

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorSecurityRestriction());
  }

  /**
   * The copy constructor.
   *
   * @see net.sf.mmm.util.exception.api.NlsRuntimeException#NlsRuntimeException(net.sf.mmm.util.exception.api.NlsRuntimeException,
   *      ExceptionTruncation)
   *
   * @param copySource is the exception to copy.
   * @param truncation is the {@link ExceptionTruncation} to configure potential truncations.
   */
  protected SecurityErrorUserException(SecurityErrorUserException copySource, ExceptionTruncation truncation) {

    super(copySource, truncation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SecurityErrorUserException createCopy(ExceptionTruncation truncation) {

    return new SecurityErrorUserException(this, truncation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return CODE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isForUser() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTechnical() {

    // should be logged with details and shall not happen in normal situations.
    return true;
  }

}
