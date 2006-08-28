/* $ Id: $ */
package net.sf.mmm.configuration.impl.format.xml.dom;

import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;
import net.sf.mmm.context.api.MutableContextIF;
import net.sf.mmm.xml.DomUtil;
import net.sf.mmm.xml.api.XmlException;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF} for an XML
 * {@link org.w3c.dom.Document document}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlDocument extends AbstractConfigurationDocument {

    /** the XML document */
    private Document xmlDocument;

    /**
     * The constructor.
     * 
     * @param configurationAccess
     * @param env
     */
    public XmlDocument(ConfigurationAccessIF configurationAccess, MutableContextIF env) {

        super(configurationAccess, env);
    }

    /**
     * The constructor.
     * 
     * @param configurationAccess
     * @param parentConfiguration
     */
    public XmlDocument(ConfigurationAccessIF configurationAccess,
            AbstractConfiguration parentConfiguration) {

        super(configurationAccess, parentConfiguration);
    }

    /**
     * 
     * @param localname
     * @param xmlNode
     * @param namespaceUri
     * @return the qualified name.
     */
    protected static String getQualifiedName(String localname, Node xmlNode, String namespaceUri) {

        String prefix = xmlNode.lookupPrefix(namespaceUri);
        if (prefix == null) {
            // TODO: generate prefix from namespace ???
            throw new IllegalArgumentException("namespace not defined: " + namespaceUri);
        }
        return createQualifiedName(localname, prefix);
    }

    /**
     * This method creates a qualified name build of <code>localName</code>
     * and <code>prefix</code>.
     * 
     * @param localName
     *        is the local name.
     * @param prefix
     *        is the namespace prefix. It may be <code>null</code> for NO
     *        namespace.
     * @return the qualified name.
     */
    protected static String createQualifiedName(String localName, String prefix) {

        if (prefix == null) {
            return localName;
        } else {
            return prefix + ":" + localName;
        }

    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfigurationDocument#save(java.io.OutputStream)
     *      {@inheritDoc}
     */
    @Override
    protected void save(OutputStream outputStream) throws ConfigurationException {

        try {
            DomUtil.writeXml(this.xmlDocument, outputStream, false);
        } catch (XmlException e) {
            // TODO:
            throw new ConfigurationException(e.getMessage());
        }
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfigurationDocument#load(java.io.InputStream)
     *      {@inheritDoc}
     */
    @Override
    protected AbstractConfiguration load(InputStream inputStream) throws ConfigurationException {

        try {
            this.xmlDocument = DomUtil.parseDocument(inputStream);
            Element rootElement = this.xmlDocument.getDocumentElement();
            XmlElement rootNode = new XmlElement(this, getParentConfiguration(), rootElement);
            return rootNode;
        } catch (XmlException e) {
            // TODO:
            throw new ConfigurationException(e.getMessage(), e);
        }
    }

}
