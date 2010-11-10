/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.api;

import java.io.InputStream;
import java.util.Properties;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a parser that {@link #parse(InputStream, long)
 * extracts} (meta-)data from the content of an as {@link InputStream}.<br>
 * <b>ATTENTION:</b><br>
 * The implementation should allocate expensive resources (e.g. byte-arrays)
 * only temporary while {@link #parse(InputStream, long) parsing}. See also
 * {@link net.sf.mmm.content.parser.base.LimitBufferSize}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ComponentSpecification(plugin = true)
public interface ContentParser {

  /**
   * this is the {@link Properties#keys() property-key} used to
   * {@link Properties#getProperty(String) get} the plain <code>text</code> (the
   * actual content) of the content from the {@link #parse(InputStream, long)
   * parsed} {@link Properties}.<br>
   * This property has to be set (not <code>null</code>).
   */
  String PROPERTY_KEY_TEXT = "text";

  /**
   * this is the {@link Properties#keys() property-key} used to
   * {@link Properties#getProperty(String) get} the <code>title</code> of the
   * content from the {@link #parse(InputStream, long) parsed}
   * {@link Properties}.<br>
   * If the title could NOT be determined, this property is NOT set.
   */
  String PROPERTY_KEY_TITLE = "title";

  /**
   * this is the {@link Properties#keys() property-key} used to
   * {@link Properties#getProperty(String) get} the <code>keywords</code> (aka
   * tags or subject) of the content from the {@link #parse(InputStream, long)
   * parsed} {@link Properties}.<br>
   * If the title could NOT be determined, this property is NOT set.
   */
  String PROPERTY_KEY_KEYWORDS = "keywords";

  /**
   * this is the {@link Properties#keys() property-key} used to
   * {@link Properties#getProperty(String) get} the <code>author</code> (aka
   * artist) of the content from the {@link #parse(InputStream, long) parsed}
   * {@link Properties}.<br>
   * If the title could NOT be determined, this property is NOT set.
   */
  String PROPERTY_KEY_AUTHOR = "author";

  /**
   * This method parses the document given as <code>inputStream</code> and
   * extracts (meta-)data returned as {@link Properties}.<br>
   * In advance to {@link #parse(InputStream, long, String)} this method guesses
   * the encoding of textual files automatically. Therefore you need to set the
   * system-encoding used as default for non-unicode files explicitly via the
   * system-property <code>file.encoding</code> (e.g.
   * <code>java -Dfile.encoding=ISO-8859-1 ...</code>).
   * 
   * @param inputStream is the fresh input stream of the content to parse. It
   *        will be {@link InputStream#close() closed} by this method (on
   *        success and in exceptional state).
   * @param filesize is the size (content-length) of the content to parse in
   *        bytes or <code>0</code> if NOT available (unknown). If available,
   *        the parser may use this value for optimized allocations.
   * @return the properties containing the extracted (meta-)data from the parsed
   *         <code>inputStream</code>. See the <code>PROPERTY_KEY_*</code>
   *         constants (e.g. {@link #PROPERTY_KEY_TEXT}) for the default keys.
   *         Please note that an implementation may use individual keys for
   *         additional properties.
   * @throws Exception if the parsing failed for a technical reason. There can
   *         be arbitrary implementations for this interface that can throw any
   *         {@link Exception} from this method. Declaring a specific
   *         <code>ParseException</code> here would cause the overhead of
   *         additional encapsulation of exceptions without any advantage. The
   *         user of this interface has to catch for {@link Exception} what
   *         includes {@link RuntimeException}s and excludes {@link Error}s. He
   *         has to handle the problem anyways (also for
   *         {@link RuntimeException}s) and has all contextual information
   *         required to enhance the exception {@link Exception#getMessage()
   *         message}. This is NOT a matter of bad design.
   */
  Properties parse(InputStream inputStream, long filesize) throws Exception;

  /**
   * This method parses the document given as <code>inputStream</code> and
   * extracts (meta-)data returned as {@link Properties}.
   * 
   * @param inputStream is the fresh input stream of the content to parse. It
   *        will be {@link InputStream#close() closed} by this method (on
   *        success and in exceptional state).
   * @param filesize is the size (content-length) of the content to parse in
   *        bytes or <code>0</code> if NOT available (unknown). If available,
   *        the parser may use this value for optimized allocations.
   * @param encoding is the explicit encoding to use for textual files. Parsers
   *        for formats that make encoding explicit (e.g. XML) will completely
   *        ignore this parameter.
   * @return the properties containing the extracted (meta-)data from the parsed
   *         <code>inputStream</code>. See the <code>PROPERTY_KEY_*</code>
   *         constants (e.g. {@link #PROPERTY_KEY_TEXT}) for the default keys.
   *         Please note that an implementation may use individual keys for
   *         additional properties.
   * @throws Exception if the parsing failed for a technical reason. There can
   *         be arbitrary implementations for this interface that can throw any
   *         {@link Exception} from this method. Declaring a specific
   *         <code>ParseException</code> here would cause the overhead of
   *         additional encapsulation of exceptions without any advantage. The
   *         user of this interface has to catch for {@link Exception} what
   *         includes {@link RuntimeException}s and excludes {@link Error}s. He
   *         has to handle the problem anyways (also for
   *         {@link RuntimeException}s) and has all contextual information
   *         required to enhance the exception {@link Exception#getMessage()
   *         message}. This is NOT a matter of bad design.
   */
  Properties parse(InputStream inputStream, long filesize, String encoding) throws Exception;

}
