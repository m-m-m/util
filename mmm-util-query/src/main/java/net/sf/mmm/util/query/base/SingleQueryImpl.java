/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import java.util.Iterator;

import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.query.api.ListQuery;
import net.sf.mmm.util.query.api.Query;
import net.sf.mmm.util.query.api.SingleQuery;
import net.sf.mmm.util.query.base.statement.AbstractSelectStatement;

/**
 * This is the implementation of {@link SingleQuery} adapting a {@link ListQuery}.
 *
 * @param <E> the generic type of the {@link #execute() result}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class SingleQueryImpl<E> extends QueryImpl<E> implements SingleQuery<E> {

  private final ListQuery<E> listQuery;

  /**
   * The constructor.
   *
   * @param statement the {@link AbstractSelectStatement} that {@link AbstractSelectStatement#query() created} this
   *        {@link Query}.
   * @param listQuery the {@link ListQuery} to adapt.
   */
  public SingleQueryImpl(AbstractSelectStatement<?, ?> statement, ListQuery<E> listQuery) {
    super(statement, listQuery.getSql(), null);
    this.listQuery = listQuery;
  }

  @Override
  public E execute() {

    Iterator<E> iterator = this.listQuery.iterator();
    if (iterator.hasNext()) {
      return iterator.next();
    }
    return null;
  }

  @Override
  public E executeRequired() throws ObjectNotFoundException {

    E hit = execute();
    if (hit == null) {
      throw new ObjectNotFoundException(getStatement().getAlias().getSource());
    }
    return hit;
  }

}
