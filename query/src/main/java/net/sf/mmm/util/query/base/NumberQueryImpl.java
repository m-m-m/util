/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import net.sf.mmm.util.query.api.NumberQuery;
import net.sf.mmm.util.query.api.Query;
import net.sf.mmm.util.query.base.statement.AbstractSelectStatement;

/**
 * This is the implementation of {@link NumberQuery}.
 *
 * @param <R> the generic type of the {@link #execute() result}.
 * @param <E> the generic type of a single selection of the {@link AbstractSelectStatement}.
 * @param <T> the generic type of the {@link AbstractSelectStatement#execute(String, QueryMode) internal results}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class NumberQueryImpl<R extends Number & Comparable<?>, E, T> extends QueryImpl<R, E, T>
    implements NumberQuery<R> {

  /**
   * The constructor.
   *
   * @param statement the {@link AbstractSelectStatement} that {@link AbstractSelectStatement#query() created} this
   *        {@link Query}.
   * @param sql the {@link AbstractSelectStatement#getSql() SQL of the statement}.
   */
  public NumberQueryImpl(AbstractSelectStatement<E, ?, T> statement, String sql) {
    super(statement, sql, QueryMode.NORMAL);
  }

}
