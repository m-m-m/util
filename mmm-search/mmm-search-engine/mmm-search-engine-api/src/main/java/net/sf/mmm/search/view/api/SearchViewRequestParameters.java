/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.api;

import net.sf.mmm.search.api.SearchEntry;

/**
 * This is the interface for accessing {@link javax.servlet.ServletRequest#getParameter(String) request
 * parameters}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchViewRequestParameters {

  /** @see #getQuery() */
  String PARAMETER_QUERY = "query";

  /** @see #getPageNumber() */
  String PARAMETER_PAGE = "page";

  /** @see #getHitsPerPage() */
  String PARAMETER_HITS_PER_PAGE = "hits";

  /** @see #getTotalHitCount() */
  String PARAMETER_TOTAL_HIT_COUNT = "totalHits";

  /** @see #getSource() */
  String PARAMETER_SOURCE = SearchEntry.FIELD_SOURCE;

  /** @see #getCreator() */
  String PARAMETER_CREATOR = SearchEntry.FIELD_CREATOR;

  /** @see #getType() */
  String PARAMETER_TYPE = SearchEntry.FIELD_TYPE;

  /** @see #getTitle() */
  String PARAMETER_TITLE = SearchEntry.FIELD_TITLE;

  /** @see #getId() */
  String PARAMETER_ID = "id";

  /**
   * This method gets the {@link net.sf.mmm.search.engine.api.SearchHit#getId() ID} from the request
   * parameters.
   * 
   * @return the id. Will be the empty string if NOT set in request.
   */
  String getId();

  /**
   * This method gets the {@link net.sf.mmm.search.api.SearchEntry#FIELD_TYPE type} from the request
   * parameters.
   * 
   * @return the type. Will be the empty string if NOT set in request.
   */
  String getType();

  /**
   * This method gets the {@link net.sf.mmm.search.api.SearchEntry#FIELD_TITLE title} from the request
   * parameters.
   * 
   * @return the title. Will be the empty string if NOT set in request.
   */
  String getTitle();

  /**
   * This method gets the {@link net.sf.mmm.search.api.SearchEntry#FIELD_CREATOR creator} from the request
   * parameters.
   * 
   * @return the creator. Will be the empty string if NOT set in request.
   */
  String getCreator();

  /**
   * This method gets the {@link net.sf.mmm.search.api.SearchEntry#FIELD_SOURCE source} from the request
   * parameters.
   * 
   * @return the source. Will be the empty string if NOT set in request.
   */
  String getSource();

  /**
   * This method gets the {@link net.sf.mmm.search.engine.api.SearchQueryBuilder#parseStandardQuery(String)
   * query} from the request parameters.
   * 
   * @return the query. Will be the empty string if NOT set in request.
   */
  String getQuery();

  /**
   * This method gets the {@link net.sf.mmm.search.engine.api.SearchResultPage#getTotalHitCount() total number
   * of hits} from the request parameters.
   * 
   * @return the totalHitCount or <code>-1</code> if NOT set in request.
   */
  int getTotalHitCount();

  /**
   * This method gets the number of {@link net.sf.mmm.search.engine.api.SearchResultPage#getHitsPerPage() hits
   * per page} from the request parameters.
   * 
   * @return the hitsPerPage. Will be
   *         {@link net.sf.mmm.search.engine.api.SearchResultPage#DEFAULT_HITS_PER_PAGE} if NOT set in
   *         request.
   */
  int getHitsPerPage();

  /**
   * This method gets the {@link net.sf.mmm.search.engine.api.SearchResultPage#getPageIndex() page-index} from
   * the request parameters.
   * 
   * @return the pageNumber. Will be <code>0</code> if NOT set in request.
   */
  int getPageNumber();

}
