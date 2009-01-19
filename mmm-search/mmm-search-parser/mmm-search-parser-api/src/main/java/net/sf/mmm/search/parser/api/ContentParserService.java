/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.api;

/**
 * This is the interface for a service that provides {@link ContentParser}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentParserService {

  /**
   * This method gets a generic {@link ContentParser parser} that can be used as
   * fallback if no {@link #getParser(String) specific parser} is available.
   * 
   * @return the generic parser.
   */
  ContentParser getGenericParser();

  /**
   * This method gets the {@link ContentParser parser} for the given
   * <code>key</code>.
   * 
   * @param key is the key identifying the requested parser. You can use the
   *        extension of the filename excluding the dot (e.g. "txt", "xml",
   *        "html", "pdf", etc.) or the mimetype (e.g. "text/plain", "text/xml",
   *        "text/html", "application/pdf", etc.).
   * @return the {@link ContentParser parser} for the given <code>key</code> or
   *         <code>null</code> if no such parser is available (see
   *         {@link #getGenericParser()}).
   */
  ContentParser getParser(String key);

}
