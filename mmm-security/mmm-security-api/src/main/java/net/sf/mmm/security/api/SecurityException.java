/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.security.api;

import java.util.Map;

import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This exception is used if something was accessed violating a security
 * constraint.<br>
 * <b>ATTENTION:</b><br/>
 * For regular exceptions it is desirable to give many details to the end-user
 * so the problem can be tracked down precisely. However, in the context of
 * security (especially authentication and authorization) it is recommended to
 * give very limited feedback to the user and log the exception on the server.
 * This does NOT only apply for exceptions of this type but also for similar
 * types such as {@link java.lang.SecurityException}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class SecurityException extends NlsRuntimeException {

  /** UID for serialization */
  private static final long serialVersionUID = 3257285846510940725L;

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   * @param arguments are the
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String)
   *        arguments} to be
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        filled into <code>internationalizedMessage</code>}.
   */
  public SecurityException(String internationalizedMessage, Map<String, Object> arguments) {

    super(internationalizedMessage, arguments);
  }

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   */
  public SecurityException(String internationalizedMessage) {

    super(internationalizedMessage);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   * @param arguments are the
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String)
   *        arguments} to be
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        filled into <code>internationalizedMessage</code>}.
   */
  public SecurityException(Throwable nested, String internationalizedMessage,
      Map<String, Object> arguments) {

    super(nested, internationalizedMessage, arguments);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   */
  public SecurityException(Throwable nested, String internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

}
