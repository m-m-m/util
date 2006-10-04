/* $ Id: $ */
package net.sf.mmm.configuration.impl.format.xml.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationAttribute;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfigurationIF} interface to
 * adapt an {@link org.w3c.dom.Attr xml-attribute}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlAttribute extends AbstractConfigurationAttribute {

  /** the owner document */
  private final XmlDocument document;

  /** the XML element owning the attribute */
  private final Element element;

  /** the XML attribute */
  private final Attr attribute;

  /** <code>false</code> if the attribute is not yet assigned */
  private boolean hasValue;

  /**
   * The constructor for an existing DOM attribute.
   * 
   * @param ownerDocument
   *        is the
   *        {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF document}
   *        this configuration belongs to.
   * @param parentConfiguration
   *        is the parent configuration.
   * @param xmlAttribute
   *        is the native XML attribute to adapt.
   */
  public XmlAttribute(XmlDocument ownerDocument, AbstractConfiguration parentConfiguration,
      Attr xmlAttribute) {

    super(parentConfiguration);
    this.document = ownerDocument;
    this.attribute = xmlAttribute;
    this.element = xmlAttribute.getOwnerElement();
    this.hasValue = true;
  }

  /**
   * The constructor for an attribute currently NOT existing in DOM.
   * 
   * @param ownerDocument
   *        is the
   *        {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF document}
   *        this configuration belongs to.
   * @param parentConfiguration
   *        is the parent configuration.
   * @param parentElement
   *        is the XML owner-element of the attribute to create.
   * @param name
   *        is the name of the attribute.
   * @param namespaceUri
   *        is the namespaceUri.
   */
  public XmlAttribute(XmlDocument ownerDocument, AbstractConfiguration parentConfiguration,
      Element parentElement, String name, String namespaceUri) {

    super(parentConfiguration);
    this.document = ownerDocument;
    this.hasValue = false;
    this.element = parentElement;
    String attributeName = name.substring(1);
    if (NAMESPACE_URI_NONE.equals(namespaceUri)) {
      this.attribute = parentElement.getOwnerDocument().createAttribute(attributeName);
    } else {
      String qName = XmlDocument.getQualifiedName(attributeName, this.element, namespaceUri);
      this.attribute = parentElement.getOwnerDocument().createAttributeNS(namespaceUri, qName);

    }
  }

  /**
   * @see net.sf.mmm.configuration.base.BasicConfiguration#getOwnerDocument()
   *      
   */
  @Override
  protected XmlDocument getOwnerDocument() {

    return this.document;
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationIF#getName() 
   */
  public String getName() {

    String name = this.attribute.getLocalName();
    if (name == null) {
      name = this.attribute.getName();
    }
    return ConfigurationIF.NAME_PREFIX_ATTRIBUTE + name;
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationIF#getNamespaceUri()
   *      
   */
  public String getNamespaceUri() {

    String ns = this.attribute.getNamespaceURI();
    if (ns == null) {
      return NAMESPACE_URI_NONE;
    }
    return ns;
  }

  /**
   * @see net.sf.mmm.configuration.base.BasicConfiguration#getPlainString()
   *      
   */
  @Override
  protected String getPlainString() {

    if (this.hasValue) {
      return this.attribute.getValue();
    } else {
      return null;
    }
  }

  /**
   * @see net.sf.mmm.configuration.base.BasicConfiguration#setPlainString(java.lang.String)
   *      
   */
  @Override
  protected void setPlainString(String newValue) {

    this.attribute.setValue(newValue);
    if (!this.hasValue) {
      this.hasValue = true;
      if (this.attribute.getNamespaceURI() == null) {
        this.element.setAttributeNode(this.attribute);
      } else {
        this.element.setAttributeNodeNS(this.attribute);
      }
    }
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doDisable()
   *      
   */
  @Override
  protected AbstractConfiguration doDisable() throws ConfigurationException {

    if (this.hasValue) {
      doRemove();
      Document xmlDoc = this.element.getOwnerDocument();
      String name = this.attribute.getLocalName();
      if (name == null) {
        name = this.attribute.getName();
      }
      String qName = XmlDocument.createQualifiedName(name,
          ConfigurationDocumentIF.NAMESPACE_PREFIX_CONFIGURATION);
      Attr newAttribute = xmlDoc.createAttributeNS(
          ConfigurationDocumentIF.NAMESPACE_URI_CONFIGURATION, qName);
      this.element.setAttributeNodeNS(newAttribute);
      return new XmlAttribute(this.document, getParent(), newAttribute);
    }
    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doRemove()
   *      
   */
  @Override
  protected void doRemove() throws ConfigurationException {

    if (this.hasValue) {
      this.element.removeAttributeNode(this.attribute);
    }
  }

}
