/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;

/**
 * This is the interface of a search-engine used to perform full-text search. An
 * implementation of this interface has to be thread-safe.
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
   * This method performs a search. The {@link SearchHit hits} are in descending
   * order of their {@link SearchHit#getScore() score} so the first
   * {@link SearchHit hit} is also the best one.<br>
   * ATTENTION: You will typically present the {@link SearchHit hits} in
   * {@link SearchResultPage pages}. If the search-engine is a remote service
   * or the {@link SearchHit hits} are presented on a remote client (e.g. a
   * web-browser), only one {@link SearchResultPage page} is transfered and
   * persented at a time. Instead of using this method, it is recommended to use
   * {@link #search(SearchQuery, int)} or {@link #search(SearchQuery, int, int)}
   * to retrieve a {@link SearchResultPage page}, what will typically be more
   * efficient (depending on the implementation of {@link SearchEngine this}
   * interface). If the complete search and presentation takes place in one
   * single process, it may be faster to use
   * {@link #search(SearchQuery) this method} instead, because the search does
   * NOT need to be performed again to switch the {@link SearchResultPage pages}.
   * 
   * @param query
   *        is the {@link SearchQuery query} of the user that wants to search.
   *        Use the {@link #getQueryBuilder() query-builder} to construct.
   * @return the complete search result.
   * @throws SearchException
   *         if the search can NOT be performed for technical reasons.
   */
  SearchResult search(SearchQuery query) throws SearchException;

  /**
   * This method performs a search. The {@link SearchHit hits} are in descending
   * order of their {@link SearchHit#getScore() score} so the best
   * {@link SearchHit hit} is the first hit ({@link SearchResultPage#getPageHit(int)
   * SearchResult.getPageHit}(0)) of the first page ({@link #search(SearchQuery, int) 
   * search}(query, 0)).<br>
   * This method will use the {@link SearchResultPage#HITS_PER_PAGE default}
   * number of hits per page.
   * 
   * @see #search(SearchQuery, int, int)
   * 
   * @param query
   *        is the {@link SearchQuery query} of the user that wants to search.
   *        Use the {@link #getQueryBuilder() query-builder} to construct.
   * @param pageIndex
   *        is the {@link SearchResultPage#getPageIndex() page index} of the
   *        requested {@link SearchResultPage search result}. For a new query
   *        this should initially be <code>0</code>. After a
   *        {@link SearchResultPage search result} was retrieved by this method,
   *        further calls for the {@link String#equals(Object) same}
   *        {@link SearchResultPage#getQuery() query} can be performed with a
   *        <code>pageIndex</code> greater than <code>0</code> and less than
   *        <code>{@link SearchResultPage#getPageCount()}</code>.<br>
   *        It can NOT be guaranteed that the <code>pageIndex</code> is always
   *        valid in the sense as described above (the search index may have
   *        been modified in the meantime). An implementation has to be able to
   *        handle calls with a <code>pageIndex</code> greater or equal to
   *        <code>{@link SearchResultPage#getPageCount()}</code>. The
   *        recommended strategy is to return the last page in this case.
   * @return the search result page for the given <code>query</code> at the
   *         given <code>pageIndex</code>.
   * @throws SearchException
   *         if the search can NOT be performed for technical reasons.
   */
  SearchResultPage search(SearchQuery query, int pageIndex) throws SearchException;

  /**
   * This method performs a search. The {@link SearchHit hits} are in descending
   * order of their {@link SearchHit#getScore() score} so the best
   * {@link SearchHit hit} is the first hit ({@link SearchResultPage#getPageHit(int)
   * SearchResult.getPageHit}(0)) of the first page ({@link #search(SearchQuery, int) 
   * search}(query, 0)).
   * 
   * @param query
   *        is the {@link SearchQuery query} of the user that wants to search.
   *        Use the {@link #getQueryBuilder() query-builder} to construct.
   * @param pageIndex
   *        is the {@link SearchResultPage#getPageIndex() page index} of the
   *        requested {@link SearchResultPage search result}. For a new query
   *        this should initially be <code>0</code>. After a
   *        {@link SearchResultPage search result} was retrieved by this method,
   *        further calls for the {@link String#equals(Object) same}
   *        {@link SearchResultPage#getQuery() query} can be performed with a
   *        <code>pageIndex</code> greater than <code>0</code> and less than
   *        <code>{@link SearchResultPage#getPageCount()}</code>.<br>
   *        It can NOT be guaranteed that the <code>pageIndex</code> is always
   *        valid in the sense as described above (the search index may have
   *        been modified in the meantime). An implementation has to be able to
   *        handle calls with a <code>pageIndex</code> greater or equal to
   *        <code>{@link SearchResultPage#getPageCount()}</code>. The
   *        recommended strategy is to return the last page in this case.
   * @param hitsPerPage
   *        is the number of {@link SearchHit hits} contained in a
   *        {@link SearchResultPage}.
   * @return the search result page for the given <code>query</code> at the
   *         given <code>pageIndex</code>.
   * @throws SearchException
   *         if the search can NOT be performed for technical reasons.
   */
  SearchResultPage search(SearchQuery query, int pageIndex, int hitsPerPage) throws SearchException;

  /**
   * This method gets a {@link SearchEntry entry} by its technical
   * {@link SearchHit#getEntryId() ID}. <br>
   * This method may be usefull for getting details of a {@link SearchHit hit}
   * in a new request (without rerunning the whole search).
   * 
   * @param id
   *        is the {@link SearchHit#getEntryId() ID} of the requested entry.
   * @return the entry with the given <code>id</code> or <code>null</code>
   *         if no such entry is available.
   * @throws SearchException
   *         if the operation failed for technical reasons.
   */
  SearchEntry getEntry(String id) throws SearchException;

}
