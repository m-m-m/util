/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query;

import java.util.List;

/**
 * This is the interface for a query.
 * 
 * @see javax.persistence.TypedQuery
 * 
 * @param <RESULT> is the generic type of the {@link #getSingleResult() result}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public interface ListQuery<RESULT> extends OptionalQuery<RESULT> {

  /**
   * This method performs the query and gets the {@link List} with all results.
   * 
   * @see javax.persistence.TypedQuery#getResultList()
   * 
   * @return the {@link List} with all result objects matching the query. Will be an {@link List#isEmpty()
   *         empty} {@link List} if no object matches.
   */
  List<RESULT> getResultList();

}
