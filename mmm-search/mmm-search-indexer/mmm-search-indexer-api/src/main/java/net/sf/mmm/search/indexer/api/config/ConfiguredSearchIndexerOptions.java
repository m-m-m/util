/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.config;

import java.util.List;

import net.sf.mmm.search.indexer.api.SearchIndexerOptions;

/**
 * This is the interface for the additional options to use for the
 * {@link net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer}.
 * 
 * @see net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer#index(String,
 *      ConfiguredSearchIndexerOptions)
 * @see net.sf.mmm.search.indexer.base.config.ConfiguredSearchIndexerOptionsBean
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ConfiguredSearchIndexerOptions extends SearchIndexerOptions {

  /**
   * This method determines if the search-index will be
   * {@link net.sf.mmm.search.indexer.api.SearchIndexer#optimize() optimized}
   * after indexing.
   * 
   * @return <code>true</code> if the search-index will be optimized,
   *         <code>false</code> otherwise.
   */
  boolean isOptimize();

  /**
   * This method determines if all existing
   * {@link net.sf.mmm.search.api.SearchEntry entries} for the
   * {@link #getSourceIds() sources} to index shall be deleted from the
   * search-index and (re-)indexed from scratch. If {@link #getSourceIds()
   * source-IDs} are NOT set (<code>null</code>), this is the same as
   * {@link #isOverwriteIndex()}.
   * 
   * @return <code>true</code> if existing
   *         {@link net.sf.mmm.search.api.SearchEntry entries} shall be
   *         overwritten, <code>false</code> if existing
   *         {@link net.sf.mmm.search.api.SearchEntry entries} shall remain (and
   *         will be updated incremental).
   */
  boolean isOverwriteEntries();

  /**
   * This method gets the optional {@link List} of
   * {@link net.sf.mmm.search.api.config.SearchSource#getId() source-IDs} that
   * shall be (re-)indexed. If the {@link List} is NOT <code>null</code>
   * (default) all sources will be indexed, otherwise
   * {@link net.sf.mmm.search.api.config.SearchSource sources}
   * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration#getSources()
   * configured} but NOT contained in this {@link List} will NOT be indexed.
   * 
   * @return the {@link List} of
   *         {@link net.sf.mmm.search.api.config.SearchSource#getId()
   *         source-IDs} or <code>null</code> to index all sources.
   */
  List<String> getSourceIds();

}
