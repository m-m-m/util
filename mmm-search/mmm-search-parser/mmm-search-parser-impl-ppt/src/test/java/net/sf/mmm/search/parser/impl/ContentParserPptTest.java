/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.junit.Test;

import net.sf.mmm.search.parser.api.ContentParser;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

import junit.framework.TestCase;

/**
 * This is the test-case for {@link ContentParserPpt}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ContentParserPptTest extends TestCase {

  /**
   * The constructor.
   */
  public ContentParserPptTest() {

    super();
  }

  protected Properties parse(ContentParser parser, String resourceName, long filesize)
      throws Exception {

    DataResource testResource = new ClasspathResource(ContentParserPptTest.class, resourceName,
        false);
    InputStream inputStream = testResource.openStream();
    try {
      return parser.parse(inputStream, filesize);
    } finally {
      inputStream.close();
    }
  }

  public void checkParser(ContentParser parser, long filesize) throws Exception {

    Properties properties = parse(parser, "test.ppt", filesize);
    String title = properties.getProperty(ContentParser.PROPERTY_KEY_TITLE);
    assertEquals("Title of Testslide", title);
    String author = properties.getProperty(ContentParser.PROPERTY_KEY_AUTHOR);
    assertEquals("J\u00F6rg Hohwiller", author);
    String keywords = properties.getProperty(ContentParser.PROPERTY_KEY_KEYWORDS);
    assertEquals("some keywords", keywords);
    String text = properties.getProperty(ContentParser.PROPERTY_KEY_TEXT);
    // System.out.println(text);
    assertTrue(text.contains("Titlecaption"));
    assertTrue(text.contains("Subscription"));
    assertTrue(text.contains("Slidetitle"));
    assertTrue(text.contains("Bullet"));
    assertTrue(text.contains("Hello world, this is a test."));
    assertTrue(text.contains("This is a note."));
  }

  @Test
  public void testParser() throws Exception {

    ContentParserPpt parser = new ContentParserPpt();
    checkParser(parser, 0);
    // parser.setMaximumBufferSize(4096);
    checkParser(parser, 9728);
  }

}
