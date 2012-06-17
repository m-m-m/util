/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api;

/**
 * This is the interface for a {@link #getPageIndex() page} of a {@link SearchResult search-result}. The
 * {@link #getTotalHitCount() total} {@link SearchHit hits} are split into pages from <code>0</code> to
 * <code>{@link #getPageCount()} - 1</code>. This instance is the {@link #getPageIndex()}.th page and has
 * {@link #getPageHitCount()} hits that can be accessed via {@link #getPageHit(int)}. They represent the
 * {@link SearchResult#getHit(int) hits} from <code>{@link #getPageCount()}*{@link #getHitsPerPage()}</code>
 * to <code>{@link #getPageCount()}*{@link #getHitsPerPage()}+{@link #getPageHitCount()}-1</code> <br>
 * You can NOT access hits out of the scope the page represented by this {@link SearchResultPage search
 * result}. To get hits of a previous or next page, you have to {@link SearchEngine#search(SearchQuery, int)
 * perform} a new search.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchResultPage extends Iterable<SearchHit> {

  /**
   * The default number of {@link #getHitsPerPage() hits per page}.
   */
  int DEFAULT_HITS_PER_PAGE = 10;

  /**
   * This method gets the original query string that produced this result.
   * 
   * @return the original query string.
   */
  String getQuery();

  /**
   * This method gets the total number of hits for this {@link #getQuery() query}.
   * 
   * @return the total number of hits.
   */
  int getTotalHitCount();

  /**
   * This method gets the number of {@link #getPageHit(int) hits} per {@link SearchResultPage page}. The
   * {@link #getPageCount() last page} may have less hits than this value.
   * 
   * @see #DEFAULT_HITS_PER_PAGE
   * 
   * @return the number of hits per page.
   */
  int getHitsPerPage();

  /**
   * This method gets the number of pages used to split the {@link #getTotalHitCount() total} hits. An empty
   * search result will have one empty page.
   * 
   * @return the number of pages.
   */
  int getPageCount();

  /**
   * This method gets the index of the page represented by this {@link SearchResultPage search result}, where
   * <code>0</code> is the first page and <code>{@link #getPageCount()} - 1</code> is the last page.
   * 
   * @return the index of the page represented by this result.
   */
  int getPageIndex();

  /**
   * This method gets the absolute number of the first hit of this page. This is the same as:
   * 
   * <pre>
   * {@link #getPageIndex()} * {@link #getHitsPerPage()} + 1
   * </pre>
   * 
   * @return the number of the first {@link #getPageHit(int) hit} of this page.
   */
  int getHitStartNumber();

  /**
   * This method gets the absolute number of the last hit of this page. This is the same as:
   * 
   * <pre>
   * {@link #getHitStartNumber()} + {@link #getPageHitCount()}
   * </pre>
   * 
   * @return the number of the last {@link #getPageHit(int) hit} of this page.
   */
  int getHitEndNumber();

  /**
   * This method get the number of {@link #getPageHit(int) hits} in the page represented by this
   * {@link SearchResultPage search result}. The value may be less than the number of
   * {@link #getHitsPerPage() hits per page} if this is the {@link #getPageIndex() last}
   * {@link #getPageCount() page}. The value will be greater than zero, except for the {@link #getPageIndex()
   * first page} that may have a {@link #getPageHitCount() page hit count} of <code>0</code> if the
   * {@link #getQuery() search query} produced no hits.
   * 
   * @return the number of hits in this page.
   */
  int getPageHitCount();

  /**
   * This method gets the {@link SearchHit hit} of this {@link SearchResultPage page} at the given
   * <code>index</code>.<br>
   * Please note, that the <code>index</code> is relative to this {@link SearchResultPage page}. The absolute
   * index of the {@link SearchHit hit} is
   * <code>{@link #getPageIndex()}*{@link #getHitsPerPage()}+index</code>.
   * 
   * @param index is the index of the requested hit. It has to be in the range from <code>0</code> to
   *        <code>{@link #getPageHitCount()} - 1</code>.
   * @return the requested {@link SearchHit hit}.
   */
  SearchHit getPageHit(int index);

  /**
   * This method gets the {@link #getPageIndex() page-index} of the start {@link SearchResultPage page} when a
   * number <code>(pagingRange*2)+1</code> links should be rendered for paging. The <code>pagingRange</code>
   * actually specifies the number of links forward and backwards.<br>
   * The number of {@link SearchResultPage result-pages} is potentially NOT limited. To avoid that your GUI
   * layout gets broken by too many paging links, you can use this method to render a fixed number of paging
   * links.<br>
   * E.g. assume <code>pagingCount</code> is 2 and you would have a {@link #getPageCount() page-count} of e.g.
   * <code>10</code>. Then you would render links for visiting the pages from <code>0</code> to <code>4</code>
   * if your {@link #getPageIndex() page-index} is <code>0</code>,<code>1</code> , or <code>2</code>. For a
   * {@link #getPageIndex() page-index} of <code>3</code>, you would render the links from <code>1</code> to
   * <code>5</code> and so on.<br>
   * The calculation is performed per call so avoid multiple calls of this method if possible.
   * 
   * @see #getPagingEndIndex(int)
   * 
   * @param pagingRange the number of paging links forward and backwards.
   * @return the {@link #getPageIndex() page-index} to start paging with.
   */
  int getPagingStartIndex(int pagingRange);

  /**
   * This method gets the {@link #getPageIndex() page-index} of the end {@link SearchResultPage page} when a
   * number <code>(pagingRange*2)+1</code> links should be rendered for paging. The <code>pagingRange</code>
   * actually specifies the number of links forward and backwards.<br>
   * The number of {@link SearchResultPage result-pages} is potentially NOT limited. To avoid that your GUI
   * layout gets broken by too many paging links, you can use this method to render a fixed number of paging
   * links.<br>
   * E.g. assume <code>pagingCount</code> is 2 and you would have a {@link #getPageCount() page-count} of e.g.
   * <code>10</code>. Then you would render links for visiting the pages from <code>5</code> to <code>9</code>
   * if your {@link #getPageIndex() page-index} is <code>7</code>,<code>8</code> , or <code>9</code>. For a
   * {@link #getPageIndex() page-index} of <code>6</code>, you would render the links from <code>4</code> to
   * <code>8</code> and so on.<br>
   * The calculation is performed per call so avoid multiple calls of this method if possible.
   * 
   * @see #getPagingStartIndex(int)
   * 
   * @param pagingRange the number of paging links forward and backwards.
   * @return the {@link #getPageIndex() page-index} to end paging with.
   */
  int getPagingEndIndex(int pagingRange);

}
