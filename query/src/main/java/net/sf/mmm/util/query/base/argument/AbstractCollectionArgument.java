/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import java.util.Collection;

import net.sf.mmm.util.query.api.argument.CollectionArgument;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.base.expression.SqlOperator;

/**
 * The abstract base implementation of {@link CollectionArgument}.
 *
 * @param <V> the generic type of the {@link Collection} values to check by the {@link Expression} to build.
 * @param <E> the generic type of the {@link Collection}-{@link Collection#contains(Object) elements}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface AbstractCollectionArgument<V extends Collection<E>, E>
    extends AbstractArgument<V>, CollectionArgument<V, E> {

  @Override
  default Expression isEmpty() {

    return exp(SqlOperator.EMPTY);
  }

  @Override
  default Expression isNotEmpty() {

    return exp(SqlOperator.NOT_EMPTY);
  }

}
