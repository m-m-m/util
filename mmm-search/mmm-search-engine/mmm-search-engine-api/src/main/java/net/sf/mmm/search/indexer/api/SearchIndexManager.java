/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import net.sf.mmm.search.api.SearchException;

/**
 * This is the interface for the component that provides the
 * {@link SearchIndexer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchIndexManager {

  /**
   * This method opens a new {@link SearchIndexer}.<br>
   * <b>ATTENTION:</b><br>
   * Only one {@link SearchIndexer indexer} should be open at a time (for the
   * same <code>dataSource</code>). You have to
   * {@link SearchIndexer#close() close} the indexer before calling this method
   * again.<br>
   * <b>WARNING:</b><br>
   * Be very careful when setting <code>update</code> to <code>false</code>
   * to avoid loosing valuable data.
   * 
   * @param dataSource
   *        is a string identifying the data source where the index is
   *        persisted. This is typically the absolute path to the index in the
   *        local filesystem.
   * @return the create indexer.
   * @param update
   *        if <code>true</code> the indexer will update the index at
   *        <code>dataSource</code> if it already exists, else if
   *        <code>false</code> the index will be overwritten if it already
   *        exists. If the index does NOT yet exist, it will be created in any
   *        case.
   * @throws SearchException
   *         if the operation failed.
   */
  SearchIndexer createIndexer(String dataSource, boolean update) throws SearchException;

}
