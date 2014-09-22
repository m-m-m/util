/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.search.api;

import net.sf.mmm.util.transferobject.api.TransferObject;

/**
 * This is the interface for a transfer-object with the criteria for a {@link SearchQuery search query}. Such
 * object specifies the criteria selecting which {@link SearchResult#getHits() hits} will match when
 * performing a search.<br/>
 * <b>NOTE:</b><br/>
 * This interface only holds generic settings for the query such as {@link #getHitOffset()},
 * {@link #getMaximumHitCount()}, and {@link #getSearchTimeout()}. For your individual search, you can extend
 * {@link net.sf.mmm.util.search.base.AbstractSearchCriteria} to create a java bean with all the fields for
 * your search. For searches in a database using JPA there is additional support provided by
 * <code>mmm-persistence</code>.
 *
 * @see SearchResult
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface SearchCriteria extends TransferObject {

  /**
   * This method gets the maximum number of hits that will be received as result for this query.<br/>
   * <b>Note:</b><br/>
   * This feature is the same as <code>Query.setMaxResults(int)</code> in JPA.
   *
   * @return the maximum hit-count or <code>null</code> for NO limit.
   */
  Integer getMaximumHitCount();

  /**
   * This method gets the offset for the first hit. This is <code>0</code> by default. By providing a multiple
   * of {@link #getMaximumHitCount()} you can simply implement paging.<br/>
   * <b>Note:</b><br/>
   * This feature is the same as <code>Query.setFirstResult(int)</code> in JPA.
   *
   * @return the offset of the first hit.
   */
  int getHitOffset();

  /**
   * This method gets the maximum delay in milliseconds the search may last until it is canceled.<br/>
   * <b>Note:</b><br/>
   * This feature is the same as the query hint <code>"javax.persistence.query.timeout"</code> in JPA.
   *
   * @return the search timeout in milliseconds or <code>null</code> for NO timeout.
   */
  Long getSearchTimeout();

  /**
   * This method determines if the search should be performed in read-only mode. In such case the
   * {@link SearchResult#getHits() result hits} can NOT be modified or their changes will NOT be persisted. If
   * a search-engine does not support this feature (e.g. for full-text search this makes no sense), it will be
   * ignored.
   *
   * @return <code>true</code> if read-only, <code>false</code> otherwise (default).
   * @since 3.1.0
   */
  boolean isReadOnly();

}
