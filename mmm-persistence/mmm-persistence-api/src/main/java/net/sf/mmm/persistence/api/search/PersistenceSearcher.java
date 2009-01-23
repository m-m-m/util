/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.search;

import net.sf.mmm.persistence.api.PersistenceEntityManager;

/**
 * This is the interface for a searcher that can be offered by a
 * {@link PersistenceEntityManager}.
 * 
 * @param <QUERY> is the type of the {@link PersistenceSearchQuery}.
 * @param <HIT>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PersistenceSearcher<QUERY extends PersistenceSearchQuery, HIT> {

  /**
   * This method performs a search specified by the given <code>query</code>.
   * 
   * @param query is the {@link PersistenceSearchQuery} that specifies the
   *        criteria of the searched objects.
   * @return the result of the search.
   */
  PersistenceSearchResult<HIT> search(QUERY query);

  /**
   * This method performs a search specified by the given <code>query</code> but
   * only determines the total number of hits. Therefore the
   * {@link PersistenceSearchQuery#getMaximumHitCount() maximum hit-count} of
   * the given <code>query</code> is ignored.
   * 
   * @param query is the {@link PersistenceSearchQuery} that specifies the
   *        criteria of the searched objects.
   * @return the total number of hits that match the given <code>query</code>.
   */
  long count(QUERY query);

}
