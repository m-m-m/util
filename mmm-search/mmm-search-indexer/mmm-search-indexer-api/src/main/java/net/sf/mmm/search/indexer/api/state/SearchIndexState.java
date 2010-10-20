/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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
public interface SearchIndexState {

  /**
   * This method gets the {@link SearchIndexSourceState} for the given
   * <code>source</code>.<br/>
   * For each
   * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration#getSources()
   * configured source} a {@link SearchIndexSourceState source-state} will be
   * available.
   * 
   * @param source is the
   *        {@link net.sf.mmm.search.api.config.SearchSource#getId() ID} of the
   *        {@link net.sf.mmm.search.api.config.SearchSource source} for which
   *        the state is requested.
   * @return the {@link SearchIndexSourceState} of the given <code>source</code>
   *         or <code>null</code> if no such state exists.
   */
  SearchIndexSourceState getSourceState(String source);

  /**
   * This method gets the {@link SearchIndexDataLocationState state} for the
   * given <code>location</code>.<br/>
   * For each
   * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration#getLocations()
   * configured data-location} a {@link SearchIndexDataLocationState} will be
   * available.
   * 
   * @param location is the
   *        {@link net.sf.mmm.search.indexer.api.config.SearchIndexDataLocation#getLocation()
   *        location} of the
   *        {@link net.sf.mmm.search.indexer.api.config.SearchIndexDataLocation
   *        data-location} for which the state is requested.
   * @return the {@link SearchIndexDataLocationState} of the given
   *         <code>location</code> or <code>null</code> if no such state exists.
   */
  SearchIndexDataLocationState getLocationState(String location);

  /**
   * This method gets the {@link Date} when the index had been updated last
   * time. It will be set the the current {@link Date} every time this state is
   * {@link SearchIndexStateManager#save(SearchIndexState) saved}.
   * 
   * @return the last indexing date or <code>null</code> if the index did NOT
   *         exist before.
   */
  Date getIndexingDate();

}
