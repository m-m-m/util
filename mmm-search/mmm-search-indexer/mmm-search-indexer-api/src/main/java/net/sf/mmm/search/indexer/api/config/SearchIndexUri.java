/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.config;

import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the interface for the configuration of a {@link #getUri()
 * data-location} that can be indexed by the indexer and is then available for
 * search via the search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexUri {

  /**
   * This method gets the {@link SearchSource#getId() ID} of the
   * {@link SearchSource}. This name will be added to the search-index for each
   * according {@link net.sf.mmm.search.api.SearchEntry entry}. A
   * {@link net.sf.mmm.search.engine.api.SearchQuery} can be limited to specific
   * sources.
   * 
   * @see net.sf.mmm.search.api.SearchEntry#getSource()
   * 
   * @return the name of the source.
   */
  String getSource();

  /**
   * This method gets the URI pointing to the data.<br>
   * <ul>
   * <li>File-URLs should point to a directory to index (e.g.
   * "file:///data/documents")</li>
   * <li>HTTP(S)-URLs should point to a web-site to index via a crawler.</li>
   * </ul>
   * 
   * @return the URI.
   */
  String getUri();

  /**
   * This method gets the filter that {@link Filter#accept(Object) decides} if
   * some content (file, web-page, etc.) should be indexed or ignored.
   * 
   * @return the filter to use.
   */
  Filter<String> getFilter();

}
