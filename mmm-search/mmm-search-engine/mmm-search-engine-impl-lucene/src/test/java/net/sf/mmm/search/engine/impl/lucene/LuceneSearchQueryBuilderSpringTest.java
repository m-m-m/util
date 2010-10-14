/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import net.sf.mmm.util.component.impl.SpringContainerPool;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LuceneSearchQueryBuilderSpringTest extends LuceneSearchQueryBuilderTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected LuceneSearchQueryBuilder getQueryBuilder() {

    return SpringContainerPool.getInstance().getComponent(LuceneSearchQueryBuilder.class);
  }

}
