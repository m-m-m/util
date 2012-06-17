/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.text;

import java.io.InputStream;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link ContentParserTextMarkupAware}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ContentParserTextMarkupAwareTest {

  protected GenericContext parse(ContentParser parser, String resourceName, long filesize)
      throws Exception {

    DataResource testResource = new ClasspathResource(ContentParserTextMarkupAwareTest.class,
        resourceName, false);
    InputStream inputStream = testResource.openStream();
    try {
      return parser.parse(inputStream, filesize);
    } finally {
      inputStream.close();
    }
  }

  public void checkParser(ContentParser parser, long filesize) throws Exception {

    GenericContext properties = parse(parser, "test.txt", filesize);
    String text = properties.getVariable(ContentParser.VARIABLE_NAME_TEXT, String.class);
    Assert.assertTrue(text.contains("Hello World!"));
    Assert.assertFalse(text.contains("foo"));
  }

  protected ContentParser getContentParser() {

    ContentParserTextMarkupAware parser = new ContentParserTextMarkupAware();
    parser.initialize();
    return parser;
  }

  @Test
  public void testParserPlain() throws Exception {

    ContentParser parser = getContentParser();
    checkParser(parser, 0);
  }

}
