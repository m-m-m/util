/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.poi;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.impl.poi.ContentParserPpt;
import net.sf.mmm.framework.base.SpringContainerPool;

/**
 * This is the test-case for {@link ContentParserPpt} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserPptSpringTest extends ContentParserPptTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContentParser getContentParser() {

    return SpringContainerPool.getInstance().getComponent(ContentParser.class,
        ContentParser.class.getName() + "-ppt");
  }

}
