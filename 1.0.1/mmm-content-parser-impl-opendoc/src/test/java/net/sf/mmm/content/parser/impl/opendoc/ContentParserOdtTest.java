/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.opendoc;

import static org.junit.Assert.assertTrue;
import net.sf.mmm.content.parser.api.ContentParser;

import org.junit.Test;

/**
 * This is the test-case for {@link ContentParserOdt}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class ContentParserOdtTest extends AbstractContentParserOpenDocTest {

  /**
   * The constructor.
   */
  public ContentParserOdtTest() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContentParser getContentParser() {

    ContentParserOdt parser = new ContentParserOdt();
    parser.initialize();
    return parser;
  }

  @Test
  public void testParser() throws Exception {

    String text = parseAndCheckMetadata(getContentParser(), "test.odt", null);
    assertTrue(text.contains("Title"));
    assertTrue(text.contains("Header"));
    assertTrue(text.contains("Footer"));
    assertTrue(text.contains("Footnote"));
    assertTrue(text.contains("Hello world, this is a test."));
  }

}
