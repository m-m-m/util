/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.util.xml.api.XmlWriter} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractXmlWriter implements XmlWriter {

  /**
   * The constructor.
   */
  public AbstractXmlWriter() {

    super();
  }

  /**
   * This method creates a qualified name for XML tags or attributes.
   * 
   * @param namespacePrefix
   *        is the prefix used to reference a namespace or <code>null</code>
   *        if the default namespace is used.
   * @param localName
   *        is the local part of the qualified name.
   * @return the qualified name based on the given prefix and local name.
   */
  protected String createQualifiedName(String namespacePrefix, String localName) {

    if (StringUtil.isEmpty(namespacePrefix)) {
      return localName;
    } else {
      return namespacePrefix + ":" + localName;
    }
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeStartElement(java.lang.String)
   */
  public void writeStartElement(String localName) throws XmlException {

    writeStartElement(localName, null, null);
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeStartElement(java.lang.String,
   *      java.lang.String)
   */
  public void writeStartElement(String localName, String namespacePrefix) throws XmlException {

    writeStartElement(localName, namespacePrefix, null);
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeAttribute(java.lang.String,
   *      java.lang.String)
   */
  public void writeAttribute(String localName, String value) throws XmlException {

    writeAttribute(localName, value, null);
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeEndElement(String)
   */
  public void writeEndElement(String localName) throws XmlException {

    writeEndElement(localName, null);
  }

}
