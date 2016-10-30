/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import java.util.Set;

import net.sf.mmm.util.query.api.argument.ListArgument;

/**
 * This is the {@link #isConstant() constant} implementation of {@link ListArgument}.
 *
 * @param <E> the generic type of the {@link Set}-{@link Set#contains(Object) elements}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class ConstantSetArgument<E> extends ConstantCollectionArgument<Set<E>, E>
    implements AbstractSetArgument<E> {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public ConstantSetArgument(Set<E> value) {
    super(value);
  }

}
