/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * An {@link XmlException} is thrown if something went wrong with XML serialization or parsing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public abstract class XmlException extends NlsRuntimeException {

  private static final long serialVersionUID = 3257850978257613621L;

  /**
   * The constructor.
   *
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and should be
   *        in English language.
   */
  public XmlException(String internationalizedMessage) {

    super(null, internationalizedMessage);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and should be
   *        in English language.
   */
  public XmlException(Throwable nested, String internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  public XmlException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  public XmlException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

}
