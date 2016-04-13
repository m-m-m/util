/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.argument;

import java.util.Collection;

import net.sf.mmm.util.query.api.expression.Expression;

/**
 * Extends {@link Argument} to build an {@link Expression} of {@link Collection} values.
 *
 * @param <V> the generic type of the {@link Collection} values to check by the {@link Expression} to build.
 * @param <E> the generic type of the {@link Collection}-{@link Collection#contains(Object) elements}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface CollectionArgument<V extends Collection<E>, E> extends Argument<V> {

  /**
   * @see Collection#isEmpty()
   * @return an {@link Expression} for {@code this IS EMPTY}.
   */
  Expression isEmpty();

  /**
   * @see Collection#isEmpty()
   * @return an {@link Expression} for {@code this IS NOT EMPTY}.
   */
  Expression isNotEmpty();

}
