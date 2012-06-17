/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.state;

import java.util.Date;

/**
 * This is the interface for the state of the
 * {@link net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer} for a
 * particular {@link #getSource() source}.
 * 
 * @see SearchIndexerState
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexerSourceState {

  /**
   * This method gets the
   * {@link net.sf.mmm.search.api.config.SearchSource#getId() ID} of the
   * according {@link net.sf.mmm.search.api.config.SearchSource source}.
   * 
   * @return the {@link net.sf.mmm.search.api.config.SearchSource#getId() ID} of
   *         the according {@link net.sf.mmm.search.api.config.SearchSource
   *         source}.
   */
  String getSource();

  /**
   * This method gets the {@link Date} when the {@link #getSource() source} had
   * been indexed last time.
   * 
   * @return the last indexing date or <code>null</code> if the
   *         {@link #getSource() source} has NOT yet been indexed.
   */
  Date getIndexingDate();

  /**
   * This method sets the {@link #getIndexingDate() indexing-date}. It should be
   * called after the {@link #getSource() source} has been completely updated in
   * the search-index.
   * 
   * @param indexingDate is the new indexing-date.
   */
  void setIndexingDate(Date indexingDate);

  /**
   * This method gets the {@link SearchIndexerDataLocationState state} for the
   * given <code>location</code>.<br/>
   * For each
   * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerSource#getLocations()
   * configured data-location} a {@link SearchIndexerDataLocationState} will be
   * available.
   * 
   * @param location is the
   *        {@link net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation#getLocationUri()
   *        location} of the
   *        {@link net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation
   *        data-location} for which the state is requested.
   * @return the {@link SearchIndexerDataLocationState} of the given
   *         <code>location</code> or <code>null</code> if no such state exists.
   */
  SearchIndexerDataLocationState getLocationState(String location);

}
