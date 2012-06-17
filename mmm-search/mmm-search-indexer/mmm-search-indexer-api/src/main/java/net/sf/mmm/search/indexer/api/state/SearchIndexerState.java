/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.state;

import java.util.Date;

/**
 * This is the interface for the state of the
 * {@link net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer}. It is used to
 * figure out the delta when updating the index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexerState {

  /**
   * This method gets the {@link SearchIndexerSourceState} for the given
   * <code>source</code>.<br/>
   * For each
   * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration#getSources()
   * configured source} a {@link SearchIndexerSourceState source-state} will be
   * available.
   * 
   * @param source is the
   *        {@link net.sf.mmm.search.api.config.SearchSource#getId() ID} of the
   *        {@link net.sf.mmm.search.api.config.SearchSource source} for which
   *        the state is requested.
   * @return the {@link SearchIndexerSourceState} of the given
   *         <code>source</code> or <code>null</code> if no such state exists.
   */
  SearchIndexerSourceState getSourceState(String source);

  /**
   * This method gets the {@link Date} when the index had been updated last
   * time. It will be set the the current {@link Date} every time this state is
   * {@link SearchIndexerStateHolder#flush() saved}.
   * 
   * @return the last indexing date or <code>null</code> if the index did NOT
   *         exist before.
   */
  Date getIndexingDate();

}
