/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.search.parser.api.ContentParserService;

/**
 * This is the test-case for {@link ContentParserService} configured using
 * spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserServiceSpringTest extends ContentParserServiceImplTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContentParserService getContentParserService() {

    return SpringContainerPool.getContainer("net/sf/mmm/search/parser/beans-search-parser.xml")
        .getComponent(ContentParserService.class);
  }

}
