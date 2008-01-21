/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl.type;

import junit.framework.TestCase;

import org.junit.Test;
import org.w3c.dom.Element;

import net.sf.mmm.util.value.ValueParseException;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.value.api.ValueManager;

import static org.junit.Assert.*;

/**
 * 
 * This is a test-case for {@link XmlValueManager}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public class XmlValueManagerTest {

  /**
   * The constructor.
   */
  public XmlValueManagerTest() {

    super();
  }

  /**
   * This method does the test.
   * 
   * @throws XmlException on xml problem.
   * @throws ValueParseException on parsing problem.
   */
  @Test
  public void test() throws XmlException, ValueParseException {

    /*
     * XMLStreamWriter xmlSerializer =
     * StaxUtil.createXmlStreamWriter(System.out);
     * xmlSerializer.writeStartDocument(); toXml(xmlSerializer);
     * xmlSerializer.writeEndDocument(); xmlSerializer.close();
     */
    ValueManager<Element> manager = new XmlValueManager();
    assertEquals(XmlValueManager.VALUE_NAME, manager.getName());
    assertEquals(Element.class, manager.getValueType());
    // Document doc = DomUtil.createDocument();
    // XmlWriter domWriter = new DomXmlWriter(doc);
    // toXml(domWriter);
    // Element rootElement = doc.getDocumentElement();
    // String xmlString = manager.toString(rootElement);

    // Element root2Element = manager.parse(xmlString);
    // String xmlString2 = manager.toString(root2Element);
    // assertEquals(xmlString, xmlString2);
    // StringWriter sw = new StringWriter();
    // XmlWriter outSerializer = new OutputXmlWriter(sw, null, "UTF-8");
    // toXml(outSerializer);
  }

}
