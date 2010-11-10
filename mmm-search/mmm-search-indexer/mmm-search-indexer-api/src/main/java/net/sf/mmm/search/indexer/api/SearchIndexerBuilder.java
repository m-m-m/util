/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.api.config.SearchIndexConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerOptions;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for the component that allows to create the
 * {@link SearchIndexer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ComponentSpecification
public interface SearchIndexerBuilder {

  /**
   * This method opens a new {@link SearchIndexer}.<br>
   * <b>ATTENTION:</b><br>
   * Only one {@link SearchIndexer indexer} should be open at a time (for the
   * same <code>dataSource</code>). You have to {@link SearchIndexer#close()
   * close} the indexer before calling this method again.<br>
   * <b>WARNING:</b><br>
   * Be very careful when setting <code>update</code> to <code>false</code> to
   * avoid loosing valuable data.
   * 
   * @param dataSource is a string identifying the data source where the index
   *        is persisted. This is typically the absolute path to the index in
   *        the local filesystem.
   * @param options are the
   *        {@link net.sf.mmm.search.indexer.api.config.SearchIndexerOptions}.
   * @return the create indexer.
   * @throws SearchException if the operation failed.
   */
  SearchIndexer createIndexer(String dataSource, SearchIndexerOptions options)
      throws SearchException;

  /**
   * This method opens a new {@link SearchIndexer}.<br>
   * <b>ATTENTION:</b><br>
   * Only one {@link SearchIndexer indexer} should be open at a time (for the
   * same {@link SearchIndexConfiguration#getLocation() location}). You have to
   * {@link SearchIndexer#close() close} the indexer before calling this method
   * again.<br>
   * 
   * @param searchIndexConfiguration is the according
   *        {@link SearchIndexConfiguration configuration}.
   * @return the create indexer.
   * @throws SearchException if the operation failed.
   */
  SearchIndexer createIndexer(SearchIndexConfiguration searchIndexConfiguration)
      throws SearchException;

  /**
   * This method opens a new {@link SearchIndexer}.<br>
   * <b>ATTENTION:</b><br>
   * Only one {@link SearchIndexer indexer} should be open at a time (for the
   * same {@link SearchIndexConfiguration#getLocation() location}). You have to
   * {@link SearchIndexer#close() close} the indexer before calling this method
   * again.<br>
   * <b>ATTENTION:</b><br>
   * Be careful when overwriting the search-index. You may loose valuable data.
   * 
   * @param searchIndexConfiguration is the according
   *        {@link SearchIndexConfiguration configuration}.
   * @param options are the
   *        {@link net.sf.mmm.search.indexer.api.config.SearchIndexerOptions}.
   * @return the create indexer.
   * @throws SearchException if the operation failed.
   */
  SearchIndexer createIndexer(SearchIndexConfiguration searchIndexConfiguration,
      SearchIndexerOptions options) throws SearchException;

}
