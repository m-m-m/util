/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * This is the interface for a collection of utility functions that help to deal with XML.
 *
 * @see DomUtil
 *
 * @see net.sf.mmm.util.xml.base.XmlUtilImpl#getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface XmlUtil {

  /** the URI of the xmlns namespace */
  String NAMESPACE_URI_XML = "http://www.w3.org/XML/1998/namespace".intern();

  /** the reserved xmlns namespace prefix */
  String NAMESPACE_PREFIX_XML = "xml".intern();

  /** the URI of the xmlns namespace */
  String NAMESPACE_URI_XMLNS = "http://www.w3.org/2000/xmlns/".intern();

  /** the reserved xmlns namespace prefix */
  String NAMESPACE_PREFIX_XMLNS = "xmlns".intern();

  /** the URI of the XInclude namespace */
  String NAMESPACE_URI_XINCLUDE = "http://www.w3.org/2001/XInclude".intern();

  /** the default namespace prefix for XInclude */
  String NAMESPACE_PREFIX_XINCLUDE = "xi".intern();

  /** the URI of the XLink namespace */
  String NAMESPACE_URI_XLINK = "http://www.w3.org/1999/xlink".intern();

  /** the default namespace prefix for XLink */
  String NAMESPACE_PREFIX_XLINK = "xlink".intern();

  /** the URI of the XSLT namespace */
  String NAMESPACE_URI_XSLT = "http://www.w3.org/1999/XSL/Transform".intern();

  /** the default namespace prefix for XSLT */
  String NAMESPACE_PREFIX_XSLT = "xslt".intern();

  /** the URI of the XML-Schema namespace */
  String NAMESPACE_URI_SCHEMA = "http://www.w3.org/2001/XMLSchema".intern();

  /** the default namespace prefix for XML-Schema */
  String NAMESPACE_PREFIX_SCHEMA = "xs".intern();

  /** the URI of the XML-Schema namespace */
  String NAMESPACE_URI_SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance".intern();

  /** the default namespace prefix for XML-Schema */
  String NAMESPACE_PREFIX_SCHEMA_INSTANCE = "xsi".intern();

  /** the URI of the XML-Schema namespace */
  String NAMESPACE_URI_XPATH_FUNCTIONS = "http://www.w3.org/2005/xpath-functions".intern();

  /** the default namespace prefix for XML-Schema */
  String NAMESPACE_PREFIX_XPATH_FUNCTIONS = "fn".intern();

  /** the URI of the SVG namespace */
  String NAMESPACE_URI_SVG = "http://www.w3.org/2000/svg".intern();

  /** the default namespace prefix for SVG */
  String NAMESPACE_PREFIX_SVG = "svg".intern();

  /** the URI of the MathML namespace */
  String NAMESPACE_URI_MATHML = "http://www.w3.org/1998/Math/MathML".intern();

  /** the URI of the XHTML namespace */
  String NAMESPACE_URI_XHTML = "http://www.w3.org/1999/xhtml".intern();

  /** the default namespace prefix for XHTML */
  String NAMESPACE_PREFIX_XHTML = "xhtml".intern();

  /** the URI of the XML-Events namespace */
  String NAMESPACE_URI_XML_EVENTS = "http://www.w3.org/2001/xml-events".intern();

  /** the default namespace prefix for XML-Events */
  String NAMESPACE_PREFIX_XML_EVENTS = "ev".intern();

  /** the URI of the RELAX-NG-Structure namespace */
  String NAMESPACE_URI_RELAXNG_STRUCTURE = "http://relaxng.org/ns/structure/1.0".intern();

  /** the URI of the RELAX-NG-Structure namespace */
  String NAMESPACE_URI_RELAXNG_ANNOTATION = "http://relaxng.org/ns/annotation/1.0".intern();

  /**
   * This method escapes the given {@code string} for usage in XML (or HTML, etc.).
   *
   * @param string is the string to escape.
   * @param escapeQuotations if {@code true} also the ASCII quotation characters (apos {@code '\''} and quot
   *        {@code '"'} ) will be escaped, else if {@code false} quotations are untouched. Set this to
   *        {@code true} if you are writing the value of an attribute.
   * @return the escaped string.
   */
  String escapeXml(String string, boolean escapeQuotations);

  /**
   * This method writes the given {@code string} to the {@code writer} while escaping special characters for
   * XML (or HTML, etc.).
   *
   * @param string is the string to escape.
   * @param writer is where to write the string to.
   * @param escapeQuotations if {@code true} also the ASCII quotation characters (apos {@code '\''} and quot
   *        {@code '"'} ) will be escaped, else if {@code false} quotations are untouched. Set this to
   *        {@code true} if you are writing the value of an attribute.
   */
  void escapeXml(String string, Writer writer, boolean escapeQuotations);

  /**
   * This method creates a {@link Reader} from the given {@code inputStream} that uses the encoding specified
   * in the (potential) XML header of the {@link InputStream}s content. If no XML header is specified, the
   * default encoding is used.
   *
   * @param inputStream is a fresh input-stream that is supposed to point to the content of an XML document.
   * @return a reader on the given {@code inputStream} that takes respect on the encoding specified in the
   *         (potential) XML header.
   */
  Reader createXmlReader(InputStream inputStream);

  /**
   * This method creates a {@link Reader} from the given {@code inputStream} that uses the encoding specified
   * in the (potential) XML header of the {@link InputStream}s content. If no XML header is specified, the
   * default encoding is used.
   *
   * @param inputStream is a fresh input-stream that is supposed to point to the content of an XML document.
   * @param defaultCharset is the {@link Charset} used if NO encoding was specified via an XML header.
   * @return a reader on the given {@code inputStream} that takes respect on the encoding specified in the
   *         (potential) XML header.
   */
  Reader createXmlReader(InputStream inputStream, Charset defaultCharset);

  /**
   * This method resolves an HTML entity given by {@code entityName}.
   *
   * @param entityName is the bare name of the entity (e.g. "amp" or "uuml"). Please note that entity-names
   *        are case-sensitive.
   * @return the value of the entity or {@code null} if no entity exists for the given {@code entityName}.
   */
  Character resolveEntity(String entityName);

  /**
   * This method extracts the plain text from the given {@code htmlFragment} and appends it to the given
   * {@code buffer}. This includes removing tags, un-escaping entities and parsing CDATA sections. Unlike DOM
   * parsers this method is completely fault tolerant, fast and uses a minimum amount of memory. <br>
   * <b>ATTENTION:</b><br>
   * Be aware that the caller is responsible for reading the HTML with the proper encoding (according to
   * Content-Type from HTTP header and/or META tag).
   *
   * @param htmlFragment is the HTML fragment to parse.
   * @param buffer is the buffer where the plain text will be appended to.
   * @param parserState is the state to continue on a subsequent call for multiple {@code htmlFragment}s of
   *        the same HTML-document or {@code null} for a fresh start.
   * @return the state at the end of {@code htmlFragment}. You can pass this as {@code parserState} argument
   *         on subsequent call to continue parsing.
   */
  ParserState extractPlainText(String htmlFragment, StringBuilder buffer, ParserState parserState);

}
