/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationHolder;
import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for the component that allows to create the {@link SearchIndexer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ComponentSpecification
public interface SearchIndexerBuilder {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.search.indexer.api.SearchIndexerBuilder";

  /**
   * This method opens a new {@link SearchIndexer}.<br>
   * <b>ATTENTION:</b><br>
   * Only one {@link SearchIndexer indexer} should be open at a time (for the same <code>dataSource</code>).
   * You have to {@link SearchIndexer#close() close} the indexer before calling this method again.<br>
   * <b>WARNING:</b><br>
   * Be very careful when setting <code>update</code> to <code>false</code> to avoid loosing valuable data.
   * 
   * @param dataSource is a string identifying the data source where the index is persisted. This is typically
   *        the absolute path to the index in the local filesystem.
   * @param options are the {@link net.sf.mmm.search.indexer.api.SearchIndexerOptions}.
   * @return the create indexer.
   * @throws SearchException if the operation failed.
   */
  SearchIndexer createIndexer(String dataSource, SearchIndexerOptions options) throws SearchException;

  /**
   * This method opens a new {@link SearchIndexer}.<br>
   * <b>ATTENTION:</b><br>
   * Only one {@link SearchIndexer indexer} should be open at a time (for the same
   * {@link net.sf.mmm.search.api.config.SearchIndexConfiguration#getLocation() location}). You have to
   * {@link SearchIndexer#close() close} the indexer before calling this method again.<br>
   * <b>ATTENTION:</b><br>
   * Be careful when overwriting the search-index. You may loose valuable data.
   * 
   * @param configurationHolder is the {@link SearchIndexerConfigurationHolder}.
   * @return the create indexer.
   * @throws SearchException if the operation failed.
   */
  SearchIndexer createIndexer(SearchIndexerConfigurationHolder configurationHolder) throws SearchException;

  /**
   * This method opens a new {@link SearchIndexer}.<br>
   * <b>ATTENTION:</b><br>
   * Only one {@link SearchIndexer indexer} should be open at a time (for the same
   * {@link net.sf.mmm.search.api.config.SearchIndexConfiguration#getLocation() location}). You have to
   * {@link SearchIndexer#close() close} the indexer before calling this method again.<br>
   * <b>ATTENTION:</b><br>
   * Be careful when overwriting the search-index. You may loose valuable data.
   * 
   * @param configurationHolder is the {@link SearchIndexerConfigurationHolder}.
   * @param options are the {@link net.sf.mmm.search.indexer.api.SearchIndexerOptions}.
   * @return the create indexer.
   * @throws SearchException if the operation failed.
   */
  SearchIndexer createIndexer(SearchIndexerConfigurationHolder configurationHolder, SearchIndexerOptions options)
      throws SearchException;

}
