/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl.type;

import java.io.StringWriter;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.mmm.util.xml.DomUtil;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;
import net.sf.mmm.util.xml.impl.DomXmlWriter;
import net.sf.mmm.util.xml.impl.OutputXmlWriter;
import net.sf.mmm.value.api.ValueManager;
import net.sf.mmm.value.api.ValueParseException;

/**
 * 
 * This is a test-case for {@link net.sf.mmm.value.impl.type.XmlValueManager}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public class XmlValueManagerTest extends TestCase {

  /**
   * The constructor.
   */
  public XmlValueManagerTest() {

    super();
  }

  /**
   * This method produces some strange xml for testing.
   * 
   * @param xmlWriter is the serializer where to create the xml.
   * @throws XmlException if the xml is illegal or general error.
   */
  public static void toXml(XmlWriter xmlWriter) throws XmlException {

    String ns3 = "http://URI3";
    // xmlWriter.writeStartElement(ValueManagerIF.XML_TAG_VALUE);
    // xmlWriter.writeAttribute(ValueManagerIF.XML_ATR_VALUE_NAME,
    // XmlValueManager.VALUE_NAME);
    xmlWriter.writeStartElement("tag1");
    xmlWriter.writeNamespaceDeclaration("ns3", ns3);
    xmlWriter.writeAttribute("atr", "val");
    xmlWriter.writeCharacters("Hello World!&<>�������");
    xmlWriter.writeStartElement("tag2", "ns2", "http://URI2");
    xmlWriter.writeAttribute("atr", "val&<>\"");
    xmlWriter.writeCharacters("Hello World!");
    xmlWriter.writeEndElement("tag2", "ns2");
    xmlWriter.writeStartElement("tag3", "ns3");
    xmlWriter.writeAttribute("atr", "val");
    xmlWriter.writeEndElement("tag3", "ns3");
    xmlWriter.writeEndElement("tag1");
    // xmlWriter.writeEndElement(ValueManagerIF.XML_TAG_VALUE);
  }

  /**
   * This method does the test.
   * 
   * @throws XmlException on xml problem.
   * @throws ValueParseException on parsing problem.
   */
  public void test() throws XmlException, ValueParseException {

    /**
     * XMLStreamWriter xmlSerializer =
     * StaxUtil.createXmlStreamWriter(System.out);
     * xmlSerializer.writeStartDocument(); toXml(xmlSerializer);
     * xmlSerializer.writeEndDocument(); xmlSerializer.close();
     */
    ValueManager<Element> manager = new XmlValueManager();
    Document doc = DomUtil.createDocument();
    XmlWriter domWriter = new DomXmlWriter(doc);
    toXml(domWriter);
    System.out.println(manager.getValueClass());
    System.out.println(manager.getName());
    System.out.println("--");
    Element rootElement = doc.getDocumentElement();
    String xmlString = manager.toString(rootElement);
    System.out.println(xmlString);
    System.out.println("--");

    Element root2Element = manager.parse(xmlString);
    String xmlString2 = manager.toString(root2Element);
    System.out.println(xmlString2);
    System.out.println("--");
    // assertEquals(xmlString, xmlString2);
    StringWriter sw = new StringWriter();
    XmlWriter outSerializer = new OutputXmlWriter(sw, null, "UTF-8");
    toXml(outSerializer);
    System.out.println(sw.toString());
    System.out.println("--");
  }

}
