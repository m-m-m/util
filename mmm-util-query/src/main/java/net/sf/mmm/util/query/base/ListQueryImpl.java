/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import java.util.List;

import net.sf.mmm.util.query.api.ListQuery;
import net.sf.mmm.util.query.api.Query;
import net.sf.mmm.util.query.base.statement.AbstractSelectStatement;

/**
 * This is the implementation of {@link ListQuery}.
 *
 * @param <E> the generic type of the {@link List}-{@link List#get(int) elements}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ListQueryImpl<E> extends QueryImpl<List<E>> implements ListQuery<E> {

  /**
   * The constructor.
   *
   * @param statement the {@link AbstractSelectStatement} that {@link AbstractSelectStatement#query() created} this
   *        {@link Query}.
   * @param sql the {@link AbstractSelectStatement#getSql() SQL of the statement}.
   * @param mode the {@link QueryMode}.
   */
  public ListQueryImpl(AbstractSelectStatement<?, ?> statement, String sql, QueryMode mode) {
    super(statement, sql, mode);
  }

}
