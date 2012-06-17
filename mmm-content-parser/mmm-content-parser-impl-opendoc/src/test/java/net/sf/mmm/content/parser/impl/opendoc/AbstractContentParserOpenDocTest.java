/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.opendoc;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * This is the abstract test-class for testing subclasses of
 * {@link AbstractContentParserOpenDoc}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public abstract class AbstractContentParserOpenDocTest {

  protected String parseAndCheckMetadata(ContentParser parser, String resourceName, Long filesize)
      throws Exception {

    GenericContext context = parse(parser, resourceName, filesize);
    checkMetadata(context);
    return context.getVariable(ContentParser.VARIABLE_NAME_TEXT, String.class);
  }

  protected void checkMetadata(GenericContext context) {

    String title = context.getVariable(ContentParser.VARIABLE_NAME_TITLE, String.class);
    assertEquals("Title of Testdocument", title);
    String author = context.getVariable(ContentParser.VARIABLE_NAME_CREATOR, String.class);
    assertEquals("J\u00F6rg Hohwiller", author);
    String keywords = context.getVariable(ContentParser.VARIABLE_NAME_KEYWORDS, String.class);
    assertEquals("some keywords", keywords);
    String text = context.getVariable(ContentParser.VARIABLE_NAME_TEXT, String.class);
  }

  protected GenericContext parse(ContentParser parser, String resourceName, Long filesize)
      throws Exception {

    DataResource testResource = new ClasspathResource(AbstractContentParserOpenDocTest.class,
        resourceName, false);
    long size;
    if (filesize == null) {
      size = testResource.getSize();
    } else {
      size = filesize.longValue();
    }
    InputStream inputStream = testResource.openStream();
    try {
      return parser.parse(inputStream, size);
    } finally {
      inputStream.close();
    }
  }

  protected abstract ContentParser getContentParser();

}
