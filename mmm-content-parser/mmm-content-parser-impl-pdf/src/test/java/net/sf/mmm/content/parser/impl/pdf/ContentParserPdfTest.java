/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.pdf;

import java.io.InputStream;
import java.util.Properties;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link ContentParserPpt}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ContentParserPdfTest {

  protected Properties parse(ContentParser parser, String resourceName, long filesize)
      throws Exception {

    DataResource testResource = new ClasspathResource(ContentParserPdfTest.class, resourceName,
        false);
    InputStream inputStream = testResource.openStream();
    try {
      return parser.parse(inputStream, filesize);
    } finally {
      inputStream.close();
    }
  }

  public void checkParser(ContentParser parser, long filesize) throws Exception {

    Properties properties = parse(parser, "test.pdf", filesize);
    String title = properties.getProperty(ContentParser.PROPERTY_KEY_TITLE);
    Assert.assertEquals("test", title);
    String author = properties.getProperty(ContentParser.PROPERTY_KEY_AUTHOR);
    Assert.assertEquals("hohwille", author);
    String text = properties.getProperty(ContentParser.PROPERTY_KEY_TEXT);
    Assert.assertTrue(text.contains("Title"));
    Assert.assertTrue(text.contains("Header"));
    Assert.assertTrue(text.contains("Footer"));
    Assert.assertTrue(text.contains("Footnote"));
    Assert.assertTrue(text.contains("Hello world, this is a test."));
    Assert.assertTrue(text.contains("Kommentar [jh1]: This is a \r\ncomment."));
  }

  protected ContentParser getContentParser() {

    ContentParserPdf parser = new ContentParserPdf();
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
