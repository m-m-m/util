/* $Id$ */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.engine.api.SearchResultPage;

/**
 * This is the (default) implementation of the {@link SearchResultPage}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchResultPageImpl extends AbstractSearchResultPage {

  /** @see #getPageHit(int) */
  private final SearchHit[] hits;

  /**
   * The constructor
   * 
   * @param searchQuery
   *        is the {@link #getQuery() query} that caused this page.
   * @param totalHits
   *        is the {@link #getTotalHitCount() total number of hits}.
   * @param hitPerPage
   *        is the {@link #getHitsPerPage() number of hits per page}.
   * @param pageNumber
   * @param searchHits
   */
  public SearchResultPageImpl(String searchQuery, int totalHits, int hitPerPage, int pageNumber,
      SearchHit[] searchHits) {

    super(searchQuery, totalHits, hitPerPage, pageNumber);
    this.hits = searchHits;
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchResultPage#getPageHit(int)
   */
  public SearchHit getPageHit(int index) {

    return this.hits[index];
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchResultPage#getPageHitCount()
   */
  public int getPageHitCount() {

    return this.hits.length;
  }

}
