/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.search.parser.api.ContentParser;

/**
 * This is the test-case for {@link ContentParserPpt} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserXlsSpringTest extends ContentParserXlsTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContentParser getContentParser() {

    return SpringContainerPool.getContainer("net/sf/mmm/search/parser/beans-search-parser-poi.xml")
        .getComponent(ContentParser.class, ContentParser.class.getName() + "-xls");
  }

}
