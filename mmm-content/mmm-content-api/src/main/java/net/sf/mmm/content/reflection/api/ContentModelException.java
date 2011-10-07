/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.api;

import java.util.Map;

import net.sf.mmm.content.api.ContentException;

/**
 * This exception is thrown if something went wrong with the
 * {@link ContentModelService content-model}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentModelException extends ContentException {

  /** UID for serialization. */
  private static final long serialVersionUID = 3544669568611071536L;

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
  public ContentModelException(String internationalizedMessage, Map<String, Object> arguments) {

    super(internationalizedMessage, arguments);
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
   *        arguments}.
   */
  public ContentModelException(Throwable nested, String internationalizedMessage,
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
   */
  public ContentModelException(String internationalizedMessage) {

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
  public ContentModelException(Throwable nested, String internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

}
