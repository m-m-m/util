/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.SearchHit;

/**
 * This is the (default) implementation of the {@link net.sf.mmm.search.engine.api.SearchResultPage}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchResultPageImpl extends AbstractSearchResultPage {

  /** @see #getPageHit(int) */
  private final SearchHit[] hits;

  /**
   * The constructor.
   * 
   * @param searchQuery is the {@link #getQuery() query} that caused this page.
   * @param totalHits is the {@link #getTotalHitCount() total number of hits}.
   * @param hitPerPage is the {@link #getHitsPerPage() number of hits per page}.
   * @param pageNumber is the {@link #getPageIndex() index} of this page.
   * @param searchHits are the actual {@link #getPageHit(int) hits} of this page.
   */
  public SearchResultPageImpl(String searchQuery, int totalHits, int hitPerPage, int pageNumber, SearchHit[] searchHits) {

    super(searchQuery, totalHits, hitPerPage, pageNumber);
    this.hits = searchHits;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchHit getPageHit(int index) {

    return this.hits[index];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPageHitCount() {

    return this.hits.length;
  }

}
