/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import net.sf.mmm.util.query.api.ListQuery;
import net.sf.mmm.util.query.api.Query;
import net.sf.mmm.util.query.base.statement.AbstractSelectStatement;

/**
 * This is the implementation of {@link ListQuery}.
 *
 * @param <E> the generic type of the {@link List}-{@link List#get(int) elements}.
 * @param <T> the generic type of the {@link AbstractSelectStatement#execute(String, QueryMode) internal results}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class ListQueryImpl<E, T> extends QueryImpl<List<E>, E, T> implements ListQuery<E> {

  private final Function<T, E> mapper;

  /**
   * The constructor.
   *
   * @param statement the {@link AbstractSelectStatement} that {@link AbstractSelectStatement#query() created} this
   *        {@link Query}.
   * @param sql the {@link AbstractSelectStatement#getSql() SQL of the statement}.
   * @param mode the {@link QueryMode}.
   * @param mapper the {@link Function} to map the results.
   */
  public ListQueryImpl(AbstractSelectStatement<E, ?, T> statement, String sql, QueryMode mode,
      Function<T, E> mapper) {
    super(statement, sql, mode);
    this.mapper = mapper;
  }

  /**
   * @return the {@link Function} to {@link Function#apply(Object) map} the original query results to the external
   *         result type. May be {@code null} for the identity function if {@literal <T>=<E>}.
   */
  protected Function<T, E> getMapper() {

    return this.mapper;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<E> execute() {

    Object list = executeInternal();
    if (this.mapper == null) {
      return (List<E>) list;
    } else {
      List<T> sourceList = (List<T>) list;
      return ListHelper.of(sourceList, this.mapper);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Iterator<E> iterator() {

    Iterator<T> iterator = iteratorInternal();
    if (this.mapper == null) {
      return (Iterator<E>) iterator;
    } else {
      return new IteratorAdapter<>(iterator, this.mapper);
    }
  }

  private Iterator<T> iteratorInternal() {

    @SuppressWarnings("unchecked")
    List<T> list = (List<T>) executeInternal();
    return list.iterator();
  }

  static class IteratorAdapter<E, T> implements Iterator<E> {

    private final Iterator<T> iterator;

    private final Function<T, E> mapper;

    /**
     * The constructor.
     *
     * @param iterator the {@link Iterator} to adapt.
     * @param mapper the {@link Function} to map the {@link #next() results}.
     */
    public IteratorAdapter(Iterator<T> iterator, Function<T, E> mapper) {
      super();
      this.iterator = iterator;
      this.mapper = mapper;
    }

    @Override
    public boolean hasNext() {

      return this.iterator.hasNext();
    }

    @Override
    public E next() {

      T next = this.iterator.next();
      if (next == null) {
        return null;
      } else {
        return this.mapper.apply(next);
      }
    }

  }

}
