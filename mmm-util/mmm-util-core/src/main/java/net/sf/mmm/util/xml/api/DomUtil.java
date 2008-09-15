/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * This is the interface for a collection of utility functions that help to deal
 * with the {@link org.w3c.dom.Node DOM} API.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DomUtil {

  /**
   * This method creates a new empty XML DOM document.
   * 
   * @return the new document.
   */
  Document createDocument();

  /**
   * This method parses an XML document from a given input stream.
   * 
   * @param inputStream is the input stream to the XML data. If will be closed
   *        at the end of this method (on success as well as in an exceptional
   *        state).
   * @return the parsed XML DOM document.
   * @throws XmlException if the XML is invalid and could NOT be parsed.
   * @throws IOException if the input stream produced an error while reading.
   */
  Document parseDocument(InputStream inputStream) throws XmlException, IOException;

  /**
   * This method parses an XML document from a given reader.
   * 
   * @param reader is the reader to the XML data.
   * @return the parsed XML DOM document.
   * @throws XmlException if the input stream produced an IOException or the XML
   *         is invalid.
   */
  Document parseDocument(Reader reader) throws XmlException;

  /**
   * This method parses an XML document into an DOM document.
   * 
   * @param inputSource is the input source to the XML data.
   * @return the parsed XML DOM document.
   * @throws XmlException if the input stream produced an IOException or the XML
   *         is invalid.
   */
  Document parseDocument(InputSource inputSource) throws XmlException;

  /**
   * This method writes the XML DOM tree given as node to the given output
   * stream.
   * 
   * @param xmlNode is the "root" node of the XML tree to write (e.g. the
   *        Document or just a single Element).
   * @param outputStream is where the serialized XML is written to.
   * @param indent - <code>true</code> if the XML should be indented
   *        (automatically add linebreaks before opening tags),
   *        <code>false</code> otherwise.
   * @throws XmlException if the output stream produced an IOException or
   *         whatever.
   */
  void writeXml(Node xmlNode, OutputStream outputStream, boolean indent) throws XmlException;

  /**
   * This method writes the XML DOM tree given as node to the given writer.
   * 
   * @param xmlNode is the "root" node of the XML tree to write (e.g. the
   *        Document or just a single Element).
   * @param writer is where the serialized XML is written to.
   * @param indent - <code>true</code> if the XML should be indented
   *        (automatically add linebreaks before opening tags),
   *        <code>false</code> otherwise.
   * @throws XmlException if the output stream produced an IOException or
   *         whatever.
   */
  void writeXml(Node xmlNode, Writer writer, boolean indent) throws XmlException;

  /**
   * This method transforms the given XML source to the given result without
   * structural modifications.
   * 
   * @param source is a source (e.g. DomSource, etc.)
   * @param result is a result (e.g. DomResult, StreamResult, etc.)
   * @param indent - <code>true</code> if the XML should be indented
   *        (automatically add linebreaks before opening tags),
   *        <code>false</code> otherwise.
   * @throws XmlException if the transformation failed (e.g. I/O error, invalid
   *         XML, or whatever).
   */
  void transformXml(Source source, Result result, boolean indent) throws XmlException;

  /**
   * This method gets the local name of a given <code>element</code>. This is
   * the name of the element without any namespace prefix.<br>
   * The method {@link Node#getLocalName()} will return <code>null</code> if the
   * {@link Node} has no {@link Node#getNamespaceURI() namespace}. Therefore
   * this method makes your life a little easier.
   * 
   * @param node the {@link Node} to get the local name from.
   * @return the local name of the given element.
   */
  String getLocalName(Node node);

  /**
   * This method determines if two {@link Node XML-nodes} represent the same
   * XML. This is quite a complex operation since the two given nodes may have
   * different segmentations of the text they contain and one might use
   * {@link org.w3c.dom.Text} while the other may use a
   * {@link org.w3c.dom.CDATASection}.
   * 
   * @param node1 is the first {@link Node} to compare.
   * @param node2 is the second {@link Node} to compare.
   * @param mode is the {@link XmlCompareMode mode} that determines how to do
   *        the comparison.
   * @return <code>true</code> if the XML fragment represented by the given
   *         nodes equals, <code>false</code> otherwise.
   */
  boolean isEqual(Node node1, Node node2, XmlCompareMode mode);

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
  Element getFirstElement(NodeList nodeList);

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
  Element getFirstElement(NodeList nodeList, String tagName);

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
  Element getFirstChildElement(Element element, String tagName);

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
  boolean getAttributeAsBoolean(Element element, String attribute, boolean defaultValue)
      throws IllegalArgumentException;

  /**
   * This method gets the text of the given node excluding the text of child
   * elements (depth=0).
   * 
   * @see #getNodeText(Node, Appendable, int)
   * 
   * @param node is the xml node containing the text.
   * @return the text of the node.
   */
  String getNodeText(Node node);

  /**
   * This method gets the text of the given node as string.
   * 
   * @see #getNodeText(Node, Appendable, int)
   * 
   * @param node is the xml node containing the text.
   * @param depth specifies how deep to step into child elements. If less than
   *        1, the text of child elements is ignored.
   * @return the text of the node including child nodes down to the specified
   *         depth.
   */
  String getNodeText(Node node, int depth);

/**
   * This method appends the text of the given node to the string buffer. Text
   * means the plain characters between the opening and the closing tag of the
   * element including the text of CDATA sections. The text of child elements is
   * only appended according to the given depth. If the depth is less than 1,
   * child elements are ignored, if equal to 1, the text of child elements is
   * included without their child elements. For an infinite depth use
   * <code>Integer.MAX_VALUE</code>. E.g. for the a element <code>a</code> in <br>
   * <pre>
   * <code>&lt;a&gt;123&lt;b/&gt;4&lt;c&gt;5&lt;d&gt;6&lt;/d&gt;&lt;/c&gt;&lt;![CDATA[7]]&gt;8&lt;/a&gt;</code>
   * </pre>
   * <br>
   * the call of <code>getNodeText(a, buffer, depth)</code> will append the
   * following text to the buffer according to the given depth. <ol start="0">
   * <li>"123478"</li> <li>"1234578"</li> <li>"12345678"</li> </ol>
   * 
   * @param node is the xml node containing the text.
   * @param buffer is the where to append the text to.
   * @param depth specifies how deep to step into child elements. If less than
   *        1, the text of child elements is ignored.
   */
  void getNodeText(Node node, Appendable buffer, int depth);

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
  Element requireFirstChildElement(Element element, String tagName) throws IllegalArgumentException;

  /**
   * This method removes all {@link Node#getChildNodes() child nodes} of the
   * given node.
   * 
   * @param node is the node to clean of children.
   */
  void removeChildren(Element node);

}
