/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
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
   * {@inheritDoc}
   */
  public int getHitsPerPage() {

    return this.hitsPerPage;
  }

  /**
   * {@inheritDoc}
   */
  public int getPageCount() {

    return this.pageCount;
  }

  /**
   * {@inheritDoc}
   */
  public int getPageIndex() {

    return this.pageIndex;
  }

  /**
   * {@inheritDoc}
   */
  public String getQuery() {

    return this.query;
  }

  /**
   * {@inheritDoc}
   */
  public int getTotalHitCount() {

    return this.totalHitCount;
  }

  /**
   * {@inheritDoc}
   */
  public int getHitStartNumber() {
  
    return this.pageIndex * this.hitsPerPage + 1;
  }
  
  /**
   * {@inheritDoc}
   */
  public int getHitEndNumber() {
  
    return getHitStartNumber() + getPageHitCount();
  }
  
  /**
   * {@inheritDoc}
   */
  public int getPagingStartIndex(int pagingRange) {

    int diff = pagingRange;
    int end = this.pageIndex + pagingRange;
    if (end >= this.pageCount) {
      int delta = (end + 1) - this.pageCount;
      diff = diff + delta;
    }
    int result = this.pageIndex - diff;
    if (result < 0) {
      result = 0;
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public int getPagingEndIndex(int pagingRange) {

    int diff = pagingRange;
    int start = this.pageIndex - pagingRange;
    if (start < 0) {
      diff = diff - start;
    }
    int result = this.pageIndex + diff;
    if (result >= this.pageCount) {
      result = this.pageCount - 1;
    }
    return result;
  }

}
