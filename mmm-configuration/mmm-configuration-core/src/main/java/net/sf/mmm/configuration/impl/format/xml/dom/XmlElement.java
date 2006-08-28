/* $ Id: $ */
package net.sf.mmm.configuration.impl.format.xml.dom;

import com.sun.org.apache.xerces.internal.xni.NamespaceContext;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationElement;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.xml.DomUtil;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfigurationIF} interface to
 * adapt an {@link org.w3c.dom.Element xml-element}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlElement extends AbstractConfigurationElement {

    /** the owner document */
    private final XmlDocument document;

    /** the adapted XML element */
    private final Element element;

    /**
     * The constructor.
     * 
     * @param ownerDocument
     *        is the
     *        {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF document}
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
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#initialize()
     *      {@inheritDoc}
     */
    @Override
    public void initialize() {

        super.initialize();
        AbstractConfiguration child;

        // initialize attributes
        NamedNodeMap attributeList = this.element.getAttributes();
        for (int i = 0; i < attributeList.getLength(); i++) {
            Attr xmlAttribute = (Attr) attributeList.item(i);
            if (!NamespaceContext.XMLNS_URI.equals(xmlAttribute.getNamespaceURI())) {
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
     * @see net.sf.mmm.configuration.base.BasicConfiguration#getOwnerDocument()
     *      {@inheritDoc}
     */
    @Override
    protected XmlDocument getOwnerDocument() {

        return this.document;
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getName() {@inheritDoc}
     */
    public String getName() {

        String name = this.element.getLocalName();
        if (name == null) {
            name = this.element.getTagName();
        }
        return name;
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getNamespaceUri()
     *      {@inheritDoc}
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
     * 
     */
    public void disable() {

        String prefix = this.element
                .lookupPrefix(ConfigurationDocumentIF.NAMESPACE_URI_CONFIGURATION);
        if (prefix == null) {
            prefix = ConfigurationDocumentIF.NAMESPACE_PREFIX_CONFIGURATION;
            String uri = this.element.lookupNamespaceURI(prefix);
            if (!ConfigurationDocumentIF.NAMESPACE_URI_CONFIGURATION.equals(uri)) {
                // TODO
                throw new ConfigurationException("Namespace "
                        + ConfigurationDocumentIF.NAMESPACE_URI_CONFIGURATION
                        + " not defined but prefix "
                        + ConfigurationDocumentIF.NAMESPACE_PREFIX_CONFIGURATION
                        + " already in use!");
            }
        }
        String qName = XmlDocument
                .createQualifiedName(ConfigurationDocumentIF.NAME_EXCLUDE, prefix);
        Element disableElement = this.element.getOwnerDocument().createElementNS(
                ConfigurationDocumentIF.NAMESPACE_URI_CONFIGURATION, qName);
        Node parent = this.element.getParentNode();
        if ((parent.getNodeType() == Node.ELEMENT_NODE)
                || (parent.getNodeType() == Node.DOCUMENT_NODE)) {
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
     * @see net.sf.mmm.configuration.base.AbstractConfigurationElement#createChildAttribute(java.lang.String,
     *      java.lang.String) {@inheritDoc}
     */
    @Override
    protected AbstractConfiguration createChildAttribute(String name, String namespaceUri) {

        return new XmlAttribute(getOwnerDocument(), this, this.element, name, namespaceUri);
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfigurationElement#createChildElement(java.lang.String,
     *      java.lang.String) {@inheritDoc}
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
     * @see net.sf.mmm.configuration.base.BasicConfiguration#getPlainString()
     *      {@inheritDoc}
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
     * @see net.sf.mmm.configuration.base.BasicConfiguration#setPlainString(java.lang.String)
     *      {@inheritDoc}
     */
    @Override
    protected void setPlainString(String newValue) {

        this.element.setTextContent(newValue);
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#doDisable()
     *      {@inheritDoc}
     */
    @Override
    protected AbstractConfiguration doDisable() throws ConfigurationException {

        Node parentNode = this.element.getParentNode();
        if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
            String qName = XmlDocument.createQualifiedName(ConfigurationDocumentIF.NAME_EXCLUDE,
                    ConfigurationDocumentIF.NAMESPACE_PREFIX_CONFIGURATION);
            Element exclude = this.element.getOwnerDocument().createElementNS(
                    ConfigurationDocumentIF.NAMESPACE_URI_CONFIGURATION, qName);
            parentNode.replaceChild(exclude, this.element);
            XmlElement newParent = new XmlElement(this.document, getParent(), exclude);
            setParent(newParent);
            return newParent;
        } else {
            // can not remove root node!
            throw new ConfigurationException("Can not remove document root node!");
        }
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#doRemove()
     *      {@inheritDoc}
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
