/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.search;

/**
 * This is the interface for a search query. Such object specifies the criteria
 * of the hits when performing a search.
 * 
 * @see PersistenceSearcher
 * @see PersistenceSearchResult
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PersistenceSearchQuery {

  /**
   * This method gets the maximum number of hits that will be received as result
   * for this query.
   * 
   * @return the maximum hit-count or <code>null</code> for NO limit.
   */
  Integer getMaximumHitCount();

  /**
   * This method gets the maximum delay the search may last until it is
   * canceled.
   * 
   * @return the search timeout or <code>null</code> for NO timeout.
   */
  Integer getSearchTimeout();

}
