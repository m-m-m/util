/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.impl;

import net.sf.mmm.util.xml.NlsResourceBundle;
import net.sf.mmm.util.xml.XmlException;

/**
 * This is the exception thrown if an input or output error occured while
 * streaming XML.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlIOException extends XmlException {

  /** uid for serialization */
  private static final long serialVersionUID = -6092609800350198023L;

  /**
   * The constructor.
   * 
   * @param ioException
   *        is the IO exception that caused this exception.
   */
  public XmlIOException(Throwable ioException) {

    super(ioException, NlsResourceBundle.ERR_IO);
  }

}
