/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import net.sf.mmm.search.engine.api.SearchEngineBuilder;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationLoader;
import net.sf.mmm.util.component.base.SpringConfigs;
import net.sf.mmm.util.component.impl.SpringContainerPool;

/**
 * This is the test-case for {@link LuceneSearchEngine} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LuceneSearchEngineSpringTest extends LuceneSearchEngineTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected SearchEngineBuilder getSearchEngineBuilder() {

    return SpringContainerPool.getInstance(SpringConfigs.SPRING_XML_SEARCH).get(
        LuceneSearchEngineBuilder.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected SearchEngineConfigurationLoader getSearchEngineConfigurationLoader() {

    return SpringContainerPool.getInstance(SpringConfigs.SPRING_XML_SEARCH).get(
        SearchEngineConfigurationLoader.class);
  }

}
