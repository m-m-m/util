/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.strategy;

import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.config.ConfiguredSearchIndexerOptions;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.search.indexer.api.state.SearchIndexerSourceState;
import net.sf.mmm.util.context.api.MutableGenericContext;

/**
 * This is the interface with the properties required for the
 * {@link SearchIndexerUpdateStrategy}. It encapsulates the state of indexing
 * and thereby allows a {@link SearchIndexerUpdateStrategy} to be stateless and
 * thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UpdateStrategyArguments {

  /**
   * This method gets the {@link MutableGenericContext}.
   * 
   * @return the {@link MutableGenericContext}.
   */
  MutableGenericContext getContext();

  /**
   * This method gets the {@link SearchIndexer}.
   * 
   * @return the {@link SearchIndexer}.
   */
  SearchIndexer getIndexer();

  /**
   * This method gets the {@link ConfiguredSearchIndexerOptions}.
   * 
   * @return the {@link ConfiguredSearchIndexerOptions}.
   */
  ConfiguredSearchIndexerOptions getOptions();

  /**
   * This method gets the {@link SearchIndexerSource} to index (incremental)
   * according to its {@link SearchIndexerSource#getUpdateStrategy()
   * update-type}.
   * 
   * @return the {@link SearchIndexerSource}.
   */
  SearchIndexerSource getSource();

  /**
   * This method gets the {@link SearchIndexerSourceState} for the
   * {@link #getSource() source}.
   * 
   * @return the {@link SearchIndexerSourceState}.
   */
  SearchIndexerSourceState getSourceState();

}
