/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.poi;

import net.sf.mmm.content.parser.api.ContentParser;

/**
 * This is the test-case for {@link ContentParserDocx} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserDocxSpringTest extends ContentParserDocxTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContentParser getContentParser() {

    return getContainer().get(ContentParserDocx.class);
  }

}
