/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.query.api.ListQuery;
import net.sf.mmm.util.query.api.Query;
import net.sf.mmm.util.query.api.SingleQuery;
import net.sf.mmm.util.query.base.statement.AbstractSelectStatement;

/**
 * This is the implementation of {@link SingleQuery} adapting a {@link ListQuery}.
 *
 * @param <R> the generic type of the {@link #execute() result}.
 * @param <T> the generic type of the internal {@link ListQuery} results.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class SingleQueryImpl<R, T> extends QueryImpl<R, R, T> implements SingleQuery<R> {

  private final ListQuery<T> listQuery;

  private final Function<T, R> mapper;

  /**
   * The constructor.
   *
   * @param statement the {@link AbstractSelectStatement} that {@link AbstractSelectStatement#query() created} this
   *        {@link Query}.
   * @param listQuery the {@link ListQuery} to adapt.
   * @param mode the {@link QueryMode}.
   * @param mapper the {@link Function} to map the results.
   */
  public SingleQueryImpl(AbstractSelectStatement<R, ?, T> statement, ListQuery<T> listQuery, QueryMode mode,
      Function<T, R> mapper) {
    super(statement, listQuery.getSql(), mode);
    this.listQuery = listQuery;
    this.mapper = mapper;
  }

  /**
   * @return the {@link Function} to {@link Function#apply(Object) map} the original query results to the external
   *         result type. Will be the identity function if {@literal <T>=<E>}.
   */
  protected Function<T, R> getMapper() {

    return this.mapper;
  }

  @SuppressWarnings("unchecked")
  @Override
  public R execute() {

    T result;
    if (getMode() == QueryMode.UNIQUE) {
      List<T> hits = this.listQuery.execute();
      int size = hits.size();
      if (size > 1) {
        throw new DuplicateObjectException(getStatement().getAlias().getSource());
      } else if (size == 1) {
        result = hits.get(0);
      } else {
        return null;
      }
    } else {
      Iterator<T> iterator = this.listQuery.iterator();
      if (iterator.hasNext()) {
        result = iterator.next();
      } else {
        return null;
      }
    }
    if (this.mapper == null) {
      return (R) result;
    }
    return this.mapper.apply(result);
  }

  @Override
  public R executeRequired() throws ObjectNotFoundException {

    R hit = execute();
    if (hit == null) {
      throw new ObjectNotFoundException(getStatement().getAlias().getSource());
    }
    return hit;
  }

}
