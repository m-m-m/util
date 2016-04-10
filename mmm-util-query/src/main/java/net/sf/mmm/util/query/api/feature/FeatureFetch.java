/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import java.util.List;

/**
 * A {@link FeatureFetch} is for a regular query {@link net.sf.mmm.util.query.api.Statement} to fetch and
 * retrieve results.
 *
 * @see net.sf.mmm.util.query.api.SelectStatement
 *
 * @param <E> the generic type of the entity to fetch.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatureFetch<E> extends StatementFeature {

  /**
   * Executes this query to fetch all matching results.
   *
   * @return the {@link List} with all matching results. Will be an {@link List#isEmpty() empty List} if no results are
   *         found.
   */
  List<E> fetch();

  /**
   * Executes this query to fetch the first matching result.
   *
   * @return the first matching result or {@code null} if no result is found.
   */
  E fetchFirst();

  /**
   * Executes this query to fetch a unique result.
   *
   * @return the unique result or {@code null} if no result is found.
   */
  E fetchOne();

  /**
   * Executes this query as a COUNT-query.
   *
   * @return the number of objects (rows) matching this query.
   */
  long fetchCount();

}
