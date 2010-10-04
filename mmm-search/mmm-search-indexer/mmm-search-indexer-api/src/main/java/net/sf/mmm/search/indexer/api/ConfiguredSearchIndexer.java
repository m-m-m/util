/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import net.sf.mmm.search.indexer.api.config.SearchIndexDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;

/**
 * This is the interface for a high-level {@link SearchIndexer}. It recursively
 * crawls {@link net.sf.mmm.util.resource.api.BrowsableResource directories},
 * performs {@link SearchIndexDataLocation#getFilter() filtering},
 * {@link SearchIndexDataLocation#getUriTransformer() URI-transformations} and
 * {@link net.sf.mmm.content.parser.api.ContentParser metadata extraction}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ConfiguredSearchIndexer {

  /**
   * This method performs the indexing for the given
   * {@link SearchIndexerConfiguration}.
   * 
   * @see SearchIndexerConfiguration#getLocations()
   * @see #index(SearchIndexer, SearchIndexDataLocation)
   * 
   * @param configuration is the {@link SearchIndexerConfiguration} to crawl and
   *        add to the index.
   */
  void index(SearchIndexerConfiguration configuration);

  /**
   * This method indexes the given {@link SearchIndexDataLocation location}.
   * @param searchIndexer TODO
   * @param location is the {@link SearchIndexDataLocation} to crawl and add to
   *        the index.
   */
  void index(SearchIndexer searchIndexer, SearchIndexDataLocation location);

  /**
   * This method performs the indexing for the given
   * {@link SearchIndexerConfiguration}.
   * 
   * @see SearchIndexerConfiguration#getLocations()
   * @see #index(SearchIndexerConfiguration)
   * 
   * @param configurationFile is the {@link java.io.File#getPath() path} to the
   *        configuration file containing the {@link SearchIndexerConfiguration}
   *        as XML.
   */
  void index(String configurationFile);

}
