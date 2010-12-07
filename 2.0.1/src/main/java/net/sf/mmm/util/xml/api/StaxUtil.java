/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.value.api.ValueException;

/**
 * This is the interface for a collection of utility functions that help to deal
 * with the StAX API (JSR 173).
 * 
 * @see net.sf.mmm.util.xml.base.StaxUtilImpl
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@ComponentSpecification
public interface StaxUtil {

  /**
   * This method creates an {@link XMLStreamReader}.
   * 
   * @see javax.xml.stream.XMLInputFactory#createXMLStreamReader(InputStream)
   * 
   * @param inputStream is the {@link InputStream} to read from.
   * @return the {@link XMLStreamReader}.
   * @throws XmlGenericException if the creation of the stream-reader failed
   *         (StAX not available or misconfigured).
   */
  XMLStreamReader createXmlStreamReader(InputStream inputStream) throws XmlGenericException;

  /**
   * This method creates an {@link XMLEventReader}.
   * 
   * @see javax.xml.stream.XMLInputFactory#createXMLEventReader(InputStream)
   * 
   * @param inputStream is the {@link InputStream} to read from.
   * @return the {@link XMLEventReader}.
   * @throws XmlGenericException if the creation of the event-reader failed
   *         (StAX not available or misconfigured).
   */
  XMLEventReader createXmlEventReader(InputStream inputStream) throws XmlGenericException;

  /**
   * This method creates a {@link XMLStreamWriter}.
   * 
   * @see javax.xml.stream.XMLOutputFactory#createXMLStreamWriter(OutputStream)
   * 
   * @param out is the output stream where the XML will be written to.
   * @return the XML stream writer.
   * @throws XmlGenericException if the creation of the stream-writer failed
   *         (StAX not available or misconfigured).
   */
  XMLStreamWriter createXmlStreamWriter(OutputStream out) throws XmlGenericException;

  /**
   * This method creates a {@link XMLStreamWriter}.
   * 
   * @see javax.xml.stream.XMLOutputFactory#createXMLStreamWriter(Writer)
   * 
   * @param writer is the writer where the XML will be written to.
   * @return the XML stream writer.
   * @throws XmlGenericException if the creation of the stream-writer failed
   *         (StAX not available or misconfigured).
   */
  XMLStreamWriter createXmlStreamWriter(Writer writer) throws XmlGenericException;

  /**
   * This method parses the attribute with the given
   * <code>localAttributeName</code> from the given <code>xmlReader</code> as
   * given by <code>type</code>.
   * 
   * @param <V> is the generic for the <code>type</code>.
   * @param xmlReader is where to read the XML from.
   * @param namespaceUri is the URI representing the namespace of the requested
   *        attribute or <code>null</code> to ignore the namespace.
   * @param localAttributeName is the local name of the requested attribute.
   * @param type is the type the requested attribute should be converted to.
   * @return the requested attribute as the given <code>type</code>.
   * @throws ValueException if the attribute is NOT defined or its value can NOT
   *         be converted to <code>type</code>.
   */
  <V> V parseAttribute(XMLStreamReader xmlReader, String namespaceUri, String localAttributeName,
      Class<V> type) throws ValueException;

  /**
   * This method parses the attribute with the given
   * <code>localAttributeName</code> from the given <code>xmlReader</code> as
   * given by <code>type</code>.
   * 
   * @param <V> is the generic for the <code>type</code>.
   * @param xmlReader is where to read the XML from.
   * @param namespaceUri is the URI representing the namespace of the requested
   *        attribute or <code>null</code> to ignore the namespace.
   * @param localAttributeName is the local name of the requested attribute.
   * @param type is the type the requested attribute should be converted to.
   * @param defaultValue is the default value returned if the requested
   *        attribute is NOT defined. It may be <code>null</code>.
   * @return the requested attribute as the given <code>type</code>.
   * @throws ValueException if the attribute value can NOT be converted to
   *         <code>type</code>.
   */
  <V> V parseAttribute(XMLStreamReader xmlReader, String namespaceUri, String localAttributeName,
      Class<V> type, V defaultValue) throws ValueException;

  /**
   * This method reads the {@link XMLStreamReader#getText() text} at the current
   * position of the given <code>xmlReader</code>.<br>
   * If the <code>xmlReader</code> is {@link XMLStreamReader#getEventType()
   * pointing} to {@link javax.xml.stream.XMLStreamConstants#START_ELEMENT} or
   * {@link javax.xml.stream.XMLStreamConstants#ATTRIBUTE} all
   * {@link javax.xml.stream.XMLStreamConstants#ATTRIBUTE attributes} are
   * {@link XMLStreamReader#next() skipped} before.
   * 
   * For {@link XMLEventReader} use {@link XMLEventReader#getElementText()}.
   * 
   * @param xmlReader is the {@link XMLStreamReader} to read the XML from.
   * @return the {@link XMLStreamReader#getText() text} at the current position
   *         or <code>null</code> if there is no text to read (e.g.
   *         {@link javax.xml.stream.XMLStreamConstants#END_ELEMENT} was hit).
   * @throws XmlGenericException if an
   *         {@link javax.xml.stream.XMLStreamException} was caused by the given
   *         <code>xmlReader</code>.
   */
  String readText(XMLStreamReader xmlReader) throws XmlGenericException;

  /**
   * This method skips all events until the current element (tag) is closed.<br>
   * You can use this method if you hit an element you want to ignore. Here is a
   * piece of code that shows an example that loops over all child elements of
   * the current open element:
   * 
   * <pre>
   *   ...
   *   while (xmlReader.nextTag() == {@link javax.xml.stream.XMLStreamConstants#START_ELEMENT}) {
   *     String tagname = xmlReader.getLocalName();
   *     if (XML_TAG_FOO.equals(tagname)) {
   *       handleFoo(xmlReader);
   *     } else {
   *       // ignore all other tags
   *       staxUtil.{@link #skipOpenElement(XMLStreamReader) skipOpenElement(xmlReader)};
   *     }
   *   }
   * </pre>
   * 
   * @param xmlReader is the STaX reader currently pointing at or inside the
   *        element to skip. After the call of this method it will point to the
   *        end-element event of the element to skip. Calling
   *        {@link XMLStreamReader#nextTag()} will then point to start-element
   *        of the next sibling or to end-element of the parent.
   * @throws XmlGenericException if an
   *         {@link javax.xml.stream.XMLStreamException} was caused by the given
   *         <code>xmlReader</code>.
   */
  void skipOpenElement(XMLStreamReader xmlReader) throws XmlGenericException;

  /**
   * This method skips all events until the current element (tag) is closed.<br>
   * 
   * @see #skipOpenElement(XMLStreamReader)
   * 
   * @param xmlReader is the STaX reader currently pointing at or inside the
   *        element to skip. After the call of this method it will point to the
   *        end-element event of the element to skip. Calling
   *        {@link XMLEventReader#nextEvent()} will then return the event after
   *        the {@link javax.xml.stream.events.EndElement}-Event of the skipped
   *        element.
   * @throws XmlGenericException if an
   *         {@link javax.xml.stream.XMLStreamException} was caused by the given
   *         <code>xmlReader</code>.
   */
  void skipOpenElement(XMLEventReader xmlReader) throws XmlGenericException;

  /**
   * This method skips all events until a
   * {@link javax.xml.stream.events.StartElement},
   * {@link javax.xml.stream.events.EndElement} or
   * {@link javax.xml.stream.events.EndDocument} is
   * {@link XMLEventReader#nextEvent() encountered}. Unlike
   * {@link XMLEventReader#nextTag()} no exception is thrown according to
   * unexpected {@link XMLEvent events} except if
   * {@link XMLEventReader#hasNext() has} no next {@link XMLEvent event}.
   * 
   * @param xmlReader is the {@link XMLEventReader} to read the XML from.
   * @return the according event.
   * @throws XmlGenericException if an
   *         {@link javax.xml.stream.XMLStreamException} was caused by the given
   *         <code>xmlReader</code>.
   */
  XMLEvent nextElement(XMLEventReader xmlReader) throws XmlGenericException;

  /**
   * This method gets the name for the given <code>eventType</code>.
   * 
   * @see javax.xml.stream.XMLStreamConstants
   * 
   * @param eventType is an event type constant declared in
   *        {@link javax.xml.stream.XMLStreamConstants}.
   * @return the according name.
   */
  String getEventTypeName(int eventType);

}
