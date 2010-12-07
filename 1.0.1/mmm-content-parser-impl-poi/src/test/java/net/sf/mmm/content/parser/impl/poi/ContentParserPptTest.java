/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.poi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.util.context.api.GenericContext;

import org.junit.Test;

/**
 * This is the test-case for {@link ContentParserPpt}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ContentParserPptTest extends AbstractContentParserPoiTest {

  public void checkParser(ContentParser parser, long filesize) throws Exception {

    GenericContext properties = parse(parser, "test.ppt", filesize);
    String title = properties.getVariable(ContentParser.VARIABLE_NAME_TITLE, String.class);
    assertEquals("Title of Testslide", title);
    String author = properties.getVariable(ContentParser.VARIABLE_NAME_CREATOR, String.class);
    assertEquals("J\u00F6rg Hohwiller", author);
    String keywords = properties.getVariable(ContentParser.VARIABLE_NAME_KEYWORDS, String.class);
    assertEquals("some keywords", keywords);
    String text = properties.getVariable(ContentParser.VARIABLE_NAME_TEXT, String.class);
    // System.out.println(text);
    assertTrue(text.contains("Titlecaption"));
    assertTrue(text.contains("Subscription"));
    assertTrue(text.contains("Slidetitle"));
    assertTrue(text.contains("Bullet"));
    assertTrue(text.contains("Hello world, this is a test."));
    assertTrue(text.contains("This is a note."));
  }

  @Override
  protected ContentParser getContentParser() {

    ContentParserPpt parser = new ContentParserPpt();
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
