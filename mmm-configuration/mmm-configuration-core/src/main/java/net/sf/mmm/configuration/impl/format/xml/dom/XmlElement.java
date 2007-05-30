/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.impl.format.xml.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationElement;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.xml.DomUtil;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfiguration} interface to adapt
 * an {@link org.w3c.dom.Element xml-element}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlElement extends AbstractConfigurationElement {

  /**
   * XML Information Set REC all namespace attributes (including those named
   * xmlns, whose [prefix] property has no value) have a namespace URI of
   * http://www.w3.org/2000/xmlns/
   */
  public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/".intern();

  /** the owner document */
  private final XmlDocument document;

  /** the adapted XML element */
  private final Element element;

  /**
   * The constructor.
   * 
   * @param ownerDocument
   *        is the
   *        {@link net.sf.mmm.configuration.api.ConfigurationDocument document}
   *        this configuration belongs to.
   * @param parentConfiguration
   *        is the parent configuration.
   * @param xmlElement
   *        is the native XML element to adapt.
   */
  public XmlElement(XmlDocument ownerDocument, AbstractConfiguration parentConfiguration,
      Element xmlElement) {

    super(parentConfiguration);
    this.document = ownerDocument;
    this.element = xmlElement;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize() {

    super.initialize();
    AbstractConfiguration child;

    // initialize attributes
    NamedNodeMap attributeList = this.element.getAttributes();
    for (int i = 0; i < attributeList.getLength(); i++) {
      Attr xmlAttribute = (Attr) attributeList.item(i);
      if (!XMLNS_URI.equals(xmlAttribute.getNamespaceURI())) {
        child = new XmlAttribute(this.document, this, xmlAttribute);
        child.initialize();
        addChild(child);
      }
    }
    // initialize children
    NodeList childList = this.element.getChildNodes();
    for (int i = 0; i < childList.getLength(); i++) {
      Node childNode = childList.item(i);
      if (childNode.getNodeType() == Node.ELEMENT_NODE) {
        Element xmlChild = (Element) childNode;
        child = new XmlElement(this.document, this, xmlChild);
        child.initialize();
        addChild(child);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected XmlDocument getOwnerDocument() {

    return this.document;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    String name = this.element.getLocalName();
    if (name == null) {
      name = this.element.getTagName();
    }
    return name;
  }

  /**
   * {@inheritDoc}
   */
  public String getNamespaceUri() {

    String ns = this.element.getNamespaceURI();
    if (ns == null) {
      return NAMESPACE_URI_NONE;
    }
    return ns;
  }

  /**
   * 
   */
  public void disable() {

    String prefix = this.element.lookupPrefix(ConfigurationDocument.NAMESPACE_URI_CONFIGURATION);
    if (prefix == null) {
      prefix = ConfigurationDocument.NAMESPACE_PREFIX_CONFIGURATION;
      String uri = this.element.lookupNamespaceURI(prefix);
      if (!ConfigurationDocument.NAMESPACE_URI_CONFIGURATION.equals(uri)) {
        // TODO: NLS
        throw new ConfigurationException("Namespace "
            + ConfigurationDocument.NAMESPACE_URI_CONFIGURATION + " not defined but prefix "
            + ConfigurationDocument.NAMESPACE_PREFIX_CONFIGURATION + " already in use!");
      }
    }
    String qName = XmlDocument.createQualifiedName(ConfigurationDocument.NAME_EXCLUDE, prefix);
    Element disableElement = this.element.getOwnerDocument().createElementNS(
        ConfigurationDocument.NAMESPACE_URI_CONFIGURATION, qName);
    Node parent = this.element.getParentNode();
    if ((parent.getNodeType() == Node.ELEMENT_NODE) || (parent.getNodeType() == Node.DOCUMENT_NODE)) {
      parent.replaceChild(disableElement, this.element);
      disableElement.appendChild(this.element);
    } else {
      throw new IllegalStateException();
    }
  }

  /**
   * 
   * @return true
   */
  public boolean isSupportingNamespaces() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractConfiguration createChildAttribute(String name, String namespaceUri) {

    return new XmlAttribute(getOwnerDocument(), this, this.element, name, namespaceUri);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractConfiguration createChildElement(String name, String namespaceUri) {

    Element childElement;
    if (NAMESPACE_URI_NONE.equals(namespaceUri)) {
      childElement = this.element.getOwnerDocument().createElement(name);
    } else {
      String qName = XmlDocument.getQualifiedName(name, this.element, namespaceUri);
      childElement = this.element.getOwnerDocument().createElementNS(namespaceUri, qName);
    }
    if (isAddDefaults()) {
      this.element.appendChild(childElement);
    }
    return new XmlElement(getOwnerDocument(), this, childElement);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getPlainString() {

    String text = DomUtil.getNodeText(this.element);
    if (StringUtil.isEmpty(text)) {
      return null;
    }
    return text;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setPlainString(String newValue) {

    this.element.setTextContent(newValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractConfiguration doDisable() throws ConfigurationException {

    Node parentNode = this.element.getParentNode();
    if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
      String qName = XmlDocument.createQualifiedName(ConfigurationDocument.NAME_EXCLUDE,
          ConfigurationDocument.NAMESPACE_PREFIX_CONFIGURATION);
      Element exclude = this.element.getOwnerDocument().createElementNS(
          ConfigurationDocument.NAMESPACE_URI_CONFIGURATION, qName);
      parentNode.replaceChild(exclude, this.element);
      XmlElement newParent = new XmlElement(this.document, getParent(), exclude);
      setParent(newParent);
      return newParent;
    } else {
      // can not remove root node!
      // TODO: NLS
      throw new ConfigurationException("Can not remove document root node!");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doRemove() throws ConfigurationException {

    Node parentNode = this.element.getParentNode();
    if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
      parentNode.removeChild(this.element);
    } else {
      // can not remove root node!
      throw new ConfigurationException("Can not remove document root node!");
    }
  }

}
