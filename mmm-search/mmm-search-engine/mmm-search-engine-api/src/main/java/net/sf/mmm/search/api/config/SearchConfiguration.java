/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api.config;

import java.util.List;

/**
 * This is the interface for the configuration of the entire search (e.g. for
 * the {@link net.sf.mmm.search.engine.api.SearchEngine}).<br>
 * You will typically provide your configuration as XML. The base-implementation
 * comes with an according (un)marshaler.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchConfiguration {

  /**
   * This method gets the {@link List} of {@link SearchSource sources}.
   * 
   * @return the {@link SearchSource}s.
   */
  List<? extends SearchSource> getSources();

  /**
   * This method gets the {@link SearchSource} with the given
   * {@link SearchSource#getId() ID}.
   * 
   * @param id is the {@link SearchSource#getId() ID} of the requested
   *        {@link SearchSource}.
   * @return the requested {@link SearchSource} or <code>null</code> if none is
   *         configured for the given <code>id</code>.
   */
  SearchSource getSource(String id);

  /**
   * This method gets the {@link SearchIndexConfiguration}.
   * 
   * @return the {@link SearchIndexConfiguration}.
   */
  SearchIndexConfiguration getSearchIndex();

}
