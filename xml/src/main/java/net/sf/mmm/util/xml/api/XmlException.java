/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessage;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;

/**
 * An {@link XmlException} is thrown if something went wrong with XML serialization or parsing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public abstract class XmlException extends RuntimeException implements AttributeReadMessage, AttributeReadMessageCode {

  private static final long serialVersionUID = 3257850978257613621L;

  /**
   * The constructor.
   *
   * @param message - see {@link #getMessage()}.
   * @param cause - see {@link #getCause()}.
   */
  protected XmlException(String message, Throwable cause) {

    super(message, cause);
  }

  /**
   * The constructor.
   *
   * @param message - see {@link #getMessage()}.
   */
  protected XmlException(String message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param cause - see {@link #getCause()}.
   */
  protected XmlException(Throwable cause) {

    super(cause);
  }

}
