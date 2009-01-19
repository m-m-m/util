/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;

import net.sf.mmm.search.parser.api.ContentParser;

/**
 * This is the test-case for {@link ContentParserPpt}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ContentParserDocTest extends AbstractContentParserPoiTest {

  public void checkParser(ContentParser parser, long filesize) throws Exception {

    Properties properties = parse(parser, "test.doc", filesize);
    String title = properties.getProperty(ContentParser.PROPERTY_KEY_TITLE);
    assertEquals("Title of Testdocument", title);
    String author = properties.getProperty(ContentParser.PROPERTY_KEY_AUTHOR);
    assertEquals("J\u00F6rg Hohwiller", author);
    String keywords = properties.getProperty(ContentParser.PROPERTY_KEY_KEYWORDS);
    assertEquals("some keywords", keywords);
    String text = properties.getProperty(ContentParser.PROPERTY_KEY_TEXT);
    // System.out.println(text);
    assertTrue(text.contains("Title"));
    assertTrue(text.contains("Header"));
    assertTrue(text.contains("Footer"));
    assertTrue(text.contains("Footnote"));
    assertTrue(text.contains("Hello world, this is a test."));
    assertTrue(text.contains("This is a comment."));
    // assertTrue(text.contains("Watermark"));
  }

  @Override
  protected ContentParser getContentParser() {

    ContentParserDoc parser = new ContentParserDoc();
    parser.initialize();
    return parser;
  }

  @Test
  public void testParser() throws Exception {

    ContentParser parser = getContentParser();
    checkParser(parser, 0);
    // parser.setMaximumBufferSize(4096);
    checkParser(parser, 9728);
  }

}
