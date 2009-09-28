/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.base;

import net.sf.mmm.content.parser.api.ContentParser;

/**
 * This is the interface for a registrar of {@link ContentParser}
 * implementations. It will typically be a view on the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParserService} but it may also be
 * something else.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentParserRegistrar {

  /**
   * This method registers the given <code>parser</code> for the given
   * <code>extensions</code>.
   * 
   * @see net.sf.mmm.content.parser.api.ContentParserService#getParser(String)
   * 
   * @param parser is the parser to register.
   * @param keys are the keys the parser will be associated with.
   */
  void addParser(ContentParser parser, String... keys);

}
