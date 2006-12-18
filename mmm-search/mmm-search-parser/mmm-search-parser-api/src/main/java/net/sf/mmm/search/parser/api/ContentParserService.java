/* $Id$ */
package net.sf.mmm.search.parser.api;

/**
 * This is the interface for a service that provides {@link ContentParser}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentParserService {

  /**
   * This method gets a generic {@link ContentParser parser} that is used if no
   * parser is available for a specific
   * {@link #getParser(String) file-extension}.
   * 
   * @return the generic parser.
   */
  ContentParser getGenericParser();

  /**
   * This method gets the {@link ContentParser parser} for the given
   * <code>fileExtension</code>.
   * 
   * @param fileExtension
   *        is the extension of the filename excluding the dot (e.g. "xml",
   *        "html", "htm", etc.).
   * @return the {@link ContentParser parser} for the given
   *         <code>fileExtension</code> or <code>null</code> if no specific
   *         parser is available for the given <code>fileExtension</code> (see
   *         {@link #getGenericParser()}).
   */
  ContentParser getParser(String fileExtension);

}
