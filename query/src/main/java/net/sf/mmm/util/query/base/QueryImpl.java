/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import net.sf.mmm.util.query.api.Query;
import net.sf.mmm.util.query.base.statement.AbstractSelectStatement;

/**
 * This is the implementation of {@link Query}.
 *
 * @param <R> the generic type of the {@link #execute() result}.
 * @param <E> the generic type of a single selection of the {@link AbstractSelectStatement}.
 * @param <T> the generic type of the {@link AbstractSelectStatement#execute(String, QueryMode) internal results}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class QueryImpl<R, E, T> implements Query<R> {

  private final AbstractSelectStatement<E, ?, T> statement;

  private final String sql;

  private final QueryMode mode;

  /**
   * The constructor.
   *
   * @param statement the {@link AbstractSelectStatement} that {@link AbstractSelectStatement#query() created} this
   *        {@link Query}.
   * @param sql the {@link AbstractSelectStatement#getSql() SQL of the statement}.
   * @param mode the {@link QueryMode}.
   */
  public QueryImpl(AbstractSelectStatement<E, ?, T> statement, String sql, QueryMode mode) {
    super();
    this.statement = statement;
    this.sql = sql;
    this.mode = mode;
  }

  @Override
  public String getSql() {

    return this.sql;
  }

  /**
   * @return the {@link QueryMode}.
   */
  public QueryMode getMode() {

    return this.mode;
  }

  /**
   * @return the original {@link AbstractSelectStatement} that created this {@link Query}. Please note that the
   *         statement can be modified after creating this {@link Query} and can also create additional queries. Hence
   *         use {@link #getSql()} from this {@link Query} instead of retrieving the SQL from this statement.
   */
  protected AbstractSelectStatement<E, ?, T> getStatement() {

    return this.statement;
  }

  @SuppressWarnings("unchecked")
  @Override
  public R execute() {

    Object result = executeInternal();
    return (R) result;
  }

  /**
   * @see #execute()
   * @return the untyped result of {@link #execute()}.
   */
  protected Object executeInternal() {

    Object result = this.statement.execute(this.sql, this.mode);
    return result;
  }

  @Override
  public String toString() {

    return this.sql;
  }

}
