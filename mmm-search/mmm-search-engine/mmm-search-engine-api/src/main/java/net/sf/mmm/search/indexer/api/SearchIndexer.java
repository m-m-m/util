/* $Id$ */
package net.sf.mmm.search.indexer.api;

import net.sf.mmm.search.engine.api.SearchException;

/**
 * This is the interface for the indexer used to create and modify a search
 * index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchIndexer {

  /**
   * This method creates a new and empty entry for the search-index. After the
   * entry is
   * {@link SearchIndexEntry#setProperty(String, String, boolean, boolean) filled}
   * with data, use {@link #add(SearchIndexEntry)} to add it to the search
   * index.
   * 
   * @return a new and empty search-index entry.
   */
  SearchIndexEntry createEntry();

  /**
   * This method adds the given <code>entry</code> to the search-index.
   * 
   * @param entry
   * @throws SearchException
   */
  void add(SearchIndexEntry entry) throws SearchException;

  /**
   * 
   * @param entry
   * @return
   * @throws SearchException
   */
  int update(SearchIndexEntry entry) throws SearchException;

  /**
   * This method removes an {@link SearchIndexEntry#addToIndex() existing} entry
   * identified by the given <code>uri</code> from the search-index.
   * 
   * @param uri
   *        is the {@link SearchIndexEntry#setUri(String) URI} of an entry
   *        previously {@link SearchIndexEntry#addToIndex() added} to the index.
   * @return the number of entries that have been removed. This should typically
   *         be <code>1</code> if one entry exists with the given
   *         <code>uri</code> or <code>0</code> if no such entry exists. A
   *         value greater than <code>1</code> indicates that multiple entries
   *         have been removed that all have the given <code>uri</code> what
   *         indicates a mistake of your index(er).
   */
  int removeEntryByUri(String uri) throws SearchException;

  /**
   * This method removes an {@link SearchIndexEntry#addToIndex() existing} entry
   * identified by the given <code>uri</code> from the search-index.
   * 
   * @param uid
   *        is the {@link SearchIndexEntry#setUid(String) UID} of an entry
   *        previously {@link SearchIndexEntry#addToIndex() added} to the index.
   * @return the number of entries that have been removed. This should typically
   *         be <code>1</code> if one entry exists with the given
   *         <code>uid</code> or <code>0</code> if no such entry exists. A
   *         value greater than <code>1</code> indicates that multiple entries
   *         have been removed that all have the given <code>uri</code> what
   *         indicates a mistake of your index(er).
   */
  int removeEntryByUid(String uid) throws SearchException;

}
