/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.impl;

import net.sf.mmm.util.xml.NlsResourceBundle;
import net.sf.mmm.util.xml.XmlException;

/**
 * This is the exception thrown if the
 * {@link net.sf.mmm.util.xml.api.XmlWriter#writeEndElement(String, String) closing}
 * of an element failed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlCloseElementException extends XmlException {

  /** uid for serialization */
  private static final long serialVersionUID = 5035282653290930026L;

  /**
   * The constructor.
   * 
   * @param openTagName
   *        is the (qualified) tagname used to
   *        {@link net.sf.mmm.util.xml.api.XmlWriter#writeStartElement(String, String, String) open}
   *        the element.
   * @param closeTagName
   *        is the (qualified) tagname used to
   *        {@link net.sf.mmm.util.xml.api.XmlWriter#writeEndElement(String, String) close}
   *        the element that differes.
   */
  public XmlCloseElementException(String openTagName, String closeTagName) {

    super(NlsResourceBundle.ERR_CLOSE_TAGNAME, openTagName, closeTagName);
  }

}
