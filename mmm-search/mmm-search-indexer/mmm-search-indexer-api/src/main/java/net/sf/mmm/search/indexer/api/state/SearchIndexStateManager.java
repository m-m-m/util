/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.state;

import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;

/**
 * This is the interface for a manager that can
 * {@link #load(SearchIndexerConfiguration) load} and
 * {@link #save(SearchIndexState) save} the {@link SearchIndexState}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexStateManager {

  /**
   * This method loads the {@link SearchIndexState}.<br/>
   * Please avoid multiple calls of this method for the same configuration as it
   * is unspecified if the implementation will reload the state on each call.
   * 
   * @param indexerConfiguration is the {@link SearchIndexerConfiguration}. It
   *        will be used to create according the
   *        {@link SearchIndexState#getSourceState(String) source-state}
   *        objects and to determine the location where to load the state from.
   * @return the loaded {@link SearchIndexState}.
   */
  SearchIndexState load(SearchIndexerConfiguration indexerConfiguration);

  /**
   * This method saves the given {@link SearchIndexState}.
   * 
   * @param searchIndexState is the state to save.
   */
  void save(SearchIndexState searchIndexState);

}
