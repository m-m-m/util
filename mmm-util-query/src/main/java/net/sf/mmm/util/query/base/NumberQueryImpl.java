/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import net.sf.mmm.util.query.api.NumberQuery;
import net.sf.mmm.util.query.api.Query;
import net.sf.mmm.util.query.base.statement.AbstractSelectStatement;

/**
 * This is the implementation of {@link NumberQuery}.
 *
 * @param <E> the generic type of the {@link #execute() result}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class NumberQueryImpl<E extends Number & Comparable<?>> extends QueryImpl<E> implements NumberQuery<E> {

  /**
   * The constructor.
   *
   * @param statement the {@link AbstractSelectStatement} that {@link AbstractSelectStatement#query() created} this
   *        {@link Query}.
   * @param sql the {@link AbstractSelectStatement#getSql() SQL of the statement}.
   */
  public NumberQueryImpl(AbstractSelectStatement<?, ?> statement, String sql) {
    super(statement, sql, QueryMode.NORMAL);
  }

}
