/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import java.util.Map;

import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This is the abstract base exception of all failures related to
 * {@link ContentObject content}.
 * 
 * @see net.sf.mmm.data.reflection.api.ContentReflectionException
 * @see net.sf.mmm.data.security.api.SecurityException
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class ContentException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -3877257188725888994L;

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
   *        arguments}.
   */
  public ContentException(Throwable nested, String internationalizedMessage,
      Map<String, Object> arguments) {

    super(nested, internationalizedMessage, arguments);
  }

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   * @param arguments are the
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String)
   *        arguments}.
   */
  public ContentException(String internationalizedMessage, Map<String, Object> arguments) {

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
  public ContentException(String internationalizedMessage) {

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
   */
  public ContentException(Throwable nested, String internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

}
