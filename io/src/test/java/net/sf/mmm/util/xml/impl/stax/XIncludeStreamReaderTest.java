/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.impl.stax;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;

import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test of {@link XIncludeStreamReaderTest}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class XIncludeStreamReaderTest extends Assert {

  /**
   * Tests {@link XIncludeStreamReader}.
   * 
   * @throws Exception if the test fails.
   */
  @Test
  public void testXInclude() throws Exception {

    XMLInputFactory factory = XMLInputFactory.newInstance();
    DataResource resource = new ClasspathResource(XIncludeStreamReaderTest.class, "test-include.xml", false);
    XIncludeStreamReader reader = new XIncludeStreamReader(factory, resource);

    // <root xmlns:xi="http://www.w3.org/2001/XInclude">
    // <embed1 xmlns:xi="http://www.w3.org/2001/XInclude">
    // <recursive>Hello</recursive>
    // <text>World!</text>
    // </embed1>
    // <default value="42"/>
    // </root>

    assertEquals(XMLStreamConstants.START_DOCUMENT, reader.getEventType());
    assertEquals(XMLStreamConstants.START_ELEMENT, reader.next()); // <root>
    assertEquals("root", reader.getLocalName());
    assertEquals(0, reader.getAttributeCount());
    assertEquals(XMLStreamConstants.CHARACTERS, reader.next());
    assertEquals("\n  ", reader.getText());
    assertEquals(XMLStreamConstants.START_ELEMENT, reader.next()); // <embed1>
    assertEquals("embed1", reader.getLocalName());
    assertEquals(XMLStreamConstants.CHARACTERS, reader.next());
    assertEquals("\n  ", reader.getText());
    assertEquals(XMLStreamConstants.START_ELEMENT, reader.next()); // <recursive>
    assertEquals("recursive", reader.getLocalName());
    assertEquals(XMLStreamConstants.CHARACTERS, reader.next());
    assertEquals("Hello", reader.getText());
    assertEquals(XMLStreamConstants.END_ELEMENT, reader.next()); // </recursive>
    assertEquals(XMLStreamConstants.CHARACTERS, reader.next());
    assertEquals("\n  ", reader.getText());
    assertEquals(XMLStreamConstants.START_ELEMENT, reader.next()); // <text>
    assertEquals("text", reader.getLocalName());
    assertEquals(XMLStreamConstants.CHARACTERS, reader.next());
    assertEquals("World!", reader.getText());
    assertEquals(XMLStreamConstants.END_ELEMENT, reader.next()); // </text>
    assertEquals(XMLStreamConstants.CHARACTERS, reader.next());
    assertEquals("\n", reader.getText());
    assertEquals(XMLStreamConstants.END_ELEMENT, reader.next()); // </embed1>
    assertEquals(XMLStreamConstants.CHARACTERS, reader.next());
    assertEquals("\n  ", reader.getText());
    assertEquals(XMLStreamConstants.CHARACTERS, reader.next());
    assertEquals("\n      ", reader.getText());
    assertEquals(XMLStreamConstants.START_ELEMENT, reader.next()); // <default value="42">
    assertEquals("default", reader.getLocalName());
    assertEquals("value", reader.getAttributeLocalName(0));
    assertEquals("42", reader.getAttributeValue(0));
    assertEquals(XMLStreamConstants.END_ELEMENT, reader.next()); // </default>
    assertEquals(XMLStreamConstants.CHARACTERS, reader.next());
    assertEquals("\n    ", reader.getText());
    assertEquals(XMLStreamConstants.CHARACTERS, reader.next());
    assertEquals("\n", reader.getText());
    assertEquals(XMLStreamConstants.END_ELEMENT, reader.next()); // </root>
    assertEquals(XMLStreamConstants.END_DOCUMENT, reader.next());
    reader.close();
  }
}
