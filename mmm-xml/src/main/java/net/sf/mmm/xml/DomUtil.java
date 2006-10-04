/* $Id$ */
package net.sf.mmm.xml;

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

import net.sf.mmm.xml.api.XmlException;
import net.sf.mmm.xml.api.XmlWriterIF;

/**
 * This utility class contains methods that help to deal with the ugly
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
   * This method parses a XML document into an DOM document.
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
   * This method adapts from DOM to {@link XmlWriterIF}. It serializes the
   * given DOM node to the given XML xmlWriter.
   * 
   * @see net.sf.mmm.xml.api.XmlSerializerIF
   * 
   * @param xmlWriter
   *        is the receiver of the serialized XML data.
   * @param node
   *        is the XML node to serialize.
   * @throws XmlException
   *         if the xml serilization failed.
   */
  public static void toXml(XmlWriterIF xmlWriter, Node node) throws XmlException {

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
