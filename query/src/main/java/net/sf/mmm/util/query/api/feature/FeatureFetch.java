/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import java.util.List;

import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.query.api.ListQuery;
import net.sf.mmm.util.query.api.NumberQuery;
import net.sf.mmm.util.query.api.Query;
import net.sf.mmm.util.query.api.SingleQuery;

/**
 * A {@link FeatureFetch} is for a regular {@link net.sf.mmm.util.query.api.statement.SelectStatement} to fetch and
 * retrieve results.
 *
 * @see net.sf.mmm.util.query.api.statement.SelectStatement
 *
 * @param <E> the generic type of the entity to fetch.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract interface FeatureFetch<E> extends StatementFeature {

  /**
   * @return a {@link ListQuery} to fetch all matching results with a regular SELECT.
   */
  ListQuery<E> query();

  /**
   * Executes this query to fetch all matching results.
   *
   * @return the {@link List} with all matching results. Will be an {@link List#isEmpty() empty List} if no results are
   *         found.
   */
  default List<E> fetch() {

    return query().execute();
  }

  /**
   * @return a {@link SingleQuery} to fetch the first matching results with a SELECT.
   */
  SingleQuery<E> queryFirst();

  /**
   * Executes {@link #queryFirst()} and returns the result.
   *
   * @return the first matching result or {@code null} if no result is found.
   */
  default E fetchFirst() {

    return queryFirst().execute();
  }

  /**
   * Executes {@link #queryFirst()} and returns the result.
   *
   * @return the first matching result.
   * @throws ObjectNotFoundException if the query had no match.
   */
  default E fetchFirstRequired() throws ObjectNotFoundException {

    return queryFirst().executeRequired();
  }

  /**
   * @return a {@link SingleQuery} to fetch a single unique matching result with a SELECT. The {@link Query}
   *         {@link Query#execute() execution} will fail with an exception if it matches multiple result objects. It can
   *         only have a single or an empty ({@code null}) result.
   */
  SingleQuery<E> queryOne();

  /**
   * Executes {@link #queryOne()} and returns the result.
   *
   * @return the unique result or {@code null} if no result is found.
   */
  default E fetchOne() {

    return queryOne().execute();
  }

  /**
   * Executes this query to fetch a unique result.
   *
   * @return the unique result.
   * @throws ObjectNotFoundException if the query had no match.
   */
  default E fetchOneRequired() throws ObjectNotFoundException {

    return queryOne().executeRequired();
  }

  /**
   * @return a {@link NumberQuery} to fetch the number of matching objects with a {@code SELECT COUNT}.
   */
  NumberQuery<Long> queryCount();

  /**
   * Executes {@link #queryCount()} and returns the result.
   *
   * @return the number of objects (rows) matching this query.
   */
  default long fetchCount() {

    return queryCount().execute().longValue();
  }

}
