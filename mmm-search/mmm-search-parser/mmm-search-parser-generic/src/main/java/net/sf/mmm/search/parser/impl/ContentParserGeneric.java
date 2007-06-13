/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.InputStream;
import java.util.Properties;

import net.sf.mmm.search.parser.base.AbstractContentParser;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} used as fallback if no
 * specific parser is available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserGeneric extends AbstractContentParser {

  /**
   * The constructor.
   */
  public ContentParserGeneric() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(InputStream inputStream, long filesize, String encoding,
      Properties properties) throws Exception {

    // TODO: re-implement GNU command "strings"...
    properties.setProperty(PROPERTY_KEY_TEXT, "");
  }

}
