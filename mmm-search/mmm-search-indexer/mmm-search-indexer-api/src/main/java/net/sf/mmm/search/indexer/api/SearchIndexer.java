/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import java.io.Closeable;
import java.io.Flushable;

import net.sf.mmm.search.api.SearchException;

/**
 * This is the interface for the indexer used to create and modify a search
 * index. After a search-index is created, the contained information can be
 * searched and found via a {@link net.sf.mmm.search.engine.api.SearchEngine}.
 * 
 * @see SearchIndexerBuilder
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchIndexer extends Flushable, Closeable {

  /**
   * This method creates a new and empty entry for the search-index. After the
   * entry is
   * {@link MutableSearchEntry#setProperty(String, String, MutableSearchEntry.Mode)
   * filled} with data, use {@link #add(MutableSearchEntry)} to add it to the
   * search index.
   * 
   * @return a new and empty search-index entry.
   */
  MutableSearchEntry createEntry();

  /**
   * This method adds the given <code>entry</code> to the search-index.
   * 
   * @param entry is the entry to add.
   * @throws SearchException if the operation failed.
   */
  void add(MutableSearchEntry entry) throws SearchException;

  /**
   * This method updates the given <code>entry</code> in the search-index. This
   * method assumes that the property
   * {@link net.sf.mmm.search.api.SearchEntry#PROPERTY_UID} (prior) or
   * {@link net.sf.mmm.search.api.SearchEntry#PROPERTY_URI} (fallback) is used
   * as unique identifier for entries.
   * 
   * @param entry is the entry to update.
   * @return the number of entries that have been replaced. This should
   *         typically be <code>1</code> if one entry was replaced or
   *         <code>0</code> none was replaced and the given <code>entry</code>
   *         has only been {@link #add(MutableSearchEntry) added}. A value
   *         greater than <code>1</code> indicates that multiple entries have
   *         been replaced that all have the same <code>uri</code> or
   *         <code>id</code> what indicates a mistake of your index(er).
   * @throws SearchException if the operation failed.
   */
  int update(MutableSearchEntry entry) throws SearchException;

  /**
   * This method removes an {@link #add(MutableSearchEntry) existing} entry
   * identified by the given
   * <code>{@link net.sf.mmm.search.engine.api.SearchHit#getEntryId() id}</code>
   * from the search-index.
   * 
   * @param entryId is the
   *        {@link net.sf.mmm.search.engine.api.SearchHit#getEntryId() ID} of
   *        the entry to remove.
   * @return <code>true</code> if the entry existed and has been removed from
   *         the index, <code>false</code> if NO entry exists for the given
   *         <code>id</code>.
   * @throws SearchException if the operation failed.
   */
  boolean removeById(String entryId) throws SearchException;

  /**
   * This method removes an {@link #add(MutableSearchEntry) existing} entry
   * identified by the given <code>uri</code> from the search-index.
   * 
   * @see #update(MutableSearchEntry)
   * 
   * @param uid is the {@link MutableSearchEntry#setUid(String) UID} of an entry
   *        previously {@link #add(MutableSearchEntry) added} to the index.
   * @return the number of entries that have been removed. This should typically
   *         be <code>1</code> if one entry exists with the given
   *         <code>uid</code> or <code>0</code> if no such entry exists. A value
   *         greater than <code>1</code> indicates that multiple entries have
   *         been removed that all have the given <code>uri</code> what
   *         indicates a mistake of your index(er).
   * @throws SearchException if the operation failed.
   */
  int removeByUid(String uid) throws SearchException;

  /**
   * This method removes an {@link #add(MutableSearchEntry) existing} entry
   * identified by the given <code>uri</code> from the search-index.
   * 
   * @see #update(MutableSearchEntry)
   * 
   * @param uri is the {@link MutableSearchEntry#setUri(String) URI} of an entry
   *        previously {@link #add(MutableSearchEntry) added} to the index.
   * @return the number of entries that have been removed. This should typically
   *         be <code>1</code> if one entry exists with the given
   *         <code>uri</code> or <code>0</code> if no such entry exists. A value
   *         greater than <code>1</code> indicates that multiple entries have
   *         been removed that all have the given <code>uri</code> what
   *         indicates a mistake of your index(er).
   * @throws SearchException if the operation failed.
   */
  int removeByUri(String uri) throws SearchException;

  /**
   * This method removes all {@link #add(MutableSearchEntry) existing} entries
   * identified by the given <code>value</code> at the given
   * <code>property</code> from the search-index.<br>
   * <b>ATTENTION:</b><br>
   * Please use this method with care.
   * 
   * @param property is the property where the <code>value</code> is expected to
   *        occur. Therefore the property has to be
   *        {@link MutableSearchEntry.Mode#NOT_TOKENIZED}.
   * @param value is the
   *        {@link MutableSearchEntry#setProperty(String, String, MutableSearchEntry.Mode)
   *        value} of an entry previously {@link #add(MutableSearchEntry) added}
   *        to the index.
   * @return the number of entries that have been removed.
   * @throws SearchException if the operation failed.
   */
  int remove(String property, String value) throws SearchException;

  /**
   * This method flushes the search index what ensures that all changes are
   * written to the index. If NOT supported by the implementation, this method
   * will have no effect.
   * 
   * @throws SearchException if the operation failed.
   */
  void flush() throws SearchException;

  /**
   * This method optimizes the search index. If NOT supported by the
   * implementation, this method will have no effect.
   * 
   * @throws SearchException if the operation failed.
   */
  void optimize() throws SearchException;

  /**
   * This method closes the search index. After the call of this method no other
   * method of this object shall be invoked.
   * 
   * @throws SearchException if the operation failed.
   */
  void close() throws SearchException;

}
