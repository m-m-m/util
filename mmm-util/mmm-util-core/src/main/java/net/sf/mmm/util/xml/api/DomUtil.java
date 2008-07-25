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
import org.w3c.dom.Node;
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
   * the name of the element without any namespace prefix.
   * 
   * @param element the element to get the local name from.
   * @return the local name of the given element.
   */
  String getLocalNodeName(Node element);

  /**
   * TODO
   * 
   * @param node1
   * @param node2
   * @return <code>true</code> if the XML fragment represented by the given
   *         nodes equals, <code>false</code> otherwise.
   */
  boolean isEqual(Node node1, Node node2);

  /**
   * This method determines if the given <code>node</code> represents regular
   * text.
   * 
   * @param node is the node to check.
   * @return <code>true</code> if the given <code>node</code> is
   *         {@link org.w3c.dom.Text text} or {@link org.w3c.dom.CDATASection
   *         CDATA}.
   */
  boolean isTextualNode(Node node);

}
