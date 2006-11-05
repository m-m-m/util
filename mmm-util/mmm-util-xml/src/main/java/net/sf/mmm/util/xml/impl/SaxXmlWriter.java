/* $ Id: $ */
package net.sf.mmm.util.xml.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.XmlUtil;
import net.sf.mmm.util.xml.base.AbstractXmlWriter;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SaxXmlWriter extends AbstractXmlWriter {

  /** the default type of an attribute */
  private static final String DEFAULT_ATTRIBUTE_TYPE = "CDATA";

  /** the SAX handler */
  private final ContentHandler handler;

  /** a table that mapps a namespacePrefix to an namespaceUri */
  private final Map<String, String> prefix2namespaceUriMap;

  /** container for the current attributes */
  private AttributesImpl currentAttributes;

  /** namespace-uri of the current open element */
  private String currentNamespaceUri;

  /** local name of the current open element */
  private String currentLocalName;

  /** (qualified) name of the current open element */
  private String currentName;

  /**
   * The constructor.
   * 
   * @param saxHandler
   *        is the handler that will receive the SAX events.
   */
  public SaxXmlWriter(ContentHandler saxHandler) {

    super();
    this.handler = saxHandler;
    this.prefix2namespaceUriMap = new HashMap<String, String>();
    this.currentAttributes = new AttributesImpl();
    this.currentName = null;
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeEndElement(java.lang.String,
   *      java.lang.String)
   */
  public void writeEndElement(String tagname, String namespacePrefix) throws XmlException {

    try {
      if (namespacePrefix == null) {
        this.handler.endElement("", "", tagname);
      } else {
        String namespaceUri = this.prefix2namespaceUriMap.get(namespacePrefix);
        if (namespaceUri == null) {
          // TODO
          throw new XmlException("Namespace undefined: " + namespacePrefix);
        }
        this.handler.endElement(namespaceUri, tagname,
            createQualifiedName(namespacePrefix, tagname));
      }
    } catch (SAXException e) {
      throw new XmlException(e.getMessage(), e);
    }
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeStartElement(java.lang.String,
   *      java.lang.String, java.lang.String)
   */
  public void writeStartElement(String tagname, String namespacePrefix, String namespaceUri)
      throws XmlException {

    completeOpenElement();
    this.currentAttributes.clear();
    if (namespacePrefix == null) {
      this.currentNamespaceUri = "";
      this.currentLocalName = "";
      this.currentName = tagname;
    } else {
      if (namespaceUri == null) {
        namespaceUri = this.prefix2namespaceUriMap.get(namespacePrefix);
      } else {
        this.prefix2namespaceUriMap.put(namespacePrefix, namespaceUri);
      }
      if (namespaceUri == null) {
        // TODO
        throw new XmlException("Namespace undefined: " + namespacePrefix);
      }
      this.currentNamespaceUri = namespaceUri;

    }
  }

  /**
   * This method closes the current element if it is still open.
   * 
   * @throws XmlException
   *         if the operation failed.
   */
  protected void completeOpenElement() throws XmlException {

    if (this.currentName != null) {
      try {
        this.handler.startElement(this.currentNamespaceUri, this.currentLocalName,
            this.currentName, this.currentAttributes);
        this.currentName = null;
      } catch (SAXException e) {
        // TODO
        throw new XmlException(e.getMessage(), e);
      }
    }
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeAttribute(java.lang.String,
   *      java.lang.String, java.lang.String)
   */
  public void writeAttribute(String name, String value, String namespacePrefix) throws XmlException {

    if (namespacePrefix == null) {
      this.currentAttributes.addAttribute("", "", name, DEFAULT_ATTRIBUTE_TYPE, value);
    } else {
      String namespaceUri = this.prefix2namespaceUriMap.get(namespacePrefix);
      if (namespaceUri == null) {
        throw new XmlNamespacePrefixUndefinedException(namespacePrefix);
      }
      this.currentAttributes.addAttribute(namespaceUri, name, createQualifiedName(namespacePrefix,
          name), DEFAULT_ATTRIBUTE_TYPE, value);
    }
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeNamespaceDeclaration(java.lang.String,
   *      java.lang.String)
   */
  public void writeNamespaceDeclaration(String namespacePrefix, String namespaceUri)
      throws XmlException {

    this.prefix2namespaceUriMap.put(namespacePrefix, namespaceUri);
    if (this.currentName != null) {
      this.currentAttributes.addAttribute(XmlUtil.NAMESPACE_URI_XMLNS, namespacePrefix,
          createQualifiedName("xmlns", namespacePrefix), DEFAULT_ATTRIBUTE_TYPE, namespaceUri);
    }
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeComment(java.lang.String)
   */
  public void writeComment(String comment) throws XmlException {

  // NOT supported by SAX API
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeCharacters(java.lang.String)
   */
  public void writeCharacters(String text) throws XmlException {

    try {
      StringWriter sw = new StringWriter(text.length() + 10);
      XmlUtil.createXmlTextWriter(sw).write(text);
      StringBuffer sb = sw.getBuffer();
      int length = sb.length();
      char[] data = new char[length];
      sw.getBuffer().getChars(0, length, data, 0);
      this.handler.characters(data, 0, length);
    } catch (IOException e) {
      // This is an internal (JDK) error!
      throw new XmlIOException(e);
    } catch (SAXException e) {
      // TODO
      throw new XmlException(e.getMessage(), e);
    }
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeCData(java.lang.String)
   */
  public void writeCData(String text) throws XmlException {

    writeCharacters(text);
  }

}
