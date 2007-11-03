/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.path.api;

import net.sf.mmm.util.nls.NlsRuntimeException;

/**
 * This exception is thrown if any piece of XML could NOT be parsed because it
 * has an illegal syntax.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlParseException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 339859320909087567L;

  /**
   * The constructor.
   * 
   * @param internaitionalizedMessage
   * @param arguments
   */
  public XmlParseException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
    // TODO Auto-generated constructor stub
  }

  /**
   * The constructor.
   * 
   * @param nested
   * @param internaitionalizedMessage
   * @param arguments
   */
  public XmlParseException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
    // TODO Auto-generated constructor stub
  }

}
