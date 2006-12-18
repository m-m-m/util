/* $Id$ */
package net.sf.mmm.search.parser.impl;

import java.io.InputStream;
import java.util.Properties;

import net.sf.mmm.search.parser.api.ContentParser;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} used as fallback if no
 * specific parser is available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserGeneric implements ContentParser {

  /**
   * The constructor
   */
  public ContentParserGeneric() {

    super();
  }

  /**
   * @see net.sf.mmm.search.parser.api.ContentParser#parse(java.io.InputStream,
   *      java.lang.String)
   */
  public Properties parse(InputStream inputStream, String filename) throws Exception {

    Properties properties = new Properties();
    properties.setProperty(PROPERTY_KEY_TEXT, "test");
    return properties;
  }

}
