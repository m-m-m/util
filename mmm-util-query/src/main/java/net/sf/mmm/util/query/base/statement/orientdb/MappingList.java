/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement.orientdb;

import java.util.AbstractList;
import java.util.List;
import java.util.function.Function;

/**
 * This is an implementation of {@link List} that adapts a given {@link List} of elements to a {@link List} of elements
 * of a different type using a given mapping {@link Function}.
 *
 * @author hohwille
 * @since 8.0.0
 */
class MappingList<S, E> extends AbstractList<E> {

  private final List<S> list;

  private final Function<S, E> mapper;

  /**
   * The constructor.
   *
   * @param list the list to adapt.
   * @param mapper the {@link Function} to map the {@link List} values.
   */
  public MappingList(List<S> list, Function<S, E> mapper) {
    super();
    this.list = list;
    this.mapper = mapper;
  }

  @Override
  public E get(int index) {

    return this.mapper.apply(this.list.get(index));
  }

  @Override
  public int size() {

    return this.list.size();
  }

}
