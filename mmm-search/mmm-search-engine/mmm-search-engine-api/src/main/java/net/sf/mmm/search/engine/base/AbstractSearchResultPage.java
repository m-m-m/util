/* $Id$ */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.SearchResultPage;

/**
 * This is the abstract base implementation of the {@link SearchResultPage}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchResultPage implements SearchResultPage {

  /** @see #getQuery() */
  private final String query;

  /** @see #getTotalHitCount() */
  private final int totalHitCount;

  /** @see #getHitsPerPage() */
  private final int hitsPerPage;

  /** @see #getPageCount() */
  private final int pageCount;

  /** @see #getPageIndex() */
  private final int pageIndex;

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
   *        is the {@link #getPageIndex() index} of this page.
   */
  public AbstractSearchResultPage(String searchQuery, int totalHits, int hitPerPage, int pageNumber) {

    super();
    this.query = searchQuery;
    this.totalHitCount = totalHits;
    this.hitsPerPage = hitPerPage;
    this.pageCount = (totalHits + hitPerPage - 1) / hitPerPage;
    this.pageIndex = pageNumber;
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchResultPage#getHitsPerPage()
   */
  public int getHitsPerPage() {

    return this.hitsPerPage;
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchResultPage#getPageCount()
   */
  public int getPageCount() {

    return this.pageCount;
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchResultPage#getPageIndex()
   */
  public int getPageIndex() {

    return this.pageIndex;
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchResultPage#getQuery()
   */
  public String getQuery() {

    return this.query;
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchResultPage#getTotalHitCount()
   */
  public int getTotalHitCount() {

    return this.totalHitCount;
  }

}
