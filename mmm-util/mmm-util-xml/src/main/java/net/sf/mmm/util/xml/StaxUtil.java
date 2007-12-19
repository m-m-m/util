/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import java.io.OutputStream;
import java.io.Writer;

import javax.annotation.Resource;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Node;

import net.sf.mmm.util.component.AlreadyInitializedException;
import net.sf.mmm.util.value.ValueConverter;
import net.sf.mmm.util.value.ValueException;

/**
 * This utility class contains methods that help to work with the StAX API (JSR
 * 173).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class StaxUtil {

  /** @see #getInstance() */
  private static StaxUtil instance;

  /** the StAX output factory */
  private static final XMLOutputFactory OUTPUT_FACTORY = XMLOutputFactory.newInstance();

  private ValueConverter valueConverter;

  /**
   * Forbidden constructor.
   */
  private StaxUtil() {

    super();
    this.valueConverter = ValueConverter.getInstance();
  }

  /**
   * This method gets the singleton instance of this {@link StaxUtil}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static StaxUtil getInstance() {

    if (instance == null) {
      synchronized (StaxUtil.class) {
        if (instance == null) {
          instance = new StaxUtil();
          instance.setValueConverter(ValueConverter.getInstance());
        }
      }
    }
    return instance;
  }

  /**
   * @return the valueConverter
   */
  protected ValueConverter getValueConverter() {

    return this.valueConverter;
  }

  /**
   * @param valueConverter the valueConverter to set
   */
  @Resource
  public void setValueConverter(ValueConverter valueConverter) {

    if (this.valueConverter != null) {
      throw new AlreadyInitializedException();
    }
    this.valueConverter = valueConverter;
  }

  /**
   * This method creates a stream writer.
   * 
   * @param out is the output stream where the XML will be written to.
   * @return the XML stream writer.
   * @throws XMLStreamException if the creation of the stream writer failed
   *         (StAX not available or misconfigured).
   */
  public XMLStreamWriter createXmlStreamWriter(OutputStream out) throws XMLStreamException {

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
  public XMLStreamWriter createXmlStreamWriter(Writer writer) throws XMLStreamException {

    return OUTPUT_FACTORY.createXMLStreamWriter(writer);
  }

  /**
   * This method parses the attribute with the given
   * <code>localAttributeName</code> from the given <code>xmlReader</code>
   * as given by <code>type</code>.
   * 
   * @param <V> is the generic for the <code>type</code>.
   * @param xmlReader is where to read the XML from.
   * @param localAttributeName is the local name of the requested attribute.
   * @param type is the type the requested attribute should be converted to.
   * @return the requested attribute as the given <code>type</code>.
   * @throws ValueException if the attribute is NOT defined or its value can NOT
   *         be converted to <code>type</code>.
   */
  public <V> V parseAttribute(XMLStreamReader xmlReader, String namespaceUri,
      String localAttributeName, Class<V> type) throws ValueException {

    String value = xmlReader.getAttributeValue(namespaceUri, localAttributeName);
    String valueSource = xmlReader.getLocalName() + "/@" + localAttributeName;
    return this.valueConverter.convertValue(value, valueSource, type);
  }

  /**
   * This method parses the attribute with the given
   * <code>localAttributeName</code> from the given <code>xmlReader</code>
   * as given by <code>type</code>.
   * 
   * @param <V> is the generic for the <code>type</code>.
   * @param xmlReader is where to read the XML from.
   * @param localAttributeName is the local name of the requested attribute.
   * @param type is the type the requested attribute should be converted to.
   * @param defaultValue is the default value returned if the requested
   *        attribute is NOT defined. It may be <code>null</code>.
   * @return the requested attribute as the given <code>type</code>.
   * @throws ValueException if the attribute value can NOT be converted to
   *         <code>type</code>.
   */
  public <V> V parseAttribute(XMLStreamReader xmlReader, String namespaceUri,
      String localAttributeName, Class<V> type, V defaultValue) throws ValueException {

    String value = xmlReader.getAttributeValue(namespaceUri, localAttributeName);
    return this.valueConverter.convertValue(value, localAttributeName, type, defaultValue);
  }

  public String readText(XMLStreamReader xmlReader) throws XMLStreamException {

    int eventType = xmlReader.getEventType();
    if (eventType == XMLStreamConstants.START_ELEMENT) {
      eventType = xmlReader.next();
    }
    while (eventType == XMLStreamConstants.ATTRIBUTE) {
      eventType = xmlReader.next();
    }
    if (eventType == XMLStreamConstants.END_ELEMENT) {
      return "";
    }
    if ((eventType == XMLStreamConstants.CHARACTERS) || (eventType == XMLStreamConstants.CDATA)) {
      return xmlReader.getText();
    }
    throw new IllegalStateException("Not implemented!");
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
  public void skipOpenElement(XMLStreamReader xmlReader) throws XMLStreamException {

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
  public String getEventTypeName(int eventType) {

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

  public void writeToDom(XMLStreamReader xmlReader, Node node) throws XMLStreamException {

    int nodeType = node.getNodeType();
    int eventType = xmlReader.getEventType();

  }

  public void readFromDom(Node node, XMLStreamWriter xmlWriter) throws XMLStreamException {

  }

}
