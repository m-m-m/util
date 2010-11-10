/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl.strategy;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.search.indexer.api.strategy.UpdateStrategyArguments;
import net.sf.mmm.search.indexer.base.strategy.AbstractCrawlingDeltaSearchIndexer;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy}
 * for {@link SearchIndexerSource#UPDATE_STRATEGY_NONE}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchIndexerUpdateStrategyNone extends AbstractCrawlingDeltaSearchIndexer {

  /**
   * The constructor.
   */
  public SearchIndexerUpdateStrategyNone() {

    super(SearchIndexerSource.UPDATE_STRATEGY_NONE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isFullIndexing(UpdateStrategyArguments arguments) {

    return true;
  }

}
