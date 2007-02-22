/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import net.sf.mmm.nls.base.NlsException;

/**
 * This exception is thrown if something went wrong with XML serialization or
 * parsing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlException extends NlsException {

  /** uid for serialization */
  private static final long serialVersionUID = 3257850978257613621L;

  /**
   * @see NlsException#NlsException(String, Object[])
   */
  public XmlException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsException#NlsException(Throwable, String, Object[])
   */
  public XmlException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
