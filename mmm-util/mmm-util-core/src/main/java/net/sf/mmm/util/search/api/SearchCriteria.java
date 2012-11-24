/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.search.api;

import java.io.Serializable;

/**
 * This is the interface for the transfer-object with the criteria for a search query. Such object specifies
 * the criteria which {@link SearchResult#getHits() hits} will match when performing a search.
 * 
 * @see SearchResult
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface SearchCriteria extends Serializable {

  /**
   * This method gets the maximum number of hits that will be received as result for this query.
   * 
   * @return the maximum hit-count or <code>null</code> for NO limit.
   */
  Integer getMaximumHitCount();

  /**
   * This method gets the offset for the first hit. This is <code>0</code> by default. By providing a multiple
   * of {@link #getMaximumHitCount()} you can simply implement paging.
   * 
   * @return the offset of the first hit.
   */
  int getHitOffset();

  /**
   * This method gets the maximum delay the search may last until it is canceled.
   * 
   * @return the search timeout or <code>null</code> for NO timeout.
   */
  Integer getSearchTimeout();

}
