/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement.orientdb;

import java.util.List;
import java.util.function.Function;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 8.0.0
 */
public class FastMappingList<S, E> extends MappingList<S, E> {

  private final Object[] cache;

  /**
   * The constructor.
   *
   * @param list the list to adapt.
   * @param mapper the {@link Function} to map the {@link List} values.
   */
  public FastMappingList(List<S> list, Function<S, E> mapper) {
    super(list, mapper);
    this.cache = new Object[list.size()];
  }

  @Override
  public E get(int index) {

    E result = (E) this.cache[index];
    if (result == null) {
      result = super.get(index);
      this.cache[index] = result;
    }
    return result;
  }

}
