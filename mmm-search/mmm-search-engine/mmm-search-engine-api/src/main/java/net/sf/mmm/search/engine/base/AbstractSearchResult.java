/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.engine.api.SearchResult;
import net.sf.mmm.search.engine.api.SearchResultPage;

/**
 * This is the abstract base implementation of the {@link SearchResult} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchResult implements SearchResult {

  /** @see #getQuery() */
  private final String query;

  /**
   * The constructor.
   * 
   * @param searchQuery is the {@link #getQuery() query} that caused this result.
   */
  public AbstractSearchResult(String searchQuery) {

    super();
    this.query = searchQuery;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQuery() {

    return this.query;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPageCount() {

    return getPageCount(SearchResultPage.DEFAULT_HITS_PER_PAGE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPageCount(int hitsPerPage) {

    int total = getHitCount();
    if (total == 0) {
      return 1;
    } else {
      return (getHitCount() + hitsPerPage - 1) / hitsPerPage;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchResultPage getPage(int pageIndex) {

    return getPage(pageIndex, SearchResultPage.DEFAULT_HITS_PER_PAGE);
  }

  /**
   * {@inheritDoc}
   * 
   * This method is a simple implementation of this method. Please override if there is a more efficient way
   * to do this.
   */
  @Override
  public SearchResultPage getPage(int pageIndex, int hitsPerPage) {

    int pageCount = getPageCount(hitsPerPage);
    int pageMax = pageCount - 1;
    int newPageIndex = pageIndex;
    if (newPageIndex > pageMax) {
      newPageIndex = pageMax;
    }
    int hitOffset = newPageIndex * hitsPerPage;
    int hitCount;
    if (newPageIndex == pageMax) {
      // the last page may have less hits
      hitCount = getHitCount() - hitOffset;
    } else {
      hitCount = hitsPerPage;
    }
    SearchHit[] hits = new SearchHit[hitCount];
    for (int i = 0; i < hits.length; i++) {
      hits[i] = getHit(i + hitOffset);
    }
    return new SearchResultPageImpl(this.query, getHitCount(), hitsPerPage, newPageIndex, hits);
  }

}
