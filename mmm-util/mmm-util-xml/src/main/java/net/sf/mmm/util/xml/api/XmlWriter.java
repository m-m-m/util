/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

import net.sf.mmm.util.xml.XmlException;

/**
 * This is the interface for a simple XML serializer. I do not like the SAX API
 * so I had to reinvent the wheel.
 * 
 * @see org.xml.sax.ContentHandler
 * @see org.w3c.dom.Document
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XmlWriter {

  /**
   * This method opens an XML {@link org.w3c.dom.Element element} in the default
   * namespace. The XML element must be closed using the
   * {@link XmlWriter#writeEndElement(String)} method.<br>
   * Will produce the XML <code>&lt;localName ...</code>
   * 
   * @param localName is the local tag name of the element.
   * @throws XmlException if the serialization produced an error (I/O Error,
   *         invalid XML, etc.)
   */
  void writeStartElement(String localName) throws XmlException;

  /**
   * This method opens an XML {@link org.w3c.dom.Element element}. The XML
   * element must be closed using the
   * {@link XmlWriter#writeEndElement(String, String)} method.<br>
   * It will produce the XML code <code>&lt;namespacePrefix:localName ...</code>.
   * 
   * @param localName is the local tag name of the element.
   * @param namespacePrefix is the prefix of the referenced namespace. The
   *        namespace must have been declared in a parent element using the
   *        {@link #writeStartElement(String, String, String)} or
   *        {@link #writeNamespaceDeclaration(String, String)} method.
   * @throws XmlException if the serialization produced an error (I/O Error,
   *         invalid XML, etc.)
   */
  void writeStartElement(String localName, String namespacePrefix) throws XmlException;

  /**
   * This method opens an XML {@link org.w3c.dom.Element element} in a new
   * namespace that has not been declared by a parent element before. The XML
   * element must be closed using the
   * {@link XmlWriter#writeEndElement(String, String)} method.<br>
   * Will produce the XML
   * <code>&lt;namespacePrefix:localName xmlns:namespacePrefix="namespaceUri" ...</code>
   * or <code>&lt;localName xmlns="namespaceUri" ...</code>
   * 
   * @param localName is the local tag name of the element.
   * @param namespacePrefix is the key of the referenced namespace.
   * @param namespaceUri is the URI that uniquely identifies the namespace.
   * @throws XmlException if the serialization produced an error (I/O Error,
   *         invalid XML, etc.)
   */
  void writeStartElement(String localName, String namespacePrefix, String namespaceUri)
      throws XmlException;

  /**
   * This method sets an attribute in the default namespace in the previously
   * opened element. This method must be called after the
   * {@link XmlWriter#writeStartElement(String)} method is called and before the
   * {@link XmlWriter#writeCharacters(String)} or
   * {@link XmlWriter#writeEndElement(String, String)} method is called.
   * 
   * @see javax.xml.stream.XMLStreamWriter#writeAttribute(java.lang.String,
   *      java.lang.String)
   * 
   * @param name is the name of the attribute
   * @param value is the plain (not encoded) value of the attribute.
   * @throws XmlException if the serialization produced an error (I/O Error,
   *         invalid XML, etc.)
   */
  void writeAttribute(String name, String value) throws XmlException;

  /**
   * This method sets an attribute of the previously opened element. This method
   * must be called after the {@link XmlWriter#writeStartElement(String)} method
   * is called and before the {@link XmlWriter#writeCharacters(String)} or
   * {@link XmlWriter#writeEndElement(String)} method is called.
   * 
   * @see javax.xml.stream.XMLStreamWriter#writeAttribute(java.lang.String,
   *      java.lang.String, java.lang.String, java.lang.String)
   * 
   * @param name is the name of the attribute
   * @param value is the plain (not encoded) value of the attribute.
   * @param namespacePrefix is the key of the referenced namespace. A value of
   *        <code>null</code> is allowed and will produce the same behaviour
   *        as {@link #writeAttribute(String, String)}.
   * @throws XmlException if the serialization produced an error (I/O Error,
   *         invalid XML, etc.)
   */
  void writeAttribute(String name, String value, String namespacePrefix) throws XmlException;

  /**
   * This method declares a namespace in the previously
   * {@link #writeStartElement(String) opened} element.This method must be
   * called after the {@link XmlWriter#writeStartElement(String)} method is
   * called and before the {@link XmlWriter#writeCharacters(String)} or
   * {@link XmlWriter#writeEndElement(String)} method is called.
   * 
   * @param namespacePrefix is the key used as shortcut for the namespace.
   * @param namespaceUri is the exact URI that uniquely identifies the
   *        namespace.
   * @throws XmlException if the serialization produced an error (I/O Error,
   *         namespacePrefix already in use, etc.)
   */
  void writeNamespaceDeclaration(String namespacePrefix, String namespaceUri) throws XmlException;

  /**
   * This method writes some text into the toplevel (last opened) element.
   * 
   * @see javax.xml.stream.XMLStreamWriter#writeCharacters(java.lang.String)
   * 
   * @param text is the plain text. It is NOT encoded or escaped for XML.
   *        Specific characters such as &amp; have to be escaped by the
   *        implementation of this method.
   * @throws XmlException if the serialization produced an error (I/O Error,
   *         invalid XML, etc.)
   */
  void writeCharacters(String text) throws XmlException;

  /**
   * This method writes some text as CData section into the toplevel (last
   * opened) element.
   * 
   * @see javax.xml.stream.XMLStreamWriter#writeCData(java.lang.String)
   * 
   * @param text is the plain text. It is NOT encoded or escaped for XML. The
   *        character sequence "]]>" has to be escaped by the implementation of
   *        this method.
   * @throws XmlException if the serialization produced an error (I/O Error,
   *         invalid XML, etc.)
   */
  void writeCData(String text) throws XmlException;

  /**
   * This method writes some text as comment into the toplevel (last opened)
   * element.
   * 
   * @see javax.xml.stream.XMLStreamWriter#writeComment(java.lang.String)
   * 
   * @param comment is the plain (not encoded) comment text.
   * @throws XmlException if the serialization produced an error (I/O Error,
   *         invalid XML, etc.)
   */
  void writeComment(String comment) throws XmlException;

  /**
   * This method closes a previously {@link #writeStartElement(String) opened}
   * element using the default namespace.<br>
   * Will produce the XML <code>&lt;/localName&gt;</code> or
   * <code>/&gt;</code> added to the currently opened tag if it was empty.
   * 
   * @see javax.xml.stream.XMLStreamWriter#writeEndElement()
   * 
   * @param localName is the local tag name of the element.
   * @throws XmlException if the serialization produced an error (I/O Error,
   *         invalid XML, etc.)
   */
  void writeEndElement(String localName) throws XmlException;

  /**
   * This method closes a previously
   * {@link #writeStartElement(String, String) opened} element in the given
   * namespace.<br>
   * Will produce the XML <code>&lt;/namespacePrefix:localName&gt;</code> or
   * <code>/&gt;</code> added to the currently opened tag if it was empty.
   * 
   * @param localName is the local tag name of the element.
   * @param namespacePrefix is the key of the referenced namespace.
   * @throws XmlException if the serialization produced an error (I/O Error,
   *         invalid XML, etc.)
   */
  void writeEndElement(String localName, String namespacePrefix) throws XmlException;

}
