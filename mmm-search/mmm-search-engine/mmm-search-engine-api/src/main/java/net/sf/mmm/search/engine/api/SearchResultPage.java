/* $Id$ */
package net.sf.mmm.search.engine.api;

/**
 * This is the interface for a {@link #getPageIndex() page} of a
 * {@link SearchResult search-result}. The {@link #getTotalHitCount() total}
 * {@link SearchHit hits} are split into pages from <code>0</code> to
 * <code>{@link #getPageCount()} - 1</code>. This instance is the
 * {@link #getPageIndex()}.th page and has {@link #getPageHitCount()} hits that
 * can be accessed via {@link #getPageHit(int)}. They represent the
 * {@link SearchResult#getHit(int) hits} from
 * <code>{@link #getPageCount()}*{@link #getHitsPerPage()}</code> to
 * <code>{@link #getPageCount()}*{@link #getHitsPerPage()}+{@link #getPageHitCount()}-1</code><br>
 * You can NOT access hits out of the scope the page represented by this
 * {@link SearchResultPage search result}. To get hits of a previous or next
 * page, you have to {@link SearchEngine#search(SearchQuery, int) perform} a new
 * search.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchResultPage {

  /**
   * The default number of {@link #getHitsPerPage() hits per page}.
   */
  int HITS_PER_PAGE = 10;

  /**
   * This method gets the original query string that produced this result.
   * 
   * @return the original query string.
   */
  String getQuery();

  /**
   * This method gets the total number of hits for this
   * {@link #getQuery() query}.
   * 
   * @return the total number of hits.
   */
  int getTotalHitCount();

  /**
   * This method gets the number of hits per page. The
   * {@link #getPageCount() last page} may have less hits than this value.
   * 
   * @see #HITS_PER_PAGE
   * 
   * @return the number of hits per page.
   */
  int getHitsPerPage();

  /**
   * This method gets the number of pages used to split the
   * {@link #getTotalHitCount() total} hits. An empty search result will have
   * one empty page.
   * 
   * @return the number of pages.
   */
  int getPageCount();

  /**
   * This method gets the index of the page represented by this
   * {@link SearchResultPage search result}, where <code>0</code> is the
   * first page and <code>{@link #getPageCount()} - 1</code> is the last page.
   * 
   * @return the index of the page represented by this result.
   */
  int getPageIndex();

  /**
   * This method get the number of {@link #getPageHit(int) hits} in the page
   * represented by this {@link SearchResultPage search result}. The value may
   * be less than the number of {@link #getHitsPerPage() hits per page} if this
   * is the {@link #getPageIndex() last} {@link #getPageCount() page}. The
   * value will be greater than zero, except for the
   * {@link #getPageIndex() first page} that may have a
   * {@link #getPageHitCount() page hit count} of <code>0</code> if the
   * {@link #getQuery() search query} produced no hits.
   * 
   * @return the number of hits in this page.
   */
  int getPageHitCount();

  /**
   * This method gets the {@link SearchHit hit} of this
   * {@link SearchResultPage page} at the given <code>index</code>.<br>
   * Please note, that the <code>index</code> is relative to this
   * {@link SearchResultPage page}. The absolute index of the
   * {@link SearchHit hit} is
   * <code>{@link #getPageIndex()}*{@link #getHitsPerPage()}+index</code>.
   * 
   * @param index
   *        is the index of the requested hit. It has to be in the range from
   *        <code>0</code> to <code>{@link #getPageHitCount()} - 1</code>.
   * @return the requested {@link SearchHit hit}.
   */
  SearchHit getPageHit(int index);

}
