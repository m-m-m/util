/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl;

import net.sf.mmm.content.parser.api.ContentParserService;
import net.sf.mmm.util.component.base.SpringConfigs;
import net.sf.mmm.util.component.impl.SpringContainerPool;

/**
 * This is the test-case for {@link ContentParserService} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserServiceSpringTest extends ContentParserServiceImplTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContentParserService getContentParserService() {

    return SpringContainerPool.getInstance(SpringConfigs.SPRING_XML_CONTENT_PARSER).get(
        ContentParserService.class);
  }

}
