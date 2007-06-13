/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import java.io.Writer;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.mmm.util.io.EscapeWriter;
import net.sf.mmm.util.xml.api.XmlWriter;

/**
 * This utility class contains methods that held to deal with XML.
 * 
 * @see net.sf.mmm.util.xml.DomUtil
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class XmlWriterUtil {

  /** the xml entity for an ampersand sign (&) */
  public static final String ENTITY_AMPERSAND = "&amp;";

  /** the xml entity for a less sign (<) */
  public static final String ENTITY_LESS = "&lt;";

  /** the xml entity for a greater sign (>) */
  public static final String ENTITY_GREATER = "&gt;";

  /** the xml entity for a quote sign (") */
  public static final String ENTITY_QUOTE = "&quot;";

  /** table with escapes for xml text */
  private static final String[] CHAR_ESCAPE_TABLE_TEXT;

  /** table with escapes for xml attributes */
  private static final String[] CHAR_ESCAPE_TABLE_ATTRIBUTE;

  static {
    int arraySize = StrictMath.max(StrictMath.max(StrictMath.max('<', '>'), '&'), '"') + 1;
    CHAR_ESCAPE_TABLE_TEXT = new String[arraySize];
    CHAR_ESCAPE_TABLE_ATTRIBUTE = new String[arraySize];
    // would not be necessary for sun JVM, but you never know...
    for (int i = 0; i < arraySize; i++) {
      CHAR_ESCAPE_TABLE_TEXT[i] = null;
      CHAR_ESCAPE_TABLE_ATTRIBUTE[i] = null;
    }
    CHAR_ESCAPE_TABLE_TEXT['&'] = ENTITY_AMPERSAND;
    CHAR_ESCAPE_TABLE_ATTRIBUTE['&'] = ENTITY_AMPERSAND;
    CHAR_ESCAPE_TABLE_TEXT['<'] = ENTITY_LESS;
    CHAR_ESCAPE_TABLE_ATTRIBUTE['<'] = ENTITY_LESS;
    CHAR_ESCAPE_TABLE_TEXT['>'] = ENTITY_GREATER;
    CHAR_ESCAPE_TABLE_ATTRIBUTE['>'] = ENTITY_GREATER;
    CHAR_ESCAPE_TABLE_ATTRIBUTE['"'] = ENTITY_QUOTE;
  }

  /**
   * Forbidden constructor.
   */
  private XmlWriterUtil() {

    super();
  }

  /**
   * This method creates a wrapper (facade) on the given writer that escapes the
   * characters reserved in XML attributes (CDATA).<br>
   * E.g. the character '"' will automatically written as {@link #ENTITY_QUOTE}
   * by the returned writer.
   * 
   * @param plainWriter
   *        is the writer to wrap.
   * @return a new writer that behaves like the given writer except that
   *         reserved XML characters are replaced by their equivalent entity.
   */
  public static Writer createXmlAttributeWriter(Writer plainWriter) {

    return new EscapeWriter(CHAR_ESCAPE_TABLE_ATTRIBUTE, plainWriter);
  }

  /**
   * This method creates a wrapper (facade) on the given writer that escapes the
   * characters reserved in XML text (PCDATA).<br>
   * E.g. the character '&' will automatically written as
   * {@link #ENTITY_AMPERSAND} by the returned writer.
   * 
   * @param plainWriter
   *        is the writer to wrap.
   * @return a new writer that behaves like the given writer except that
   *         reserved XML characters are replaced by their equivalent entity.
   */
  public static Writer createXmlTextWriter(Writer plainWriter) {

    return new EscapeWriter(CHAR_ESCAPE_TABLE_TEXT, plainWriter);
  }


  /**
   * This method adapts from DOM to {@link XmlWriter}. It serializes the given
   * DOM node to the given XML xmlWriter.
   * 
   * @see net.sf.mmm.util.xml.api.XmlSerializer
   * 
   * @param xmlWriter is the receiver of the serialized XML data.
   * @param node is the XML node to serialize.
   * @throws XmlException if the xml serialization failed.
   */
  public static void toXml(XmlWriter xmlWriter, Node node) throws XmlException {

    if (node == null) {
      new InternalError().printStackTrace();
      return;
    }
    if (node.getNodeType() == Node.ELEMENT_NODE) {
      Element element = (Element) node;
      String prefix = element.getPrefix();
      if (prefix == null) {
        xmlWriter.writeStartElement(element.getTagName());
      } else {
        xmlWriter.writeStartElement(prefix, element.getLocalName(), element.getNamespaceURI());
      }
      NamedNodeMap attributeList = element.getAttributes();
      int length = attributeList.getLength();
      for (int i = 0; i < length; i++) {
        toXml(xmlWriter, attributeList.item(i));
      }
      NodeList childList = element.getChildNodes();
      length = childList.getLength();
      for (int i = 0; i < length; i++) {
        toXml(xmlWriter, childList.item(i));
      }
      if (prefix == null) {
        xmlWriter.writeEndElement(element.getTagName());
      } else {
        xmlWriter.writeEndElement(prefix, element.getLocalName());
      }
    } else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
      Attr attribute = (Attr) node;
      String prefix = attribute.getPrefix();
      if (prefix == null) {
        xmlWriter.writeAttribute(attribute.getName(), attribute.getValue());
      } else {
        if (prefix.equals(XmlUtil.NAMESPACE_PREFIX_XMLNS)) {
          xmlWriter.writeNamespaceDeclaration(attribute.getLocalName(), attribute.getValue());
        } else {
          xmlWriter.writeAttribute(attribute.getLocalName(), attribute.getValue(), prefix);
        }
      }
    } else if (node.getNodeType() == Node.TEXT_NODE) {
      xmlWriter.writeCharacters(node.getTextContent());
    } else if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
      xmlWriter.writeCData(node.getTextContent());
    } else if (node.getNodeType() == Node.COMMENT_NODE) {
      Comment comment = (Comment) node;
      xmlWriter.writeComment(comment.getData());
    } else if (node.getNodeType() == Node.DOCUMENT_NODE) {
      Document document = (Document) node;
      toXml(xmlWriter, document.getDocumentElement());
    }
  }
  
}
