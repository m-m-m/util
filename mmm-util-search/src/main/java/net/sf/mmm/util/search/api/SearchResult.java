/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.search.api;

import java.io.Serializable;
import java.util.List;

/**
 * This is the interface for the result of a query for {@link SearchCriteria}. It contains the
 * {@link #getHits() hits} matching the {@link SearchCriteria}. <br>
 * 
 * @param <HIT> is the type of {@link #getHits() hits}. Should typically be a
 *        {@link net.sf.mmm.util.entity.api.GenericEntity}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface SearchResult<HIT> extends Serializable {

  /**
   * This method gets the {@link List} with all hits returned by this {@link SearchResult}. <br>
   * <b>ATTENTION:</b><br>
   * The {@link List#size() hit-count} is limited to the {@link SearchCriteria#getMaximumHitCount() maximum
   * hit-count} specified by your {@link SearchCriteria}.
   * 
   * @see #isComplete()
   * 
   * @return the {@link List} with all hits.
   */
  List<HIT> getHits();

  /**
   * This method determines if this result holds all hits that matched your {@link SearchCriteria}. Otherwise
   * the matches have been truncated and {@link #getHits()} will return only the amount of hits specified by
   * {@link SearchCriteria#getMaximumHitCount() maximum hit-count} of your {@link SearchCriteria} even though
   * more hits are available.
   * 
   * @return <code>true</code> if this result is complete, <code>false</code> if there are more matches
   *         available than {@link #getHits() contained} in this result.
   */
  boolean isComplete();

}
