/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import java.util.List;

import net.sf.mmm.util.query.api.argument.ListArgument;

/**
 * This is the {@link #isConstant() constant} implementation of {@link ListArgument}.
 *
 * @param <E> the generic type of the {@link List}-{@link List#get(int) elements}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class ConstantListArgument<E> extends ConstantCollectionArgument<List<E>, E>
    implements AbstractListArgument<E> {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public ConstantListArgument(List<E> value) {
    super(value);
  }

}
