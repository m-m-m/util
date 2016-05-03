/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.path.api;

import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This exception is thrown if any piece of XML could NOT be parsed because it has an illegal syntax.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlParseException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 339859320909087567L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "XmlParse";

  /**
   * The constructor.
   *
   * @param message
   */
  public XmlParseException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param cause
   * @param message
   */
  public XmlParseException(Throwable cause, NlsMessage message) {

    super(cause, message);
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
