/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.state;

import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a manager that can
 * {@link #load(SearchIndexerConfiguration) load} and
 * {@link #save(SearchIndexerState) save} the {@link SearchIndexerState}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface SearchIndexerStateManager {

  /**
   * This method loads the {@link SearchIndexerState}.<br/>
   * Please avoid multiple calls of this method for the same configuration as it
   * is unspecified if the implementation will reload the state on each call.
   * 
   * @param indexerConfiguration is the {@link SearchIndexerConfiguration}. It
   *        will be used to create according the
   *        {@link SearchIndexerState#getSourceState(String) source-state} objects
   *        and to determine the location where to load the state from.
   * @return the loaded {@link SearchIndexerState}.
   */
  SearchIndexerState load(SearchIndexerConfiguration indexerConfiguration);

  /**
   * This method saves the given {@link SearchIndexerState}.
   * 
   * @param searchIndexerState is the state to save.
   */
  void save(SearchIndexerState searchIndexerState);

}
