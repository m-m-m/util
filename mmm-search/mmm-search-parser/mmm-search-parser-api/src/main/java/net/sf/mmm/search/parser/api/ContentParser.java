/* $Id$ */
package net.sf.mmm.search.parser.api;

import java.io.InputStream;
import java.util.Properties;

/**
 * This is the interface for a parser that
 * {@link #parse(InputStream, String) extracts} (meta-)data from the content of
 * an as {@link InputStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentParser {

  /**
   * this is the {@link Properties#keys() property-key} used to
   * {@link Properties#getProperty(String) get} the plain <code>text</code>
   * (the actual content) of the content from the
   * {@link #parse(InputStream, String) parsed} {@link Properties}.<br>
   * This property has to be set (not <code>null</code>).
   */
  String PROPERTY_KEY_TEXT = "text";

  /**
   * this is the {@link Properties#keys() property-key} used to
   * {@link Properties#getProperty(String) get} the <code>title</code> of the
   * content from the {@link #parse(InputStream, String) parsed}
   * {@link Properties}.<br>
   * If the title could NOT be determined, this property is NOT set.
   */
  String PROPERTY_KEY_TITLE = "title";

  /**
   * this is the {@link Properties#keys() property-key} used to
   * {@link Properties#getProperty(String) get} the <code>keywords</code> (aka
   * tags or subject) of the content from the
   * {@link #parse(InputStream, String) parsed} {@link Properties}.<br>
   * If the title could NOT be determined, this property is NOT set.
   */
  String PROPERTY_KEY_KEYWORDS = "keywords";

  /**
   * this is the {@link Properties#keys() property-key} used to
   * {@link Properties#getProperty(String) get} the <code>author</code> (aka
   * artist) of the content from the {@link #parse(InputStream, String) parsed}
   * {@link Properties}.<br>
   * If the title could NOT be determined, this property is NOT set.
   */
  String PROPERTY_KEY_AUTHOR = "author";

  /**
   * This method parses the document given as <code>inputStream</code> and
   * extracts (meta-)data returned as {@link Properties}.
   * 
   * @param inputStream
   *        is the fresh input stream of the content to parse. It will NOT be
   *        {@link InputStream#close() closed} by this method because there can
   *        be arbitrary implementations for this interface that would all need
   *        to handle the closing for success and exceptional states.
   * @param filename
   *        is the filename of the content to parse. It may be used as hint for
   *        the parser.
   * @return the properties containing the extracted (meta-)data from the parsed
   *         <code>inputStream</code>. See the <code>PROPERTY_KEY_*</code>
   *         constants (e.g. {@link #PROPERTY_KEY_TEXT}) for the default keys.
   *         Please note that an implementation may use individual keys for
   *         additional properties.
   * @throws Exception
   *         if the parsing failed for a technical reason. There can be
   *         arbitrary implementations for this interface that can throw any
   *         {@link Exception} from this method. Declaring a specific
   *         <code>ParseException</code> here would cause the overhead of
   *         additional encapsulation of exceptions without any advantage. The
   *         user of this interface has to catch for {@link Exception} what
   *         includes {@link RuntimeException}s and excludes {@link Error}s.
   *         He has to handle the problem anyways (also for
   *         {@link RuntimeException}s) and has all contextual information
   *         required to enhance the exception
   *         {@link Exception#getMessage() message}. This is NOT a matter of
   *         bad design.
   */
  Properties parse(InputStream inputStream, String filename) throws Exception;

}
