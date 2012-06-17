/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;

/**
 * This is the interface of a search-engine used to perform full-text search. An implementation of this
 * interface has to be thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchEngine {

  /**
   * This method gets the query builder user to create and parse queries.
   * 
   * @return the query builder of this engine.
   */
  SearchQueryBuilder getQueryBuilder();

  /**
   * This method performs an initial search for a given <code>query</code> and returns the first
   * {@link SearchResultPage}. The {@link SearchHit hits} are in descending order of their
   * {@link SearchHit#getScore() score} so the best {@link SearchHit hit} is the first
   * {@link SearchResultPage#getPageHit(int) hit} of the page .<br/>
   * For getting the results of a following {@link SearchResultPage} use
   * {@link #search(SearchQuery, int, int, int)}.
   * 
   * @see #getQueryBuilder()
   * 
   * @param query is the {@link SearchQuery query} of the user that wants to search. Use the
   *        {@link #getQueryBuilder() query-builder} to construct.
   * @param hitsPerPage is the {@link SearchResultPage#getHitsPerPage() number of hits per page}. See
   *        {@link SearchResultPage#DEFAULT_HITS_PER_PAGE}.
   * @return the search result page for the given <code>query</code> at the given <code>pageIndex</code>.
   * @throws SearchException if the search can NOT be performed for technical reasons.
   */
  SearchResultPage search(SearchQuery query, int hitsPerPage) throws SearchException;

  /**
   * This method performs a search for a subsequent {@link SearchResultPage} of an
   * {@link #search(SearchQuery, int) initial search}. The {@link SearchHit hits} are in descending order of
   * their {@link SearchHit#getScore() score}.
   * 
   * @param query is the {@link SearchQuery query} of the user that wants to search. Use the
   *        {@link #getQueryBuilder() query-builder} to construct.
   * @param hitsPerPage is the number of {@link SearchHit hits} contained in a {@link SearchResultPage}. This
   *        should be the same value as in the {@link #search(SearchQuery, int) initial search}.
   * @param pageIndex is the {@link SearchResultPage#getPageIndex() page index} of the requested
   *        {@link SearchResultPage search result}. For a new query this should initially be <code>0</code>.
   *        After a {@link SearchResultPage search result} was retrieved by this method, further calls for the
   *        {@link String#equals(Object) same} {@link SearchResultPage#getQuery() query} can be performed with
   *        a <code>pageIndex</code> greater than <code>0</code> and less than
   *        <code>{@link SearchResultPage#getPageCount()}</code>.<br>
   *        It can NOT be guaranteed that the <code>pageIndex</code> is always valid in the sense as described
   *        above (the search index may have been modified in the meantime). An implementation has to be able
   *        to handle calls with a <code>pageIndex</code> greater or equal to
   *        <code>{@link SearchResultPage#getPageCount()}</code>. The recommended strategy is to return the
   *        last page in this case.
   * @param totalHitCount is the {@link SearchResultPage#getTotalHitCount() total hit count} expected for this
   *        query that is already known from the {@link #search(SearchQuery, int) initial search}. The
   *        implementation has to be able to handle that the search index may have been modified in the
   *        meantime and the value is NOT correct anymore. If the implementation can determine the correct
   *        total hit-count without performance overhead, it may ignore this parameter.
   * @return the search result page for the given <code>query</code> at the given <code>pageIndex</code>.
   * @throws SearchException if the search can NOT be performed for technical reasons.
   */
  SearchResultPage search(SearchQuery query, int hitsPerPage, int pageIndex, int totalHitCount) throws SearchException;

  /**
   * This method counts the number of {@link SearchEntry}s where the given <code>field</code> has the given
   * <code>value</code>. This can be useful for counting the {@link SearchEntry}s of a particular
   * {@link SearchEntry#getSource() source} or {@link SearchEntry#getType() type}. You could also do a search
   * to figure this out but this method is supposed to be much faster.
   * 
   * @param field is the name of the {@link SearchEntry#getField(String) field}.
   * @param value is the potential {@link SearchEntry#getField(String) value} to match.
   * @return the number of matching {@link SearchEntry}s.
   */
  long count(String field, String value);

  /**
   * This method gets the total number of {@link SearchEntry}s in the search-index.
   * 
   * @return the total number of {@link SearchEntry}s.
   */
  long getTotalEntryCount();

  /**
   * This method gets a {@link SearchEntry entry} by its technical {@link SearchHit#getEntryId() entry-ID}. <br>
   * This method may be useful for getting details of a {@link SearchHit hit} in a new request (without
   * rerunning the search).
   * 
   * @param entryId is the {@link SearchHit#getEntryId() ID} of the requested entry.
   * @return the entry with the given <code>id</code> or <code>null</code> if no such entry is available.
   * @throws SearchException if the operation failed for technical reasons.
   */
  SearchEntry getEntry(String entryId) throws SearchException;

}
