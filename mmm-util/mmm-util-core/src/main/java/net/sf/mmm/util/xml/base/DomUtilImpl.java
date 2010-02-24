/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.events.ProcessingInstruction;
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

import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.BasicUtil;
import net.sf.mmm.util.lang.api.CharIterator;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.BasicUtilImpl;
import net.sf.mmm.util.lang.base.SpaceNormalizingCharIterator;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.xml.api.DomUtil;
import net.sf.mmm.util.xml.api.XmlCompareMode;
import net.sf.mmm.util.xml.api.XmlException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This utility class contains methods that help to deal with the
 * {@link org.w3c.dom.Node DOM} API.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public final class DomUtilImpl extends AbstractLoggable implements DomUtil {

  /** @see #getInstance() */
  private static DomUtil instance;

  /** the document builder factory used to read and parse XML */
  private DocumentBuilderFactory documentBuilderFactory;

  /** the transformer factory used to transform or write XML */
  private TransformerFactory transformerFactory;

  /** @see #getBasicUtil() */
  private BasicUtil basicUtil;

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
   * This method gets the {@link BasicUtil}.
   * 
   * @return the {@link BasicUtil} to use.
   */
  protected BasicUtil getBasicUtil() {

    return this.basicUtil;
  }

  /**
   * This method sets the {@link #getBasicUtil() BasicUtil}.
   * 
   * @param basicUtil is the {@link BasicUtil} to set
   */
  @Resource
  public void setBasicUtil(BasicUtil basicUtil) {

    getInitializationState().requireNotInitilized();
    this.basicUtil = basicUtil;
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
          util.initialize();
          instance = util;
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.documentBuilderFactory == null) {
      try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        this.documentBuilderFactory = factory;
      } catch (FactoryConfigurationError e) {
        throw new NlsIllegalStateException(e);
      }
    }
    if (this.transformerFactory == null) {
      try {
        TransformerFactory factory = TransformerFactory.newInstance();
        this.transformerFactory = factory;
      } catch (TransformerFactoryConfigurationError e) {
        throw new NlsIllegalStateException(e);
      }
    }
    if (this.basicUtil == null) {
      this.basicUtil = BasicUtilImpl.getInstance();
    }
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
   * {@inheritDoc}
   */
  public Element getFirstElement(NodeList nodeList) {

    return getFirstElement(nodeList, null);
  }

  /**
   * {@inheritDoc}
   */
  public Element getFirstElement(NodeList nodeList, String tagName) {

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
   * {@inheritDoc}
   */
  public Element getFirstChildElement(Element element, String tagName) {

    return getFirstElement(element.getChildNodes(), tagName);
  }

  /**
   * {@inheritDoc}
   */
  public Element requireFirstChildElement(Element element, String tagName)
      throws IllegalArgumentException {

    Element result = getFirstChildElement(element, tagName);
    if (result == null) {
      // TODO: NLS
      throw new IllegalArgumentException("Missing element '" + tagName + "' in element '"
          + element.getTagName() + "'!");
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public boolean getAttributeAsBoolean(Element element, String attribute, boolean defaultValue)
      throws IllegalArgumentException {

    boolean result = defaultValue;
    if (element.hasAttribute(attribute)) {
      String flag = element.getAttribute(attribute);
      if (StringUtil.TRUE.equalsIgnoreCase(flag)) {
        result = true;
      } else if (StringUtil.FALSE.equalsIgnoreCase(flag)) {
        result = false;
      } else {
        throw new NlsParseException(flag, element.getTagName() + "@" + attribute, boolean.class);
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public String getNodeText(Node node) {

    return getNodeText(node, 0);
  }

  /**
   * {@inheritDoc}
   */
  public String getNodeText(Node node, int depth) {

    StringBuffer buffer = new StringBuffer();
    getNodeText(node, buffer, depth);
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  public void getNodeText(Node node, Appendable buffer, int depth) {

    try {
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
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * This method removes all {@link Node#getChildNodes() child nodes} of the
   * given node.
   * 
   * @param node is the node to clean of children.
   */
  public void removeChildren(Element node) {

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
      throw new XmlInvalidException(e);
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
      throw new XmlInvalidException(e);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
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
      throw new XmlInvalidException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getLocalName(Node node) {

    String localName = node.getLocalName();
    if (localName == null) {
      localName = node.getNodeName();
    }
    return localName;
  }

  /**
   * This method determines if the given nodes have the same
   * {@link #getLocalName(Node) name} and {@link Node#getNamespaceURI()
   * namespace}.
   * 
   * @param node1 is the first node.
   * @param node2 is the second node.
   * @return <code>true</code> if both nodes have equal
   *         {@link #getLocalName(Node) name} and {@link Node#getNamespaceURI()
   *         namespace}.
   */
  private boolean isEqualName(Node node1, Node node2) {

    if (!this.basicUtil.isEqual(node1.getNamespaceURI(), node2.getNamespaceURI())) {
      return false;
    }
    if (!this.basicUtil.isEqual(getLocalName(node1), getLocalName(node2))) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEqual(Node node1, Node node2, XmlCompareMode mode) {

    if (node1 == null) {
      return (node2 == null);
    } else {
      if (node2 == null) {
        return false;
      }
    }

    short type1 = node1.getNodeType();
    short type2 = node2.getNodeType();
    if (type1 != type2) {
      boolean accept = false;
      if (mode.isJoinText() && mode.isJoinCData()) {
        if (((type1 == Node.TEXT_NODE) && (type2 == Node.CDATA_SECTION_NODE))
            || ((type1 == Node.CDATA_SECTION_NODE) && (type2 == Node.TEXT_NODE))) {
          accept = true;
        }
      }
      if (!accept) {
        return false;
      }
    }
    if (type1 == Node.ELEMENT_NODE) {
      return isEqual((Element) node1, (Element) node2, mode);
    } else if (type1 == Node.ATTRIBUTE_NODE) {
      if (!isEqualName(node1, node2)) {
        return false;
      }
      if (!this.basicUtil.isEqual(node1.getNodeValue(), node2.getNodeValue())) {
        return false;
      }
      return true;
    } else if ((type1 == Node.COMMENT_NODE) && !mode.isCheckComments()) {
      return true;
    } else if ((type1 == Node.TEXT_NODE) || (type1 == Node.CDATA_SECTION_NODE)
        || (type1 == Node.COMMENT_NODE)) {
      CharIterator charIterator1 = new NodeValueCharIterator(node1);
      CharIterator charIterator2 = new NodeValueCharIterator(node2);
      return isEqual(charIterator1, charIterator2, mode);
    } else if (type1 == Node.DOCUMENT_NODE) {
      Document doc1 = (Document) node1;
      Document doc2 = (Document) node2;
      return isEqual(doc1.getDocumentElement(), doc2.getDocumentElement(), mode);
    } else if (type1 == Node.DOCUMENT_FRAGMENT_NODE) {
      // this should actually never happen...
      return isEqual(node1.getChildNodes(), node1.getChildNodes(), mode);
    } else if (type1 == Node.ENTITY_NODE) {
      Entity entity1 = (Entity) node1;
      Entity entity2 = (Entity) node2;
      if (!this.basicUtil.isEqual(entity1.getNotationName(), entity2.getNotationName())) {
        return false;
      }
      return true;
    } else if (type1 == Node.PROCESSING_INSTRUCTION_NODE) {
      ProcessingInstruction pi1 = (ProcessingInstruction) node1;
      ProcessingInstruction pi2 = (ProcessingInstruction) node2;
      if (!this.basicUtil.isEqual(pi1.getTarget(), pi2.getTarget())) {
        return false;
      }
      if (!this.basicUtil.isEqual(pi1.getData(), pi2.getData())) {
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * This method determines if the given {@link CharSequence}s are equal.
   * 
   * @see #isEqual(Node, Node, XmlCompareMode)
   * 
   * @param charIterator1 is the first {@link CharIterator}.
   * @param charIterator2 is the second {@link CharIterator}.
   * @param mode is the mode of comparison.
   * @return <code>true</code> if equal, <code>false</code> otherwise.
   */
  protected boolean isEqual(CharIterator charIterator1, CharIterator charIterator2,
      XmlCompareMode mode) {

    CharIterator c1, c2;
    if (mode.isNormalizeSpaces()) {
      c1 = new SpaceNormalizingCharIterator(charIterator1);
      c2 = new SpaceNormalizingCharIterator(charIterator2);
    } else {
      c1 = charIterator1;
      c2 = charIterator2;
    }
    return this.basicUtil.compare(c1, c2);
  }

  /**
   * This method determines if the given {@link Element elements} are equal.
   * 
   * @see #isEqual(Node, Node, XmlCompareMode)
   * 
   * @param element1 is the first {@link Element}.
   * @param element2 is the second {@link Element}.
   * @param mode is the mode of comparison.
   * @return <code>true</code> if equal, <code>false</code> otherwise.
   */
  protected boolean isEqual(Element element1, Element element2, XmlCompareMode mode) {

    if (!isEqualName(element1, element2)) {
      return false;
    }
    // compare attributes
    NamedNodeMap attributes1 = element1.getAttributes();
    NamedNodeMap attributes2 = element2.getAttributes();
    int length = attributes1.getLength();
    if (attributes2.getLength() != length) {
      return false;
    }
    for (int i = 0; i < length; i++) {
      Node attr1 = attributes1.item(i);
      String namespaceUri = attr1.getNamespaceURI();
      String name = getLocalName(attr1);
      Node attr2;
      if (namespaceUri == null) {
        attr2 = attributes2.getNamedItem(name);
      } else {
        attr2 = attributes2.getNamedItemNS(namespaceUri, name);
      }
      if (attr2 == null) {
        return false;
      }
      if (!attr1.getNodeValue().equals(attr2.getNodeValue())) {
        return false;
      }
    }
    if (!isEqual(element1.getChildNodes(), element2.getChildNodes(), mode)) {
      return false;
    }
    return true;
  }

  /**
   * This method determines if the given {@link NodeList}s are equal.
   * 
   * @param nodeList1 is the first {@link NodeList}.
   * @param nodeList2 is the second {@link NodeList}.
   * @param mode is the mode of comparison.
   * @return <code>true</code> if equal, <code>false</code> otherwise.
   */
  protected boolean isEqual(NodeList nodeList1, NodeList nodeList2, XmlCompareMode mode) {

    // compare child nodes
    Iterator<Node> nodeIterator1 = new JoiningNodeIterator(nodeList1, mode);
    Iterator<Node> nodeIterator2 = new JoiningNodeIterator(nodeList2, mode);
    while (nodeIterator1.hasNext()) {
      Node child1 = nodeIterator1.next();
      if (!nodeIterator2.hasNext()) {
        return false;
      }
      Node child2 = nodeIterator2.next();
      if ((child1.getNodeType() == Node.DOCUMENT_FRAGMENT_NODE)
          && (child2.getNodeType() == Node.DOCUMENT_FRAGMENT_NODE)) {
        // according to DOM-spec nodes can never return DocumentFragment as
        // child - in our case this indicates that the JoiningNodeIterator has
        // joined CharacterData according to "mode" into a DocumentFragment.
        CharIterator charIterator1 = new NodeValueCharIterator(child1.getChildNodes());
        CharIterator charIterator2 = new NodeValueCharIterator(child2.getChildNodes());
        if (!isEqual(charIterator1, charIterator2, mode)) {
          return false;
        }
      }
      if (!isEqual(child1, child2, mode)) {
        return false;
      }
    }
    if (nodeIterator2.hasNext()) {
      return false;
    }
    return true;
  }

}
