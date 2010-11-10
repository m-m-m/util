/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import net.sf.mmm.search.indexer.api.config.ConfiguredSearchIndexerOptions;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.search.indexer.api.state.SearchIndexerState;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a high-level {@link SearchIndexer}. It recursively
 * crawls {@link net.sf.mmm.util.resource.api.BrowsableResource directories},
 * performs
 * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation#getFilter()
 * filtering},
 * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation#getUriTransformer()
 * URI-transformations}. It will use the {@link ResourceSearchIndexer} for
 * indexing of single {@link net.sf.mmm.util.resource.api.DataResource
 * resources}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface ConfiguredSearchIndexer {

  /**
   * This method reads the {@link SearchIndexerConfiguration} from the given
   * <code>configurationLocation</code> and performs the
   * {@link #index(SearchIndexerConfiguration) indexing}.
   * 
   * @see #index(SearchIndexerConfiguration)
   * @see net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationReader#readConfiguration(String)
   * 
   * @param configurationLocation is the location where the configuration data
   *        shall be read from. This is typically a path into the filesystem.
   */
  void index(String configurationLocation);

  /**
   * This method reads the {@link SearchIndexerConfiguration} from the given
   * <code>configurationLocation</code> and performs the
   * {@link #index(SearchIndexerConfiguration, ConfiguredSearchIndexerOptions)
   * indexing}.
   * 
   * @see #index(SearchIndexerConfiguration, ConfiguredSearchIndexerOptions)
   * @see net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationReader#readConfiguration(String)
   * 
   * @param configurationLocation is the location where the configuration data
   *        shall be read from. This is typically a path into the filesystem.
   * @param options are the {@link ConfiguredSearchIndexerOptions}.
   */
  void index(String configurationLocation, ConfiguredSearchIndexerOptions options);

  /**
   * This method performs the indexing of all
   * {@link SearchIndexerConfiguration#getSources() sources} given by
   * <code>configuration</code>.
   * 
   * @see SearchIndexerSource#getLocations()
   * 
   * @param configuration is the {@link SearchIndexerConfiguration} to crawl and
   *        add to the index.
   */
  void index(SearchIndexerConfiguration configuration);

  /**
   * This method performs the indexing for the given <code>configuration</code>
   * and <code>options</code>.
   * 
   * @see #index(SearchIndexerConfiguration)
   * @see ConfiguredSearchIndexerOptions#getSourceIds()
   * 
   * @param configuration is the {@link SearchIndexerConfiguration} to crawl and
   *        add to the index.
   * @param options are the {@link ConfiguredSearchIndexerOptions}.
   */
  void index(SearchIndexerConfiguration configuration, ConfiguredSearchIndexerOptions options);

  /**
   * This method performs the indexing for the given {@link SearchIndexerSource
   * source}.
   * 
   * @param source is the {@link SearchIndexerConfiguration#getSource(String)
   *        source} to index.
   * @param options are the {@link ConfiguredSearchIndexerOptions}.
   * @param state is the {@link SearchIndexerState}.
   * @param searchIndexer is the {@link SearchIndexer} used for modifying the
   *        index.
   * 
   * @see SearchIndexerSource#getLocations()
   */
  void index(SearchIndexerSource source, ConfiguredSearchIndexerOptions options,
      SearchIndexerState state, SearchIndexer searchIndexer);

  // /**
  // * This method indexes the given {@link SearchIndexDataLocation
  // location}.<br/>
  // * <b>NOTE:</b><br>
  // * Unlike
  // * {@link #index(SearchIndexerConfiguration, ConfiguredSearchIndexerOptions,
  // SearchIndexState, SearchIndexer, SearchIndexerSource)}
  // * this method will NOT delete search-entries from the index in advance.
  // *
  // * @param location is the {@link SearchIndexDataLocation} to crawl and add
  // to
  // * the index.
  // * @param state is the {@link SearchIndexState}.
  // * @param searchIndexer is the {@link SearchIndexer} used for modifying the
  // * index.
  // */
  // void index(SearchIndexDataLocation location, SearchIndexState state,
  // SearchIndexer searchIndexer);

}
