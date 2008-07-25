/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.lang.base.BasicUtilImpl;
import net.sf.mmm.util.nls.base.NlsIllegalStateException;
import net.sf.mmm.util.xml.api.DomUtil;
import net.sf.mmm.util.xml.api.XmlException;

/**
 * This utility class contains methods that help to deal with the
 * {@link org.w3c.dom.Node DOM} API.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class DomUtilImpl extends AbstractLoggable implements DomUtil {

  /** @see #getInstance() */
  private static DomUtil instance;

  /** the document builder factory used to read and parse xml */
  private DocumentBuilderFactory documentBuilderFactory;

  /** the transformer factory used to transform or write xml */
  private TransformerFactory transformerFactory;

  /**
   * The constructor.
   */
  public DomUtilImpl() {

    super();
  }

  /**
   * This method gets the {@link DocumentBuilderFactory}.
   * 
   * @return the {@link DocumentBuilderFactory} to use.
   */
  protected DocumentBuilderFactory getDocumentBuilderFactory() {

    return this.documentBuilderFactory;
  }

  /**
   * This method sets the {@link #getDocumentBuilderFactory()
   * documentBuilderFactory}.
   * 
   * @param documentBuilderFactory is the documentBuilderFactory to set.
   */
  public void setDocumentBuilderFactory(DocumentBuilderFactory documentBuilderFactory) {

    getInitializationState().requireNotInitilized();
    this.documentBuilderFactory = documentBuilderFactory;
  }

  /**
   * This method gets the {@link TransformerFactory}.
   * 
   * @return the {@link TransformerFactory} to use.
   */
  protected TransformerFactory getTransformerFactory() {

    return this.transformerFactory;
  }

  /**
   * This method sets the {@link #getTransformerFactory() transformerFactory}.
   * 
   * @param transformerFactory is the transformerFactory to set
   */
  public void setTransformerFactory(TransformerFactory transformerFactory) {

    getInitializationState().requireNotInitilized();
    this.transformerFactory = transformerFactory;
  }

  /**
   * This method gets the singleton instance of this {@link DomUtilImpl}.<br>
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
  public static DomUtil getInstance() {

    if (instance == null) {
      synchronized (DomUtilImpl.class) {
        if (instance == null) {
          DomUtilImpl util = new DomUtilImpl();
          try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            util.setDocumentBuilderFactory(documentBuilderFactory);
          } catch (FactoryConfigurationError e) {
            throw new NlsIllegalStateException(e);
          }
          try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            util.setTransformerFactory(transformerFactory);
          } catch (TransformerFactoryConfigurationError e) {
            throw new NlsIllegalStateException(e);
          }
          util.initialize();
          instance = util;
        }
      }
    }
    return instance;
  }

  /**
   * This method creates a new document builder.
   * 
   * @return the new document builder instance.
   */
  private DocumentBuilder createDocumentBuilder() {

    try {
      return this.documentBuilderFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new IllegalStateException("XML Parser misconfigured!"
          + " Probably your JVM does not support the required JAXP version!", e);
    }
  }

  /**
   * This method creates a new transformer.
   * 
   * @param indent - <code>true</code> if the XML should be indented
   *        (automatically add linebreaks before opening tags),
   *        <code>false</code> otherwise.
   * @return the new transformer.
   */
  private Transformer createTransformer(boolean indent) {

    try {
      Transformer result = this.transformerFactory.newTransformer();
      if (indent) {
        result.setOutputProperty(OutputKeys.INDENT, "yes");
      }
      return result;
    } catch (TransformerConfigurationException e) {
      throw new IllegalStateException("XML Transformer misconfigured!"
          + " Probably your JVM does not support the required JAXP version!", e);
    }
  }

  /**
   * This method gets the first element of the given node-list.
   * 
   * @see #getFirstElement(NodeList, String)
   * 
   * @param nodeList is the node-list (potentially) containing the requested
   *        element.
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
   * @param nodeList is the node-list (potentially) containing the requested
   *        element.
   * @param tagName is the {@link Element#getTagName() tag-name} of the
   *        requested element or <code>null</code> if any element is acceptable.
   * @return the first element in the node-list with the given tag-name (or at
   *         all if tag-name is <code>null</code>). If no such element exists in
   *         the node-list, <code>null</code> is returned.
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
   * This method gets the first child-element of the given <code>element</code>
   * with the specified <code>tagName</code>.
   * 
   * @param element is the element (potentially) containing the requested
   *        child-element.
   * @param tagName is the {@link Element#getTagName() tag-name} of the
   *        requested element or <code>null</code> if any element is acceptable.
   * @return the first element in the node-list with the given tag-name (or at
   *         all if tag-name is <code>null</code>). If no such element exists in
   *         the node-list, <code>null</code> is returned.
   */
  public static Element getFirstChildElement(Element element, String tagName) {

    return getFirstElement(element.getChildNodes(), tagName);
  }

  /**
   * This method requires the first child-element of the given
   * <code>element</code> with the specified <code>tagName</code>.
   * 
   * @param element is the element (potentially) containing the requested
   *        child-element.
   * @param tagName is the {@link Element#getTagName() tag-name} of the
   *        requested element or <code>null</code> if any element is acceptable.
   * @return the first element in the node-list with the given tag-name (or at
   *         all if tag-name is <code>null</code>).
   * @throws IllegalArgumentException if the requested child element does NOT
   *         exist.
   */
  public static Element requireFirstChildElement(Element element, String tagName)
      throws IllegalArgumentException {

    Element result = getFirstChildElement(element, tagName);
    if (result == null) {
      throw new IllegalArgumentException("Missing element '" + tagName + "' in element '"
          + element.getTagName() + "'!");
    }
    return result;
  }

  /**
   * This method gets the value of the <code>attribute</code> from the given
   * <code>element</code> as a boolean value.
   * 
   * @param element is the element potentially containing the requested boolean
   *        attribute.
   * @param attribute is the name of the requested attribute.
   * @param defaultValue is the default returned if the attribute is NOT
   *        present.
   * @return the value of the specified <code>attribute</code> or the
   *         <code>defaultValue</code> if the attribute is NOT present.
   * @throws IllegalArgumentException if the value of the specified attribute
   *         does NOT represent a boolean value.
   */
  public static boolean getAttributeAsBoolean(Element element, String attribute,
      boolean defaultValue) throws IllegalArgumentException {

    boolean result = defaultValue;
    if (element.hasAttribute(attribute)) {
      String flag = element.getAttribute(attribute);
      if (Boolean.TRUE.toString().equalsIgnoreCase(flag)) {
        result = true;
      } else if (Boolean.FALSE.toString().equalsIgnoreCase(flag)) {
        result = false;
      } else {
        // TODO: NLS?
        throw new IllegalArgumentException("XML-Attribute " + attribute
            + " must be either 'true' or 'false' but was '" + flag + "'!");
      }
    }
    return result;
  }

  /**
   * This method gets the text of the given node excluding the text of child
   * elements (depth=0).
   * 
   * @see DomUtilImpl#getNodeText(Node, StringBuffer, int)
   * 
   * @param node is the xml node containing the text.
   * @return the text of the node.
   */
  public static String getNodeText(Node node) {

    return getNodeText(node, 0);
  }

  /**
   * This method gets the text of the given node as string.
   * 
   * @see DomUtilImpl#getNodeText(Node, StringBuffer, int)
   * 
   * @param node is the xml node containing the text.
   * @param depth specifies how deep to step into child elements. If less than
   *        1, the text of child elements is ignored.
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
   * included without their child elements. For an infinite depth use
   * <code>Integer.MAX_VALUE</code>. E.g. for the a element <code>a</code> in <br>
   * <code>&lt;a&gt;123&lt;b/&gt;4&lt;c&gt;5&lt;d&gt;6&lt;/d&gt;&lt;/c&gt;
   * &lt;![CDATA[7]]&gt;8&lt;/a&gt;</code> <br>
   * the call of <code>getNodeText(a, buffer, depth)</code> will append the
   * following text to the buffer according to the given depth. <ol start="0">
   * <li>"123478"</li> <li>"1234578"</li> <li>"12345678"</li> </ol>
   * 
   * @param node is the xml node containing the text.
   * @param buffer is the string buffer where to append the text.
   * @param depth specifies how deep to step into child elements. If less than
   *        1, the text of child elements is ignored.
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
   * @param node is the node to clean of children.
   */
  public static void removeAllChildren(Element node) {

    NodeList children = node.getChildNodes();
    for (int i = (children.getLength() - 1); i >= 0; i--) {
      node.removeChild(children.item(i));
    }
  }

  /**
   * {@inheritDoc}
   */
  public Document createDocument() {

    return createDocumentBuilder().newDocument();
  }

  /**
   * {@inheritDoc}
   */
  public Document parseDocument(InputStream inputStream) throws XmlException, IOException {

    try {
      return createDocumentBuilder().parse(inputStream);
    } catch (SAXException e) {
      // TODO: NLS, etc.
      throw new XmlException(e.getMessage(), e);
    } finally {
      inputStream.close();
    }
  }

  /**
   * {@inheritDoc}
   */
  public Document parseDocument(Reader reader) throws XmlException {

    return parseDocument(new InputSource(reader));
  }

  /**
   * {@inheritDoc}
   */
  public Document parseDocument(InputSource inputSource) throws XmlException {

    try {
      return createDocumentBuilder().parse(inputSource);
    } catch (SAXException e) {
      throw new XmlException(e.getMessage(), e);
    } catch (IOException e) {
      throw new XmlException(e.getMessage(), e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void writeXml(Node xmlNode, OutputStream outputStream, boolean indent) throws XmlException {

    transformXml(new DOMSource(xmlNode), new StreamResult(outputStream), indent);
  }

  /**
   * {@inheritDoc}
   */
  public void writeXml(Node xmlNode, Writer writer, boolean indent) throws XmlException {

    transformXml(new DOMSource(xmlNode), new StreamResult(writer), indent);
  }

  /**
   * {@inheritDoc}
   */
  public void transformXml(Source source, Result result, boolean indent) throws XmlException {

    try {
      createTransformer(indent).transform(source, result);
    } catch (TransformerException e) {
      throw new XmlException(e.getMessage(), e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getLocalNodeName(Node element) {

    String localName = element.getLocalName();
    if (localName == null) {
      localName = element.getNodeName();
    }
    return localName;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEqual(Node node1, Node node2) {

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
        if (!BasicUtilImpl.getInstance().isEqual(node1.getLocalName(), node2.getLocalName())) {
          return false;
        }
        if (!BasicUtilImpl.getInstance().isEqual(node1.getNamespaceURI(), node2.getNamespaceURI())) {
          return false;
        }
        Attr attr1 = (Attr) node1;
        Attr attr2 = (Attr) node2;
        if (!BasicUtilImpl.getInstance().isEqual(attr1.getValue(), attr2.getValue())) {
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
        return BasicUtilImpl.getInstance().isEqual(node1.getNodeValue(), node2.getNodeValue());
      } else if (type == Node.CDATA_SECTION_NODE) {
        return BasicUtilImpl.getInstance().isEqual(node1.getNodeValue(), node2.getNodeValue());
      } else if (type == Node.ELEMENT_NODE) {
        if (!BasicUtilImpl.getInstance().isEqual(getLocalNodeName(node1), getLocalNodeName(node2))) {
          return false;
        }
        if (!BasicUtilImpl.getInstance().isEqual(node1.getNamespaceURI(), node2.getNamespaceURI())) {
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
  private boolean isEqual(NodeList nodeList1, NodeList nodeList2) {

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
          // nodes may NOT be normalized...
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
   */
  private int equalsTextNode(Node textNode1, StringBuffer sb1, Node node2, StringBuffer sb2) {

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
  private boolean isEqualLast(Node node, StringBuffer sb) {

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
   * {@inheritDoc}
   */
  public boolean isTextualNode(Node node) {

    return ((node.getNodeType() == Node.TEXT_NODE) || (node.getNodeType() == Node.CDATA_SECTION_NODE));
  }

}
