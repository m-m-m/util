/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.html;

import java.io.InputStream;
import java.util.Properties;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is the test-case for {@link ContentParserHtml}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ContentParserHtmlTest {

  protected Properties parse(ContentParser parser, String resourceName, long filesize)
      throws Exception {

    DataResource testResource = new ClasspathResource(ContentParserHtmlTest.class, resourceName,
        false);
    InputStream inputStream = testResource.openStream();
    try {
      return parser.parse(inputStream, filesize);
    } finally {
      inputStream.close();
    }
  }

  public void checkParser(ContentParser parser, long filesize) throws Exception {

    Properties properties = parse(parser, "test.html", filesize);
    String title = properties.getProperty(ContentParser.PROPERTY_KEY_TITLE);
    Assert.assertEquals("This is the title!", title);
    String author = properties.getProperty(ContentParser.PROPERTY_KEY_AUTHOR);
    Assert.assertEquals("Jörg Hohwiller", author);
    String keywords = properties.getProperty(ContentParser.PROPERTY_KEY_KEYWORDS);
    Assert.assertEquals("Some keywords, and others", keywords);
    String text = properties.getProperty(ContentParser.PROPERTY_KEY_TEXT);
    Assert.assertTrue(text.contains("Body title"));
    Assert.assertTrue(text.contains("Hello World!"));
  }

  protected ContentParser getContentParser() {

    ContentParserHtml parser = new ContentParserHtml();
    parser.initialize();
    return parser;
  }

  @Test
  public void testParserPlain() throws Exception {

    ContentParser parser = getContentParser();
    checkParser(parser, 0);
  }

  @Ignore("Jtidy has encoding bug, update?")
  @Test
  public void testParserJtidy() throws Exception {

    ContentParser parser = getContentParser();
    checkParser(parser, 9728);
  }

}
