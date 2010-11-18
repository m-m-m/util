/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.api;

import java.io.InputStream;
import java.util.Set;

import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.context.api.GenericContext;

/**
 * This is the interface for a parser that {@link #parse(InputStream, long)
 * extracts} (meta-)data from the content of an {@link InputStream}.<br>
 * <b>ATTENTION:</b><br>
 * The implementation should allocate expensive resources (e.g. byte-arrays)
 * only temporary while {@link #parse(InputStream, long) parsing}. See also
 * {@link ContentParserOptions#getMaximumBufferSize()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ComponentSpecification(plugin = true)
public interface ContentParser {

  /**
   * This is the name of the {@link GenericContext#getVariable(String, Class)
   * variable} with the plain <code>text</code> of the content from the
   * {@link #parse(InputStream, long) parsed} {@link GenericContext}.<br>
   * This variable has the type {@link String} and should always be set (not
   * <code>null</code>).
   */
  String VARIABLE_NAME_TEXT = "text";

  /**
   * This is the name of the {@link GenericContext#getVariable(String, Class)
   * variable} with the <code>title</code> of the content from the
   * {@link #parse(InputStream, long) parsed} {@link GenericContext}.<br>
   * It has the type {@link String} and is optional (may be <code>null</code>).
   */
  String VARIABLE_NAME_TITLE = "title";

  /**
   * This is the name of the {@link GenericContext#getVariable(String, Class)
   * variable} with the <code>keywords</code> (also called tags) of the content
   * from the {@link #parse(InputStream, long) parsed} {@link GenericContext}.<br>
   * It has the type {@link String} and is optional (may be <code>null</code>).
   */
  String VARIABLE_NAME_KEYWORDS = "keywords";

  /**
   * This is the name of the {@link GenericContext#getVariable(String, Class)
   * variable} with the <code>creator</code> (also called author, artist,
   * composer, etc.) of the content from the {@link #parse(InputStream, long)
   * parsed} {@link GenericContext}.<br>
   * It has the type {@link String} and is optional (may be <code>null</code>).
   */
  String VARIABLE_NAME_CREATOR = "creator";

  /**
   * This method parses the document given as <code>inputStream</code> and
   * extracts {@link #VARIABLE_NAME_TEXT text} and metadata returned as
   * {@link GenericContext}.
   * 
   * @param inputStream is the fresh input stream of the content to parse. It
   *        will be {@link InputStream#close() closed} by this method (on
   *        success and in exceptional state).
   * @param filesize is the size (content-length) of the content to parse in
   *        bytes or <code>0</code> if NOT available (unknown). If available,
   *        the parser may use this value for optimized allocations.
   * @return the {@link GenericContext} containing the extracted metadata from
   *         the parsed <code>inputStream</code>. See the
   *         <code>VARIABLE_NAME_*</code> constants (e.g.
   *         {@link #VARIABLE_NAME_TEXT}) for the default keys. Please note that
   *         an implementation may use individual names for additional
   *         variables.
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
  GenericContext parse(InputStream inputStream, long filesize) throws Exception;

  /**
   * This method parses the document given as <code>inputStream</code> and
   * extracts {@link #VARIABLE_NAME_TEXT text} and metadata returned as
   * {@link GenericContext}.
   * 
   * @param inputStream is the fresh input stream of the content to parse. It
   *        will be {@link InputStream#close() closed} by this method (on
   *        success and in exceptional state).
   * @param filesize is the size (content-length) of the content to parse in
   *        bytes or <code>0</code> if NOT available (unknown). If available,
   *        the parser may use this value for optimized allocations.
   * @param options are the {@link ContentParserOptions}.
   * @return the {@link GenericContext} containing the extracted metadata from
   *         the parsed <code>inputStream</code>. See the
   *         <code>VARIABLE_NAME_*</code> constants (e.g.
   *         {@link #VARIABLE_NAME_TEXT}) for the default keys. Please note that
   *         an implementation may use individual names for additional
   *         variables.
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
  GenericContext parse(InputStream inputStream, long filesize, ContentParserOptions options)
      throws Exception;

  /**
   * This method gets the default filename extension excluding the dot (e.g.
   * "txt", "xml", "html", "pdf", etc.) for the content managed by this
   * {@link ContentParser}.
   * 
   * @return the default filename extension or <code>null</code> if this is the
   *         {@link ContentParserService#getGenericParser() generic parser}.
   */
  String getExtension();

  /**
   * This method gets the default mimetype (e.g. "text/plain", "text/xml",
   * "text/html", "application/pdf", etc.) for the content managed by this
   * {@link ContentParser}.
   * 
   * @return the default filename extension or <code>null</code> if this is the
   *         {@link ContentParserService#getGenericParser() generic parser}.
   */
  String getMimetype();

  /**
   * This method gets the primary
   * {@link net.sf.mmm.content.parser.api.ContentParserService#getParser(String)
   * keys} used to register this {@link ContentParser}. This set contains
   * {@link #getExtension() extension} and {@link #getMimetype() mimetype} and
   * maybe other alternatives.<br/>
   * Examples:<br/>
   * <ul>
   * <li>An HTML-parser will return "html" for {@link #getExtension()} but can
   * also include "htm" as {@link #getPrimaryKeys() primary key}.</li>
   * <li>A text-parser with {@link #getExtension() extension} "tar.gz" may also
   * include "tgz" as {@link #getPrimaryKeys() primary key}.</li>
   * <li>An XML-parser with {@link #getMimetype() mimetype} "text/xml" may also
   * include "application/xml" as {@link #getPrimaryKeys() primary key}.</li>
   * </ul>
   * 
   * @see net.sf.mmm.content.parser.api.ContentParserService#getParser(String)
   * @see #getSecondaryKeys()
   * @see java.util.Collections#emptySet()
   * 
   * @return the alias keys (extensions and mimetypes) of this
   *         {@link ContentParser}.
   */
  Set<String> getPrimaryKeys();

  /**
   * This method gets the secondary
   * {@link net.sf.mmm.content.parser.api.ContentParserService#getParser(String)
   * keys} used to register this {@link ContentParser}. If an other (more
   * specific) {@link ContentParser} defines such key as
   * {@link #getPrimaryKeys() primary key}, that {@link ContentParser} is chosen
   * first. Otherwise this implementation will be used.<br/>
   * Examples:<br/>
   * <ul>
   * <li>An HTML-parser will return "html" for {@link #getExtension()} but can
   * define "xhtml" and "application/xhtml+xml" as {@link #getSecondaryKeys()
   * secondary key}.</li>
   * <li>A text-parser with {@link #getExtension() extension} "txt" may return
   * "java", "php", "c", "cpp", etc. as {@link #getSecondaryKeys() secondary
   * keys}.</li>
   * </ul>
   * 
   * @see net.sf.mmm.content.parser.api.ContentParserService#getParser(String)
   * @see #getPrimaryKeys()
   * @see java.util.Collections#emptySet()
   * 
   * @return the alias keys (extensions and mimetypes) of this
   *         {@link ContentParser}.
   */
  Set<String> getSecondaryKeys();

}
