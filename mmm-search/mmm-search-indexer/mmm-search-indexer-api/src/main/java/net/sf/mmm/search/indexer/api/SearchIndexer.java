/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import java.io.Closeable;
import java.io.Flushable;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.engine.api.SearchEngine;
import net.sf.mmm.search.engine.api.SearchHit;

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
   * entry is {@link MutableSearchEntry#setField(String, Object) filled} with
   * data, use {@link #add(MutableSearchEntry)} to add it to the search index.
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
   * method will use the property
   * {@link net.sf.mmm.search.api.SearchEntry#FIELD_CUSTOM_ID} to identify a
   * potentially existing <code>entry</code> to update. Otherwise it will use a
   * combination of {@link net.sf.mmm.search.api.SearchEntry#FIELD_URI} and
   * {@link net.sf.mmm.search.api.SearchEntry#FIELD_SOURCE} as identifier (what
   * is NOT a very strong ID as the URI can change).
   * 
   * @param entry is the entry to update.
   * @return the number of entries that have been replaced. This should
   *         typically be <code>1</code> if one entry was replaced or
   *         <code>0</code> none was replaced and the given <code>entry</code>
   *         has only been {@link #add(MutableSearchEntry) added}. A value
   *         greater than <code>1</code> indicates that multiple entries have
   *         been replaced that all have the same identifier what indicates a
   *         mistake of your index or the indexer.
   * @throws SearchException if the operation failed.
   */
  int update(MutableSearchEntry entry) throws SearchException;

  /**
   * This method removes an {@link #add(MutableSearchEntry) existing} entry
   * identified by the given
   * <code>{@link net.sf.mmm.search.engine.api.SearchHit#getId() id}</code> from
   * the search-index. <br>
   * <b>ATTENTION:</b><br>
   * If you are using lucene as implementation according to some questionable
   * design of lucene this method has to close the IndexWriter and create an
   * IndexReader to perform the delete. If you invoke an other method after that
   * the same has to be done reverse. This can cause really bad performance.
   * 
   * @param entryId is the
   *        {@link net.sf.mmm.search.engine.api.SearchHit#getId() ID} of the
   *        entry to remove (NOT <code>null</code>).
   * @return <code>true</code> if the entry existed and has been removed from
   *         the index, <code>false</code> if NO entry exists for the given
   *         <code>id</code>.
   * @throws SearchException if the operation failed.
   */
  boolean removeById(Long entryId) throws SearchException;

  /**
   * This method removes an {@link #add(MutableSearchEntry) existing} entry
   * identified by the given <code>hit</code>. In most cases this method will
   * delegate to {@link #removeById(Long)}. However the implementation can
   * decide what is the best way to do it.
   * 
   * @param hit is the {@link SearchHit} to remove.
   * @return <code>true</code> if the entry existed and has been removed from
   *         the index, <code>false</code> if NO entry exists for the given
   *         <code>hit</code>.
   * @throws SearchException if the operation failed.
   */
  boolean remove(SearchHit hit) throws SearchException;

  /**
   * This method removes an {@link #add(MutableSearchEntry) existing} entry
   * identified by the given <code>uri</code> from the search-index.
   * 
   * @see #update(MutableSearchEntry)
   * 
   * @param cid is the {@link MutableSearchEntry#getCustomId() custom-ID} of an
   *        entry previously {@link #add(MutableSearchEntry) added} to the
   *        index.
   * @return the number of entries that have been removed. This should typically
   *         be <code>1</code> if one entry exists with the given
   *         <code>uid</code> or <code>0</code> if no such entry exists. A value
   *         greater than <code>1</code> indicates that multiple entries have
   *         been removed that all have the given <code>uid</code> what
   *         indicates a mistake of your index(er). A value of <code>-1</code>
   *         means the number is unknown because this is not supported by the
   *         implementation.
   * @throws SearchException if the operation failed.
   */
  int removeByCustumId(String cid) throws SearchException;

  /**
   * This method removes an {@link #add(MutableSearchEntry) existing} entry
   * identified by the given <code>uri</code> from the search-index.
   * 
   * @see #update(MutableSearchEntry)
   * 
   * @param uri is the {@link MutableSearchEntry#setUri(String) URI} of an entry
   *        previously {@link #add(MutableSearchEntry) added} to the index.
   * @param sourceId is the
   *        {@link net.sf.mmm.search.api.config.SearchSource#getId() ID} of the
   *        {@link net.sf.mmm.search.api.config.SearchSource}. This is required
   *        because the <code>uri</code> itself may NOT be unique (e.g.
   *        different sources might both contain the entry with URI
   *        "index.html").
   * @return the number of entries that have been removed. This should typically
   *         be <code>1</code> if one entry exists with the given
   *         <code>uri</code> or <code>0</code> if no such entry exists. A value
   *         greater than <code>1</code> indicates that multiple entries have
   *         been removed that all have the given <code>uri</code>. A value of
   *         <code>-1</code> means the number is unknown because this is not
   *         supported by the implementation.
   * @throws SearchException if the operation failed.
   */
  int removeByUri(String uri, String sourceId) throws SearchException;

  /**
   * This method removes all {@link #add(MutableSearchEntry) existing} entries
   * identified by the given <code>value</code> at the given
   * <code>property</code> from the search-index. <br>
   * <b>ATTENTION:</b><br>
   * Please use this method with care.
   * 
   * @param field is the name of the
   *        {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String)
   *        field} where the <code>value</code> is expected to occur. Therefore
   *        the field should NOT to be of the
   *        {@link net.sf.mmm.search.api.config.SearchFieldConfiguration#getType()
   *        type} {@link net.sf.mmm.search.api.config.SearchFieldType#TEXT}.
   * @param value is the
   *        {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String)
   *        value} of the field.
   * @return the number of entries that have been removed or <code>-1</code> if
   *         the number is unknown because this is not supported by the
   *         implementation.
   * @throws SearchException if the operation failed.
   */
  int remove(String field, Object value) throws SearchException;

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
   * implementation, this method will have no effect. Optimization will
   * typically cause some performance overhead but therefore speed up your
   * {@link SearchEngine#search(net.sf.mmm.search.engine.api.SearchQuery, int)
   * searches}.
   * 
   * @throws SearchException if the operation failed.
   */
  void optimize() throws SearchException;

  /**
   * This method gets the {@link SearchEngine} for the index of this
   * {@link SearchIndexer}. It may be used for advanced indexing where
   * search-queries need to performed for updating the search-index.
   * 
   * @return the {@link SearchEngine}.
   */
  SearchEngine getSearchEngine();

  /**
   * This method closes the search index. After the call of this method no other
   * method of this object shall be invoked.
   * 
   * @throws SearchException if the operation failed.
   */
  void close() throws SearchException;

}
