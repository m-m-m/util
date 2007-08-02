/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import java.io.OutputStream;
import java.io.Writer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * This utility class contains methods that help to work with the StAX API (JSR
 * 173).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class StaxUtil {

  /** the StAX output factory */
  private static final XMLOutputFactory OUTPUT_FACTORY = XMLOutputFactory.newInstance();

  /**
   * Forbidden constructor.
   */
  private StaxUtil() {

    super();
  }

  /**
   * This method creates a stream writer.
   * 
   * @param out is the output stream where the XML will be written to.
   * @return the XML stream writer.
   * @throws XMLStreamException if the creation of the stream writer failed
   *         (StAX not available or misconfigured).
   */
  public static XMLStreamWriter createXmlStreamWriter(OutputStream out) throws XMLStreamException {

    return OUTPUT_FACTORY.createXMLStreamWriter(out);
  }

  /**
   * This method creates a stream writer.
   * 
   * @param writer is the writer where the XML will be written to.
   * @return the XML stream writer.
   * @throws XMLStreamException if the creation of the stream writer failed
   *         (StAX not available or misconfigured).
   */
  public static XMLStreamWriter createXmlStreamWriter(Writer writer) throws XMLStreamException {

    return OUTPUT_FACTORY.createXMLStreamWriter(writer);
  }

  /**
   * This method skips all events until the current element (tag) is closed.<br>
   * You can use this method if you hit an element you want to ignore. Here is a
   * piece of code that shows an example that loops over all child elements of
   * the current open element:
   * 
   * <pre>
   *   ...
   *   while (xmlReader.nextTag() == {@link XMLStreamConstants#START_ELEMENT}) {
   *     String tagname = xmlReader.getLocalName();
   *     if (XML_TAG_FOO.equals(tagname)) {
   *       handleFoo(xmlReader);
   *     } else {
   *       // ignore all other tags
   *       {@link StaxUtil}.skipOpenElement(xmlReader);
   *     }
   *   }
   * </pre>
   * 
   * @param xmlReader is the STaX reader currently pointing at or inside the
   *        element to skip. After the call of this method it will point to the
   *        end-element event of the element to skip. Calling
   *        {@link XMLStreamReader#nextTag()} will then point to start-element
   *        of the next sibling or to end-element of the parent.
   * @throws XMLStreamException if the operation failed.
   */
  public static void skipOpenElement(XMLStreamReader xmlReader) throws XMLStreamException {

    int depth = 1;
    do {
      int eventType = xmlReader.nextTag();
      if (eventType == XMLStreamConstants.START_ELEMENT) {
        depth++;
        // } else if (eventType == XMLStreamConstants.END_ELEMENT) {
      } else {
        depth--;
        if (depth == 0) {
          return;
        }
      }
    } while (true);
  }

  /**
   * This method gets the name for the given <code>eventType</code>.
   * 
   * @see XMLStreamConstants
   * 
   * @param eventType is an event type constant declared in
   *        {@link XMLStreamConstants}.
   * @return the according name.
   */
  public static String getEventTypeName(int eventType) {

    switch (eventType) {
      case XMLStreamConstants.ATTRIBUTE:
        return "ATTRIBUTE";
      case XMLStreamConstants.CDATA:
        return "CDATA";
      case XMLStreamConstants.CHARACTERS:
        return "CHARACTERS";
      case XMLStreamConstants.COMMENT:
        return "COMMENT";
      case XMLStreamConstants.DTD:
        return "DTD";
      case XMLStreamConstants.END_DOCUMENT:
        return "END_DOCUMENT";
      case XMLStreamConstants.END_ELEMENT:
        return "END_ELEMENT";
      case XMLStreamConstants.ENTITY_DECLARATION:
        return "ENTITY_DECLARATION";
      case XMLStreamConstants.ENTITY_REFERENCE:
        return "ENTITY_REFERENCE";
      case XMLStreamConstants.NAMESPACE:
        return "NAMESPACE";
      case XMLStreamConstants.NOTATION_DECLARATION:
        return "NOTATION_DECLARATION";
      case XMLStreamConstants.PROCESSING_INSTRUCTION:
        return "PROCESSING_INSTRUCTION";
      case XMLStreamConstants.SPACE:
        return "SPACE";
      case XMLStreamConstants.START_DOCUMENT:
        return "START_DOCUMENT";
      case XMLStreamConstants.START_ELEMENT:
        return "START_ELEMENT";
    }
    return "UNKNOWN_EVENT_TYPE (" + String.valueOf(eventType) + ")";
  }
}
