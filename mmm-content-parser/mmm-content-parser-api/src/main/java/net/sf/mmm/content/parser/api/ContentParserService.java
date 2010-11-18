/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.api;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a service that provides {@link ContentParser}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ComponentSpecification
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
   * @see ContentParser#getPrimaryKeys()
   * @see ContentParser#getSecondaryKeys()
   * 
   * @param key is the key identifying the requested parser. You can use the
   *        {@link ContentParser#getExtension() extension} or the
   *        {@link ContentParser#getMimetype() mimetype} of the content you want
   *        to parse.
   * @return the {@link ContentParser parser} for the given <code>key</code> or
   *         the {@link #getGenericParser() generic parser} if no specific
   *         parser is available (check {@link ContentParser#getExtension()} for
   *         <code>null</code> to figure out).
   */
  ContentParser getParser(String key);

}
