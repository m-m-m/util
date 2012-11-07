/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.search;

import java.util.Iterator;

/**
 * This is the interface for the result of a {@link PersistenceSearchQuery#search()}.<br>
 * To iterate all hits, you can use an enhanced-for-loop:
 * 
 * <pre>
 * {@link PersistenceSearchResult}&lt;MyHit&gt; result = query.{@link PersistenceSearchQuery#search() search()};
 * for (MyHit hit : result) {
 *   ...
 * }
 * </pre>
 * 
 * @param <HIT> is the type of the individual items contained in this result. It is NOT bound to
 *        {@link net.sf.mmm.util.entity.api.GenericEntity} to allow using dedicated transport objects.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PersistenceSearchResult<HIT> extends Iterable<HIT> {

  /**
   * {@inheritDoc}
   * 
   * This method gets an {@link Iterator} of the hits available in this result.
   */
  @Override
  Iterator<HIT> iterator();

  /**
   * This method gets the number of hits that are available within this search-result.<br>
   * <b>ATTENTION:</b><br>
   * The hit-count is limited to the {@link PersistenceSearchCriteria#getMaximumHitCount() maximum hit-count}
   * specified by your {@link PersistenceSearchCriteria query}.
   * 
   * @see #isComplete()
   * 
   * @return the number of hits available in this result.
   */
  int getHitCount();

  /**
   * This method determines if this result holds the all hits that matched your
   * {@link PersistenceSearchCriteria query}. Otherwise the matches have been truncated and
   * {@link #getHitCount()} will return the {@link PersistenceSearchCriteria#getMaximumHitCount() maximum
   * hit-count} of your {@link PersistenceSearchCriteria query} even though more hits are available.
   * 
   * @return <code>true</code> if this result is complete, <code>false</code> if there are more matches
   *         available than {@link #iterator() contained} in this result.
   */
  boolean isComplete();

}
