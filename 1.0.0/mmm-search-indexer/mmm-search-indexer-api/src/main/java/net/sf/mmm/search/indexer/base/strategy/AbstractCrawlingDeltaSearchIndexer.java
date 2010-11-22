/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.strategy;

import net.sf.mmm.search.indexer.api.EntryUpdateVisitor;
import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.search.indexer.api.strategy.UpdateStrategyArguments;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy}
 * that recursively crawls
 * {@link net.sf.mmm.util.resource.api.BrowsableResource resources}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractCrawlingDeltaSearchIndexer extends BasicSearchIndexerUpdateStrategy {

  /**
   * The constructor.
   * 
   * @param responsibleStrategies are the
   *        {@link net.sf.mmm.search.indexer.api.config.SearchIndexerSource#getUpdateStrategy()
   *        update-strategies} this
   *        {@link net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy}
   *        {@link #isResponsible(net.sf.mmm.search.indexer.api.config.SearchIndexerSource)
   *        is responsible} for.
   */
  public AbstractCrawlingDeltaSearchIndexer(String... responsibleStrategies) {

    super(responsibleStrategies);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void index(UpdateStrategyArguments arguments, SearchIndexerDataLocation location, EntryUpdateVisitor entryUpdateVisitor) {

    indexRecursive(arguments, location, entryUpdateVisitor);
  }

}
