/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import java.util.List;
import java.util.function.Function;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 8.5.0
 */
final class ListHelper {

  private ListHelper() {
  }

  public static <S, E> List<E> of(List<S> sourceList, Function<S, E> mapper) {

    return new MappingList<>(sourceList, mapper);
  }

}
