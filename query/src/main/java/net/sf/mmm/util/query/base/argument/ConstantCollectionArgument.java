/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import java.util.Collection;

import net.sf.mmm.util.query.api.argument.CollectionArgument;
import net.sf.mmm.util.query.api.expression.Expression;

/**
 * This is the {@link #isConstant() constant} implementation of {@link CollectionArgument}.
 *
 * @param <V> the generic type of the {@link Collection} values to check by the {@link Expression} to build.
 * @param <E> the generic type of the {@link Collection}-{@link Collection#contains(Object) elements}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class ConstantCollectionArgument<V extends Collection<E>, E> extends ConstantArgument<V>
    implements AbstractCollectionArgument<V, E> {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public ConstantCollectionArgument(V value) {
    super(value);
  }

}
