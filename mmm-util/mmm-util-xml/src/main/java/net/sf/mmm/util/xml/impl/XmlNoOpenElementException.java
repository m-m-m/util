/* $Id$ */
package net.sf.mmm.util.xml.impl;

import net.sf.mmm.util.xml.NlsResourceBundle;
import net.sf.mmm.util.xml.XmlException;

/**
 * This is the exception thrown if no XML element is
 * {@link net.sf.mmm.util.xml.api.XmlWriter#writeStartElement(String, String, String) open}
 * and an element was
 * {@link net.sf.mmm.util.xml.api.XmlWriter#writeEndElement(String, String) closed}
 * or
 * {@link net.sf.mmm.util.xml.api.XmlWriter#writeAttribute(String, String, String) attribute} /
 * content was written.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlNoOpenElementException extends XmlException {

  /** uid for serialization */
  private static final long serialVersionUID = 4749788432393108108L;

  /**
   * The constructor.
   */
  public XmlNoOpenElementException() {

    super(NlsResourceBundle.ERR_NOT_OPEN);
  }

}
