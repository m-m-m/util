/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import java.util.Collection;

import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.indexer.api.config.ConfiguredSearchIndexerOptions;
import net.sf.mmm.search.indexer.api.config.SearchIndexDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.state.SearchIndexState;

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
   * @see #index(SearchIndexerConfiguration)
   * @see net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationReader#readConfiguration(String)
   * 
   * @param configurationLocation is the location where the configuration data
   *        shall be read from. This is typically a path into the filesystem.
   */
  void index(String configurationLocation);

  /**
   * This method performs the indexing for the given
   * {@link SearchIndexerConfiguration}.
   * 
   * @see #index(SearchIndexerConfiguration)
   * @see net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationReader#readConfiguration(String)
   * 
   * @param configurationLocation is the location where the configuration data
   *        shall be read from. This is typically a path into the filesystem.
   * @param options are the {@link ConfiguredSearchIndexerOptions}.
   */
  void index(String configurationLocation, ConfiguredSearchIndexerOptions options);

  /**
   * This method performs the indexing for the given
   * {@link SearchIndexerConfiguration}.
   * 
   * @see SearchIndexerConfiguration#getLocations()
   * @see #index(SearchIndexer, SearchIndexState, boolean,
   *      SearchIndexDataLocation, Collection)
   * 
   * @param configuration is the {@link SearchIndexerConfiguration} to crawl and
   *        add to the index.
   */
  void index(SearchIndexerConfiguration configuration);

  /**
   * This method performs the indexing for the given
   * {@link SearchIndexerConfiguration}.
   * 
   * @see SearchIndexerConfiguration#getLocations()
   * @see #index(SearchIndexer, SearchIndexState, boolean,
   *      SearchIndexDataLocation, Collection)
   * 
   * @param configuration is the {@link SearchIndexerConfiguration} to crawl and
   *        add to the index.
   * @param options are the {@link ConfiguredSearchIndexerOptions}.
   */
  void index(SearchIndexerConfiguration configuration, ConfiguredSearchIndexerOptions options);

  /**
   * This method performs the indexing for the given
   * {@link SearchIndexerConfiguration} limited to the
   * {@link SearchIndexerConfiguration#getLocations() locations} that are
   * {@link SearchIndexDataLocation#getSource() associated} with the given
   * <code>source</code>.
   * 
   * @param searchIndexer is the {@link SearchIndexer} used for modifying the
   *        index.
   * @param state is the {@link SearchIndexState}.
   * @param forceFullIndexing - <code>true</code> if the <code>location</code>
   *        should be entirely re-indexed (all existing entries for the
   *        according source/location are deleted and everything is indexed
   *        again from scratch), <code>false</code> if delta-indexing should be
   *        enabled so a potentially existing index is updated incremental.
   * @param configuration is the {@link SearchIndexerConfiguration} to crawl and
   *        add to the index.
   * @param source is the {@link SearchIndexerConfiguration#getSource(String)
   *        source} to index.
   * @see SearchIndexerConfiguration#getLocations()
   * @see #index(SearchIndexer, SearchIndexState, boolean,
   *      SearchIndexDataLocation, Collection)
   */
  void index(SearchIndexer searchIndexer, SearchIndexState state, boolean forceFullIndexing,
      SearchIndexerConfiguration configuration, SearchSource source);

  /**
   * This method indexes the given {@link SearchIndexDataLocation location}.<br/>
   * <b>NOTE:</b><br>
   * Unlike
   * {@link #index(SearchIndexer, SearchIndexState, boolean, SearchIndexerConfiguration, SearchSource)}
   * this method will NOT delete search-entries from the index in advance.
   * 
   * @param searchIndexer is the {@link SearchIndexer} used for modifying the
   *        index.
   * @param state is the {@link SearchIndexState}.
   * @param forceFullIndexing - <code>true</code> if the <code>location</code>
   *        should be entirely indexed again from scratch, <code>false</code> if
   *        delta-indexing should be enabled so a potentially existing index is
   *        updated incremental.
   * @param location is the {@link SearchIndexDataLocation} to crawl and add to
   *        the index.
   * @param resourceUris is a {@link Collection} where the URIs of the indexed
   *        resources are collected. It can be used to determine the entries
   *        from the index that need to be removed for delta-indexing.
   */
  void index(SearchIndexer searchIndexer, SearchIndexState state, boolean forceFullIndexing,
      SearchIndexDataLocation location, Collection<String> resourceUris);

}
