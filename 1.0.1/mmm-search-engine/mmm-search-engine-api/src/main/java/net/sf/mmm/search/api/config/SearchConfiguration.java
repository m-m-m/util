/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api.config;

import java.util.Collection;

/**
 * This is the interface for the configuration of the entire search (e.g. for
 * the {@link net.sf.mmm.search.engine.api.SearchEngine}).<br>
 * You will typically provide your configuration as XML in the
 * {@link #DEFAULT_CONFIGURATION_URL default configuration file}. The
 * base-implementation comes with an according (un)marshaler.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface SearchConfiguration {

  /** The default location of the configuration for the search. */
  String DEFAULT_CONFIGURATION_URL = "file://~/.mmm/search.xml";

  /**
   * This method gets the {@link SearchIndexConfiguration}.
   * 
   * @return the {@link SearchIndexConfiguration}.
   */
  SearchIndexConfiguration getSearchIndex();

  /**
   * This method gets the {@link Collection} of {@link SearchSource sources}.
   * 
   * @return the {@link SearchSource}s.
   */
  Collection<? extends SearchSource> getSources();

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
   * This method gets the general purpose {@link SearchProperties}.
   * 
   * @return the {@link SearchProperties}.
   */
  SearchProperties getProperties();

  /**
   * This method gets the {@link SearchFields}.
   * 
   * @return the {@link SearchFields}.
   */
  SearchFields getFields();

}
