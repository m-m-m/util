/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl.type;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import net.sf.mmm.util.value.api.ValueParseException;
import net.sf.mmm.util.xml.api.XmlCompareMode;
import net.sf.mmm.util.xml.api.XmlException;
import net.sf.mmm.util.xml.base.DomUtilImpl;
import net.sf.mmm.value.api.ValueParseStringException;
import net.sf.mmm.value.base.AbstractValueManager;

/**
 * This is the {@link net.sf.mmm.value.api.ValueManager manager} for
 * {@link org.w3c.dom.Document xml} values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlValueManager extends AbstractValueManager<Element> {

  /** The {@link XmlCompareMode}. */
  private static final XmlCompareMode XML_COMPARE_MODE = new XmlCompareMode();

  /**
   * The {@link net.sf.mmm.value.api.ValueManager#getName() "logical name"} of
   * the managed value.
   */
  public static final String VALUE_NAME = "Xml";

  /**
   * The {@link net.sf.mmm.value.api.ValueManager#getValueClass() type} of the
   * managed value.
   */
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
  public String getName() {

    return VALUE_NAME;
  }

  /**
   * {@inheritDoc}
   */
  public Element fromString(String valueAsString) throws ValueParseException {

    try {
      // TODO: this may cause encoding problems...
      return DomUtilImpl.getInstance().parseDocument(
          new InputSource(new StringReader(valueAsString))).getDocumentElement();
    } catch (XmlException e) {
      throw new ValueParseStringException(valueAsString, VALUE_TYPE, VALUE_NAME, e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Element fromXmlContent(XMLStreamReader xmlReader) throws XMLStreamException {

    Document doc = DomUtilImpl.getInstance().createDocument();
    // getStaxUtil().writeToDom(xmlReader, doc);
    return doc.getDocumentElement();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toStringNotNull(Element value) {

    try {
      Writer writer = new StringWriter();
      DomUtilImpl.getInstance().writeXml(value, writer, false);
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

    return DomUtilImpl.getInstance().isEqual(value1, value2, XML_COMPARE_MODE);
  }

}
