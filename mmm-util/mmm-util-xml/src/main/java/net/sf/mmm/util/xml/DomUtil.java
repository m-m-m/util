/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.sf.mmm.util.BasicUtil;
import net.sf.mmm.util.xml.XmlUtil;
import net.sf.mmm.util.xml.api.XmlWriter;

/**
 * This utility class contains methods that help to deal with the
 * {@link org.w3c.dom.Node DOM} API.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class DomUtil {

  /** the document builder factory used to read and parse xml */
  private static final DocumentBuilderFactory BUILDER_FACTORY;

  /** the transformer factory used to transform or write xml */
  private static final TransformerFactory TRANSFORMER_FACTORY;

  static {
    try {

      BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
      BUILDER_FACTORY.setNamespaceAware(true);
      BUILDER_FACTORY.setXIncludeAware(true);
      TRANSFORMER_FACTORY = TransformerFactory.newInstance();

    } catch (FactoryConfigurationError e) {
      throw new InternalError(e.getMessage());
    } catch (TransformerFactoryConfigurationError e) {
      throw new InternalError(e.getMessage());
    }

  }

  /**
   * Forbidden constructor.
   */
  private DomUtil() {

    super();
  }

  /**
   * This method creates a new document builder.
   * 
   * @return the new document builder instance.
   */
  private static DocumentBuilder createDocumentBuilder() {

    try {
      return BUILDER_FACTORY.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new IllegalStateException("XML Parser misconfigured!"
          + " Propably your JVM does not support the required JAXP version!", e);
    }
  }

  /**
   * This method creates a new transformer.
   * 
   * @param indent -
   *        <code>true</code> if the XML should be indented (automatically add
   *        linebreaks before opening tags), <code>false</code> otherwise.
   * @return the new transformer.
   */
  private static Transformer createTransformer(boolean indent) {

    try {
      Transformer result = TRANSFORMER_FACTORY.newTransformer();
      if (indent) {
        result.setOutputProperty(OutputKeys.INDENT, "yes");
      }
      return result;
    } catch (TransformerConfigurationException e) {
      throw new IllegalStateException("XML Transformer misconfigured!"
          + " Propably your JVM does not support the required JAXP version!", e);
    }
  }

  /**
   * This method gets the first element of the given node-list.
   * 
   * @see #getFirstElement(NodeList, String)
   * 
   * @param nodeList
   *        is the node-list (potentially) containing the requested element.
   * @return the first element in the node-list or <code>null</code>, if the
   *         node-list contains NO element.
   */
  public static Element getFirstElement(NodeList nodeList) {

    return getFirstElement(nodeList, null);
  }

  /**
   * This method gets the first element of the given node-list with the
   * specified tag-name.
   * 
   * @param nodeList
   *        is the node-list (potentially) containing the requested element.
   * @param tagName
   *        is the {@link Element#getTagName() tag-name} of the requested
   *        element or <code>null</code> if any element is acceptable.
   * @return the first element in the node-list with the given tag-name (or at
   *         all if tag-name is <code>null</code>). If no such element exists
   *         in the node-list, <code>null</code> is returned.
   */
  public static Element getFirstElement(NodeList nodeList, String tagName) {

    int len = nodeList.getLength();
    for (int i = 0; i < len; i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        if ((tagName == null) || (tagName.equals(element.getTagName()))) {
          return element;
        }
      }
    }
    return null;
  }

  /**
   * This method gets the text of the given node excluding the text of child
   * elements (depth=0).
   * 
   * @see DomUtil#getNodeText(Node, StringBuffer, int)
   * 
   * @param node
   *        is the xml node containing the text.
   * @return the text of the node.
   */
  public static String getNodeText(Node node) {

    return getNodeText(node, 0);
  }

  /**
   * This method gets the text of the given node as string.
   * 
   * @see DomUtil#getNodeText(Node, StringBuffer, int)
   * 
   * @param node
   *        is the xml node containing the text.
   * @param depth
   *        specifies how deep to step into child elements. If less than 1, the
   *        text of child elements is ignored.
   * @return the text of the node including child nodes down to the specified
   *         depth.
   */
  public static String getNodeText(Node node, int depth) {

    StringBuffer buffer = new StringBuffer();
    getNodeText(node, buffer, depth);
    return buffer.toString();
  }

  /**
   * This method appends the text of the given node to the string buffer. Text
   * means the plain characters between the opening and the closing tag of the
   * element including the text of CDATA sections. The text of child elements is
   * only appended according to the given depth. If the depth is less than 1,
   * child elements are ignored, if equal to 1, the text of child elements is
   * included without their child elements. For an infinte depth use
   * <code>Integer.MAX_VALUE</code>. E.g. for the a element <code>a</code>
   * in <br>
   * <code>&lt;a&gt;123&lt;b/&gt;4&lt;c&gt;5&lt;d&gt;6&lt;/d&gt;&lt;/c&gt;
   * &lt;![CDATA[7]]&gt;8&lt;/a&gt;</code>
   * <br>
   * the call of <code>getNodeText(a, buffer, depth)</code> will append the
   * following text to the buffer according to the given depth.
   * <ol start="0">
   * <li>"123478"</li>
   * <li>"1234578"</li>
   * <li>"12345678"</li>
   * </ol>
   * 
   * @param node
   *        is the xml node containing the text.
   * @param buffer
   *        is the string buffer where to append the text.
   * @param depth
   *        specifies how deep to step into child elements. If less than 1, the
   *        text of child elements is ignored.
   */
  public static void getNodeText(Node node, StringBuffer buffer, int depth) {

    NodeList nodeList = node.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node childNode = nodeList.item(i);
      if ((childNode.getNodeType() == Node.TEXT_NODE)
          || (childNode.getNodeType() == Node.CDATA_SECTION_NODE)) {

        buffer.append(childNode.getNodeValue());
      } else if ((depth > 0) && (childNode.getNodeType() == Node.ELEMENT_NODE)) {
        getNodeText(childNode, buffer, depth - 1);
      }
    }
  }

  /**
   * This method removes all child nodes of the given node.
   * 
   * @param node
   *        is the node to clean of children.
   */
  public static void removeAllChildren(Element node) {

    NodeList children = node.getChildNodes();
    for (int i = (children.getLength() - 1); i >= 0; i--) {
      node.removeChild(children.item(i));
    }
  }

  /**
   * This method creates a new empty XML DOM document.
   * 
   * @return the new document.
   */
  public static Document createDocument() {

    return createDocumentBuilder().newDocument();
  }

  /**
   * This method parses an XML document from a given input stream.
   * 
   * @param inputStream
   *        is the input stream to the XML data.
   * @return the parsed XML DOM document.
   * @throws XmlException
   *         if the input stream produced an IOException or the XML is invalid.
   */
  public static Document parseDocument(InputStream inputStream) throws XmlException {

    try {
      return createDocumentBuilder().parse(inputStream);
    } catch (SAXException e) {
      throw new XmlException(e.getMessage(), e);
    } catch (IOException e) {
      throw new XmlException(e.getMessage(), e);
    }
  }

  /**
   * This method parses an XML document from a given reader.
   * 
   * @param reader
   *        is the reader to the XML data.
   * @return the parsed XML DOM document.
   * @throws XmlException
   *         if the input stream produced an IOException or the XML is invalid.
   */
  public static Document parseDocument(Reader reader) throws XmlException {

    return parseDocument(new InputSource(reader));
  }

  /**
   * This method parses an XML document into an DOM document.
   * 
   * @param inputSource
   *        is the input source to the XML data.
   * @return the parsed XML DOM document.
   * @throws XmlException
   *         if the input stream produced an IOException or the XML is invalid.
   */
  public static Document parseDocument(InputSource inputSource) throws XmlException {

    try {
      return createDocumentBuilder().parse(inputSource);
    } catch (SAXException e) {
      throw new XmlException(e.getMessage(), e);
    } catch (IOException e) {
      throw new XmlException(e.getMessage(), e);
    }
  }

  /**
   * This method writes the XML DOM tree given as node to the given output
   * stream.
   * 
   * @param xmlNode
   *        is the "root" node of the XML tree to write (e.g. the Document or
   *        just a single Element).
   * @param outputStream
   *        is where the serialized XML is written to.
   * @param indent -
   *        <code>true</code> if the XML should be indented (automatically add
   *        linebreaks before opening tags), <code>false</code> otherwise.
   * @throws XmlException
   *         if the output stream produced an IOException or whatever.
   */
  public static void writeXml(Node xmlNode, OutputStream outputStream, boolean indent)
      throws XmlException {

    transformXml(new DOMSource(xmlNode), new StreamResult(outputStream), indent);
  }

  /**
   * This method writes the XML DOM tree given as node to the given writer.
   * 
   * @param xmlNode
   *        is the "root" node of the XML tree to write (e.g. the Document or
   *        just a single Element).
   * @param writer
   *        is where the serialized XML is written to.
   * @param indent -
   *        <code>true</code> if the XML should be indented (automatically add
   *        linebreaks before opening tags), <code>false</code> otherwise.
   * @throws XmlException
   *         if the output stream produced an IOException or whatever.
   */
  public static void writeXml(Node xmlNode, Writer writer, boolean indent) throws XmlException {

    transformXml(new DOMSource(xmlNode), new StreamResult(writer), indent);
  }

  /**
   * This method transforms the given XML source to the given result without
   * structural modifications.
   * 
   * @param source
   *        is a source (e.g. DomSource, etc.)
   * @param result
   *        is a result (e.g. DomResult, StreamResult, etc.)
   * @param indent -
   *        <code>true</code> if the XML should be indented (automatically add
   *        linebreaks before opening tags), <code>false</code> otherwise.
   * @throws XmlException
   *         if the tranformation failed (e.g. I/O error, invalid XML, or
   *         whatever).
   */
  public static void transformXml(Source source, Result result, boolean indent) throws XmlException {

    try {
      createTransformer(indent).transform(source, result);
    } catch (TransformerException e) {
      throw new XmlException(e.getMessage(), e);
    }
  }

  /**
   * This method gets the local name of a given <code>element</code>. This is
   * the name of the element without any namespace prefix.
   * 
   * @param element
   *        the element to get the local name from.
   * @return the local name of the given element.
   */
  public static String getLocalNodeName(Node element) {

    String localName = element.getLocalName();
    if (localName == null) {
      localName = element.getNodeName();
    }
    return localName;
  }

  /**
   * TODO
   * 
   * @param node1
   * @param node2
   * @return <code>true</code> if the XML fragment represented by the given
   *         nodes equals, <code>false</code> otherwise.
   */
  public static boolean isEqual(Node node1, Node node2) {

    if (node1 == null) {
      return (node2 == null);
    } else {
      if (node2 == null) {
        return false;
      }
      short type = node1.getNodeType();
      if (type != node2.getNodeType()) {
        return false;
      }
      if (type == Node.ATTRIBUTE_NODE) {
        if (!BasicUtil.isEqual(node1.getLocalName(), node2.getLocalName())) {
          return false;
        }
        if (!BasicUtil.isEqual(node1.getNamespaceURI(), node2.getNamespaceURI())) {
          return false;
        }
        Attr attr1 = (Attr) node1;
        Attr attr2 = (Attr) node2;
        if (!BasicUtil.isEqual(attr1.getValue(), attr2.getValue())) {
          return false;
        }
        return true;
      } else if (type == Node.COMMENT_NODE) {
        // ignore comments
        return true;
      } else if (type == Node.DOCUMENT_NODE) {
        Document doc1 = (Document) node1;
        Document doc2 = (Document) node2;
        return isEqual(doc1.getDocumentElement(), doc2.getDocumentElement());
      } else if (type == Node.TEXT_NODE) {
        return BasicUtil.isEqual(node1.getNodeValue(), node2.getNodeValue());
      } else if (type == Node.CDATA_SECTION_NODE) {
        return BasicUtil.isEqual(node1.getNodeValue(), node2.getNodeValue());
      } else if (type == Node.ELEMENT_NODE) {
        if (!BasicUtil.isEqual(getLocalNodeName(node1), getLocalNodeName(node2))) {
          return false;
        }
        if (!BasicUtil.isEqual(node1.getNamespaceURI(), node2.getNamespaceURI())) {
          return false;
        }
        // compare attributes
        NamedNodeMap attributes1 = node1.getAttributes();
        NamedNodeMap attributes2 = node2.getAttributes();
        int length = attributes1.getLength();
        if (attributes2.getLength() != length) {
          return false;
        }
        for (int i = 0; i < length; i++) {
          Attr attr1 = (Attr) attributes1.item(i);
          Attr attr2 = (Attr) attributes2.getNamedItemNS(attr1.getNamespaceURI(), attr1
              .getLocalName());
          if (attr2 == null) {
            attr2 = (Attr) attributes2.getNamedItem(attr1.getName());
          }
          if (attr2 == null) {
            return false;
          }
          if (!attr1.getValue().equals(attr2.getValue())) {
            return false;
          }
        }
        // compare child nodes
        NodeList children1 = node1.getChildNodes();
        NodeList children2 = node2.getChildNodes();
        return isEqual(children1, children2);
      }
      // TODO
      return false;
    }
  }

  /**
   * 
   * @param nodeList1
   * @param nodeList2
   * @return <code>true</code> if the XML fragment represented by the given
   *         node-lists equals, <code>false</code> otherwise.
   */
  private static boolean isEqual(NodeList nodeList1, NodeList nodeList2) {

    int length1 = nodeList1.getLength();
    int length2 = nodeList2.getLength();
    int index1 = 0;
    int index2 = 0;
    StringBuffer text1 = new StringBuffer();
    StringBuffer text2 = new StringBuffer();
    while ((index1 < length1) || (index2 < length2)) {

      Node node1 = null;
      Node node2 = null;
      if (index1 < length1) {
        node1 = nodeList1.item(index1);
        index1++;
      }
      if (index2 < length2) {
        node2 = nodeList2.item(index2);
        index2++;
      }
      if (node1 == null) {
        if (!isEqualLast(node2, text2)) {
          return false;
        }
      } else if (node2 == null) {
        if (!isEqualLast(node1, text1)) {
          return false;
        }
      } else {
        if (isEqual(node1, node2)) {
          if (!text1.toString().equals(text2.toString())) {
            return false;
          }
          text1.setLength(0);
          text2.setLength(0);
        } else {
          // mmhm, nodes may NOT be normalized...
          if (node1.getNodeType() == Node.COMMENT_NODE) {
            // skip node1
            index2--;
          } else if (node2.getNodeType() == Node.COMMENT_NODE) {
            // skip node2
            index1--;
          } else if (isTextualNode(node1)) {
            int code = equalsTextNode(node1, text1, node2, text2);
            if (code == -1) {
              // skip node1
              index2--;
            } else if (code == 0) {
              return false;
            }
          } else if (isTextualNode(node2)) {
            int code = equalsTextNode(node2, text2, node1, text1);
            if (code == -1) {
              // skip node2
              index1--;
            } else if (code == 0) {
              return false;
            }
          } else {
            return false;
          }
        }
      }
    }
    if (text1.toString().equals(text2.toString())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * @param textNode1
   * @param sb1
   * @param node2
   * @param sb2
   * @return -1 falls textNode1 ?bersprungen werden sollte, 0 falls keine
   *         ?bereinstimmung festgestellt wurde, 1 normal weitermachen.
   */
  private static int equalsTextNode(Node textNode1, StringBuffer sb1, Node node2, StringBuffer sb2) {

    sb1.append(textNode1.getNodeValue());
    if (isTextualNode(node2)) {
      sb2.append(node2.getNodeValue());
      return 1;
    } else {
      if (sb1.toString().trim().length() == 0) {
        // skip node1
        return -1;
      } else if (sb1.toString().equals(sb2.toString())) {
        sb1.setLength(0);
        sb2.setLength(0);
        // skip node1
        return -1;
      } else {
        return 0;
      }
    }
  }

  /**
   * 
   * @param node
   * @param sb
   * @return
   */
  private static boolean isEqualLast(Node node, StringBuffer sb) {

    if (node.getNodeType() == Node.ELEMENT_NODE) {
      return false;
    }
    if (isTextualNode(node)) {
      sb.append(node.getNodeValue());
    } else {
      // ignore comments, entities, etc.
    }
    return true;
  }

  /**
   * This method determines if the given <code>node</code> represents regular
   * text.
   * 
   * @param node
   *        is the node to check.
   * @return <code>true</code> if the given <code>node</code> is
   *         {@link org.w3c.dom.Text text} or
   *         {@link org.w3c.dom.CDATASection CDATA}.
   */
  public static boolean isTextualNode(Node node) {

    return ((node.getNodeType() == Node.TEXT_NODE) || (node.getNodeType() == Node.CDATA_SECTION_NODE));
  }

  /**
   * This method adapts from DOM to {@link XmlWriter}. It serializes the given
   * DOM node to the given XML xmlWriter.
   * 
   * @see net.sf.mmm.util.xml.api.XmlSerializer
   * 
   * @param xmlWriter
   *        is the receiver of the serialized XML data.
   * @param node
   *        is the XML node to serialize.
   * @throws XmlException
   *         if the xml serilization failed.
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
        // who ever out of this w3c guyz named it Attr...
        toXml(xmlWriter, attributeList.item(i));
      }
      NodeList childList = element.getChildNodes();
      length = childList.getLength();
      for (int i = 0; i < length; i++) {
        // who ever out of this w3c guyz named it Attr...
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
