/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl.strategy;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy;
import net.sf.mmm.search.indexer.base.strategy.AbstractSearchIndexerUpdateStrategy;
import net.sf.mmm.search.indexer.base.strategy.SearchIndexerUpdateStrategyManagerImpl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategyManager}
 * interface that can be used without
 * {@link net.sf.mmm.util.component.api.IocContainer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DefaultSearchIndexerUpdateStrategyManager extends
    SearchIndexerUpdateStrategyManagerImpl {

  /**
   * The constructor.
   */
  public DefaultSearchIndexerUpdateStrategyManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<? extends SearchIndexerUpdateStrategy> createSearchIndexerUpdateStrategies() {

    List<AbstractSearchIndexerUpdateStrategy> strategies = new ArrayList<AbstractSearchIndexerUpdateStrategy>();
    strategies.add(new SearchIndexerUpdateStrategyNone());
    strategies.add(new SearchIndexerUpdateStrategyLastModified());
    strategies.add(new SearchIndexerUpdateStrategyVcs());
    for (AbstractSearchIndexerUpdateStrategy strategy : strategies) {
      strategy.initialize();
    }
    return strategies;
  }

}
