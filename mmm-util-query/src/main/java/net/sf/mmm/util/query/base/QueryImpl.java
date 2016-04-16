/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import net.sf.mmm.util.query.api.Query;
import net.sf.mmm.util.query.base.statement.AbstractSelectStatement;

/**
 * This is the implementation of {@link Query}.
 *
 * @param <E> the generic type of the {@link #execute() result}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class QueryImpl<E> implements Query<E> {

  private final AbstractSelectStatement<?, ?> statement;

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
  public QueryImpl(AbstractSelectStatement<?, ?> statement, String sql, QueryMode mode) {
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
   * @return the original {@link AbstractSelectStatement} that created this {@link Query}. Please note that the
   *         statement can be modified after creating this {@link Query} and can also create additional queries. Hence
   *         use {@link #getSql()} from this {@link Query} instead of retrieving the SQL from this statement.
   */
  protected AbstractSelectStatement<?, ?> getStatement() {

    return this.statement;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E execute() {

    return (E) this.statement.executeQuery(this.sql, this.mode);
  }

  @Override
  public String toString() {

    return this.sql;
  }

}
