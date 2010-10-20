/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.config;

/**
 * This is the interface for the additional options for the
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer}.
 * 
 * @see net.sf.mmm.search.indexer.api.SearchIndexerBuilder#createIndexer(String,
 *      SearchIndexerOptions)
 * @see net.sf.mmm.search.indexer.base.config.SearchIndexerOptionsBean
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexerOptions {

  /**
   * This method determines if a potentially existing indexer shall be
   * overwritten. In this case if the search-index already exists, it will be
   * deleted and all data is lost. If no index exists yet, it will be created in
   * any case.
   * 
   * @return <code>true</code> if the index shall be overwritten,
   *         <code>false</code> if an existing index shall remain (and can be
   *         updated incremental).
   */
  boolean isOverwriteIndex();

}
