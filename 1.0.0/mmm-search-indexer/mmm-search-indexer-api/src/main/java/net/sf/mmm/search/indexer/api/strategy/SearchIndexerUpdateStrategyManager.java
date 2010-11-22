/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.strategy;

import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for the component used to
 * {@link #getStrategy(SearchIndexerSource) get} the proper
 * {@link SearchIndexerUpdateStrategy}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface SearchIndexerUpdateStrategyManager {

  /**
   * This method will create the {@link SearchIndexerUpdateStrategy} for the
   * given <code>source</code>.
   * 
   * @param source is the {@link SearchIndexerSource} to index (incremental)
   *        according to its {@link SearchIndexerSource#getUpdateStrategy()
   *        update-strategy}.
   * @return the according {@link SearchIndexerUpdateStrategy}.
   */
  SearchIndexerUpdateStrategy getStrategy(SearchIndexerSource source);

}
