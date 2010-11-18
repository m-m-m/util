/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.pdf;

import java.io.InputStream;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link ContentParserPdf}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ContentParserPdfTest {

  protected GenericContext parse(ContentParser parser, String resourceName, long filesize)
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

    GenericContext properties = parse(parser, "test.pdf", filesize);
    String title = properties.getVariable(ContentParser.VARIABLE_NAME_TITLE, String.class);
    Assert.assertEquals("test", title);
    String author = properties.getVariable(ContentParser.VARIABLE_NAME_CREATOR, String.class);
    Assert.assertEquals("hohwille", author);
    String text = properties.getVariable(ContentParser.VARIABLE_NAME_TEXT, String.class);
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
