/* $Id$ */
package net.sf.mmm.xml.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import net.sf.mmm.xml.NlsResourceBundle;
import net.sf.mmm.xml.XmlUtil;
import net.sf.mmm.xml.api.XmlException;
import net.sf.mmm.xml.base.AbstractXmlWriter;

/**
 * This is an implementation of the {@link net.sf.mmm.xml.api.XmlSerializerIF}
 * interface that generates the XML as {@link org.w3c.dom.Node DOM} tree. This
 * can be done from scratch or the XML can be appended to an element of an
 * existing DOM tree.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DomXmlWriter extends AbstractXmlWriter {

    /** the XML DOM document */
    private Document document;

    /** the XML DOM element */
    private Element element;

    /** the current XML DOM element */
    public Element currentElement;

    /**
     * The constructor.
     * 
     * @param xmlDocument
     *        is the XML DOM document where to append the XML to serialize.
     */
    public DomXmlWriter(Document xmlDocument) {

        super();
        this.document = xmlDocument;
        this.element = null;
    }

    /**
     * The constructor.
     * 
     * @param xmlElement
     *        is the XML DOM element where to append the XML to serialize.
     */
    public DomXmlWriter(Element xmlElement) {

        super();
        this.element = xmlElement;
        this.currentElement = this.element;
        this.document = this.element.getOwnerDocument();
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeStartElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     * {@inheritDoc}
     */
    public void writeStartElement(String localName, String namespacePrefix, String namespaceUri)
            throws XmlException {

        Element newElement;
        if ((namespacePrefix == null) && (namespaceUri == null)) {
            newElement = this.document.createElement(localName);
        } else {
            if (namespaceUri == null) {
                if (this.currentElement != null) {
                    namespaceUri = this.currentElement.lookupNamespaceURI(namespacePrefix);
                }
                if (namespaceUri == null) {
                    throw new XmlNamespacePrefixUndefinedException(namespacePrefix);
                }
            } else {
                if (namespacePrefix == null) {
                    if (this.currentElement != null) {
                        namespacePrefix = this.currentElement.lookupPrefix(namespaceUri);
                    }
                    if (namespacePrefix == null) {
                        // Exception or build own prefix?
                        throw new XmlException("No such Uri" + namespaceUri);
                    }
                }
            }
            newElement = this.document.createElementNS(namespaceUri, createQualifiedName(
                    namespacePrefix, localName));
        }
        if (this.currentElement == null) {
            if (this.document.getDocumentElement() != null) {
                throw new XmlException(NlsResourceBundle.ERR_SECOND_ROOT);
            }
            this.document.appendChild(newElement);
        } else {
            this.currentElement.appendChild(newElement);
        }
        this.currentElement = newElement;
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeAttribute(java.lang.String,
     *      java.lang.String, java.lang.String)
     * {@inheritDoc}
     */
    public void writeAttribute(String name, String value, String namespacePrefix)
            throws XmlException {

        if (this.currentElement == null) {
            throw new XmlNoOpenElementException();
        }
        if (namespacePrefix == null) {
            this.currentElement.setAttribute(name, value);
        } else {
            String namespaceUri = this.currentElement.lookupNamespaceURI(namespacePrefix);
            if (namespaceUri == null) {
                throw new XmlNamespacePrefixUndefinedException(namespacePrefix);
            }
            this.currentElement.setAttributeNS(namespaceUri, createQualifiedName(namespacePrefix,
                    name), value);
        }
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeCharacters(java.lang.String)
     * {@inheritDoc}
     */
    public void writeCharacters(String text) throws XmlException {

        if (this.currentElement == null) {
            throw new XmlNoOpenElementException();
        }
        this.currentElement.appendChild(this.document.createTextNode(text));
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeCData(java.lang.String)
     * {@inheritDoc}
     */
    public void writeCData(String text) throws XmlException {

        if (this.currentElement == null) {
            throw new XmlNoOpenElementException();
        }
        this.currentElement.appendChild(this.document.createCDATASection(text));
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeEndElement(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    public void writeEndElement(String localName, String namespacePrefix) throws XmlException {

        if (this.currentElement == null) {
            throw new XmlNoOpenElementException();
        }
        String openTagName = this.currentElement.getTagName();
        String qualifiedName = createQualifiedName(namespacePrefix, localName);
        if (!qualifiedName.equals(openTagName)) {
            throw new XmlCloseElementException(openTagName, qualifiedName);
        }
        Node parentNode = this.currentElement.getParentNode();
        if (parentNode == this.element) {
            throw new XmlException(NlsResourceBundle.ERR_CLOSE_TOPLEVEL);
        }
        if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
            this.currentElement = (Element) parentNode;
        } else if (parentNode.getNodeType() == Node.DOCUMENT_NODE) {
            this.currentElement = null;
        } else {
            throw new XmlException(NlsResourceBundle.ERR_INVALID_XML);
        }
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeComment(java.lang.String)
     * {@inheritDoc}
     */
    public void writeComment(String text) throws XmlException {

        if (this.currentElement == null) {
            throw new XmlNoOpenElementException();
        }
        this.currentElement.appendChild(this.document.createComment(text));
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeNamespaceDeclaration(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    public void writeNamespaceDeclaration(String namespaceKey, String namespaceUri)
            throws XmlException {

        this.currentElement.setAttributeNS(XmlUtil.NAMESPACE_URI_XMLNS, createQualifiedName(
                XmlUtil.NAMESPACE_PREFIX_XMLNS, namespaceKey), namespaceUri);
    }
}