/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.security.api;

import net.sf.mmm.security.NlsBundleSecurityRoot;
import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This exception is used if something was accessed violating a security constraint.<br>
 * <b>ATTENTION:</b><br/>
 * For regular exceptions it is desirable to give many details to the end-user so the problem can be tracked
 * down precisely. However, in the context of security (especially authentication and authorization) it is
 * recommended to give very limited feedback to the user and log the exception on the server. This does NOT
 * only apply for exceptions of this type but also for similar types such as
 * {@link java.lang.SecurityException}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class SecurityException extends NlsRuntimeException {

  /** UID for serialization */
  private static final long serialVersionUID = 3257285846510940725L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected SecurityException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and
   *        should be in English language.
   */
  public SecurityException(String internationalizedMessage) {

    super(internationalizedMessage);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and
   *        should be in English language.
   */
  public SecurityException(Throwable nested, String internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public SecurityException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param cause is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public SecurityException(Throwable cause, NlsMessage message) {

    super(cause, message);
  }

  /**
   * @return the default bundle {@link NlsBundleSecurityRoot}.
   */
  protected static NlsBundleSecurityRoot getBundle() {

    return createBundle(NlsBundleSecurityRoot.class);
  }
}
