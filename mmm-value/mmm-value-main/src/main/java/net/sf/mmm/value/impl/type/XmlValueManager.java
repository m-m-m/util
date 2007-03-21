/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl.type;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import net.sf.mmm.util.xml.DomUtil;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;
import net.sf.mmm.value.api.ValueManager;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueParseStringException;
import net.sf.mmm.value.base.AbstractValueManager;

/**
 * This is the {@link ValueManager manager} for
 * {@link org.w3c.dom.Document xml} values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlValueManager extends AbstractValueManager<Element> {

  /** the {@link ValueManager#getName() "logical name"} of the managed value */
  public static final String VALUE_NAME = "Xml";

  /** the {@link ValueManager#getValueClass() type} of the managed value */
  private static final Class<Element> VALUE_TYPE = Element.class;

  /**
   * The constructor.
   */
  public XmlValueManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<Element> getValueClass() {

    return VALUE_TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public Element parse(String valueAsString) throws ValueParseException {

    try {
      // TODO: this may cause encoding problems...
      return DomUtil.parseDocument(new InputSource(new StringReader(valueAsString)))
          .getDocumentElement();
    } catch (XmlException e) {
      throw new ValueParseStringException(valueAsString, VALUE_TYPE, VALUE_NAME, e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Element parse(Element valueAsXml) throws ValueParseException {

    checkXml(valueAsXml);
    Element childElement = DomUtil.getFirstElement(valueAsXml.getChildNodes());
    // create deep-copy in new XML document...
    Document copyDocument = DomUtil.createDocument();
    return (Element) copyDocument.importNode(childElement, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(Element value) {

    try {
      Writer writer = new StringWriter();
      DomUtil.writeXml(value, writer, false);
      return writer.toString();
    } catch (XmlException e) {
      return "toString failed for " + value;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEqual(Element value1, Element value2) {

    return DomUtil.isEqual(value1, value2);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void toXmlValue(XmlWriter xmlWriter, Element value) throws XmlException {

    DomUtil.toXml(xmlWriter, value);
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return VALUE_NAME;
  }

}
