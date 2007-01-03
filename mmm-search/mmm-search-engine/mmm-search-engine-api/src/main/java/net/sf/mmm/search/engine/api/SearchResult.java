/* $Id$ */
package net.sf.mmm.search.engine.api;

/**
 * This is the interface for the complete result of a
 * {@link SearchEngine#search(SearchQuery) search}.
 * 
 * @see SearchResultPage
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchResult {

  /**
   * This method gets the original query string that produced this result.
   * 
   * @return the original query string.
   */
  String getQuery();

  /**
   * This method gets the total number of hits for this
   * {@link SearchResult search-result}.
   * 
   * @return the total number of hits.
   */
  int getHitCount();

  /**
   * This method gets the hit at the given <code>index</code>.<br>
   * 
   * @param index
   *        is the index of the requested hit. It has to be in the range from
   *        <code>0</code> to <code>{@link #getHitCount()} - 1</code>.
   * @return the requested {@link SearchHit hit}.
   */
  SearchHit getHit(int index);

  /**
   * This method gets the number of {@link SearchResultPage pages} used to split
   * the {@link #getHitCount() total} {@link SearchHit hits} using the
   * {@link SearchResultPage#HITS_PER_PAGE default} number of hits per page. An
   * empty search result will have one empty page.
   * 
   * @see #getPageCount(int)
   * 
   * @return the number of pages.
   */
  int getPageCount();

  /**
   * This method gets number of {@link SearchResultPage pages} used to split the
   * {@link #getHitCount() total} hits using the given <code>hitsPerPage</code>.
   * An empty search result will have one empty page.
   * 
   * @param hitsPerPage
   *        is the desired number of
   *        {@link SearchResultPage#getHitsPerPage() hits per page}. This
   *        should be a value like 5, 10, 20, 30, ..., 100.
   * @return the number of {@link SearchResultPage pages} required to split the
   *         {@link #getHitCount() total} {@link SearchHit hits}.
   */
  int getPageCount(int hitsPerPage);

  /**
   * This method gets the {@link SearchResultPage page} of this
   * {@link SearchResult search-result} at the given <code>pageIndex</code>
   * using the {@link SearchResultPage#HITS_PER_PAGE default} number of hits per
   * page.
   * 
   * @see SearchEngine#search(SearchQuery, int)
   * 
   * @param pageIndex
   *        is the {@link SearchResultPage#getPageIndex() page index} of the
   *        requested {@link SearchResultPage search result}. This should be in
   *        the range from <code>0</code> to
   *        <code>{@link #getPageCount()} - 1</code>.<br>
   *        It can NOT be guaranteed that the <code>pageIndex</code> is always
   *        valid in the sense as described above (the search index may have
   *        been modified in the meantime). An implementation has to be able to
   *        handle calls with a <code>pageIndex</code> greater or equal to
   *        <code>{@link SearchResultPage#getPageCount()}</code>. The
   *        recommended strategy is to return the last page in this case.
   * @return the requested page.
   */
  SearchResultPage getPage(int pageIndex);

  /**
   * This method gets the {@link SearchResultPage page} of this
   * {@link SearchResult search-result} at the given <code>pageIndex</code>
   * using the given <code>hitsPerPage</code>.
   * 
   * @see SearchEngine#search(SearchQuery, int)
   * 
   * @param pageIndex
   *        is the {@link SearchResultPage#getPageIndex() page index} of the
   *        requested {@link SearchResultPage search result}. This should be in
   *        the range from <code>0</code> to
   *        <code>{@link #getPageCount(int) getPageCount(hitsPerPage)} - 1</code>.<br>
   *        It can NOT be guaranteed that the <code>pageIndex</code> is always
   *        valid in the sense as described above (the search index may have
   *        been modified in the meantime). An implementation has to be able to
   *        handle calls with a <code>pageIndex</code> greater or equal to
   *        <code>{@link SearchResultPage#getPageCount()}</code>. The
   *        recommended strategy is to return the last page in this case.
   * @param hitsPerPage
   *        is the number of {@link SearchHit hits} contained in a
   *        {@link SearchResultPage}.
   * @return the requested page.
   */
  SearchResultPage getPage(int pageIndex, int hitsPerPage);

}
